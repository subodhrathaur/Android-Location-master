package com.subodh.location_demo_android

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices


class UpdateLocationService : Service(), GoogleApiClient.ConnectionCallbacks,
    com.google.android.gms.location.LocationListener,
    GoogleApiClient.OnConnectionFailedListener {
    var mContext: Context? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    var mLocationRequest: LocationRequest? = null

    companion object {
        val SENDMESAGGE = "passMessage"
        var serviceStatus = false
    }

    override fun onBind(p0: Intent?): IBinder? {

        return null
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        serviceStatus = true;
        buildGoogleApiClient()
        Log.e("loaction_service", "Service Create")
        MainActivity.showLog("Service Create")
    }

    private fun buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            Log.e("loaction_service", "api client is null")
            mGoogleApiClient = GoogleApiClient.Builder(this!!)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()
            mGoogleApiClient!!.connect()
        }
    }

    override fun onLocationChanged(location: Location?) {
        Log.e("loaction_service", "service " + location!!.latitude + " " + location!!.longitude)
//        Toast.makeText(mContext,"service "+location!!.latitude+" "+location!!.longitude, Toast.LENGTH_SHORT).show()
        updateLocation(location!!.latitude, location!!.longitude)

    }

    override fun onConnectionSuspended(p0: Int) {
//        Toast.makeText(mContext,"Connection Supported ", Toast.LENGTH_SHORT).show()

    }

    override fun onConnectionFailed(p0: ConnectionResult) {
//        Toast.makeText(mContext,"Connection Failed ", Toast.LENGTH_SHORT).show()
    }

    override fun onConnected(p0: Bundle?) {
//        Toast.makeText(mContext,"Connectioned ", Toast.LENGTH_SHORT).show()
        MainActivity.showLog("On Connected")
        Log.e("on connected ", "on connnected")
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 5000
        mLocationRequest!!.fastestInterval = 5000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                this
            )
            val mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient
            )
        }
    }

    private fun updateLocation(currentLat: Double, currentLong: Double) {
        MainActivity.showLog("LAT LNG       $currentLat            $currentLong")
        sendDataToActivity("Current LatLng is : $currentLat   $currentLong")
    }

    private fun sendDataToActivity(message: String) {
        val intent = Intent()
        intent.action = SENDMESAGGE
        intent.putExtra("message", message)
        sendBroadcast(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sendDataToActivity("Location Service is start to pick your location...");
        return START_STICKY;
    }

    override fun onDestroy() {
        super.onDestroy()
        sendDataToActivity("Location Service is finished, This is going to be more cooler than the heart of your ex...")
        println("onDestroy")
        serviceStatus = false
    }

}