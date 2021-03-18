package com.subodh.location_demo_android


import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    //    private var mLatitudeTextView: TextView? = null
//    private var mLongitudeTextView: TextView? = null
    private var mLoggerView: RecyclerView? = null

    var locationListener: LocationReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mLatitudeTextView = findViewById<TextView>(R.id.tv_latitude)
//        mLongitudeTextView = findViewById<TextView>(R.id.tv_langitude)
        mLoggerView = findViewById<RecyclerView>(R.id.rvLogger)
//        service = UpdateLocationService(this)
        adapter = LoggerAdapter(this@MainActivity, list)
        mLoggerView!!.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        mLoggerView!!.adapter = adapter
        registerReceiver();
        PermissionUtil().checkPermission(this, Manifest.permission.ACCESS_FINE_LOCATION,
            object : PermissionListener {
                override fun onNeedPermission() {
                    showLog("OnNeedPermission")
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        Array(1) { Manifest.permission.ACCESS_FINE_LOCATION },
                        118
                    )
                }

                override fun onPermissionPreviouslyDenied(numberDenyPermission: Int) {
                    showLog("onPermissionPreviouslyDenied             $numberDenyPermission")
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        Array(1) { Manifest.permission.ACCESS_FINE_LOCATION },
                        118
                    )
                }

                override fun onPermissionDisabledPermanently(numberDenyPermission: Int) {
                    showLog("onPermissionDisabledPermanently               $numberDenyPermission")
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        Array(1) { Manifest.permission.ACCESS_FINE_LOCATION },
                        118
                    )

                }

                override fun onPermissionGranted() {
                    showLog("onPermissionGranted")
//                    startService(Intent(this@MainActivity, service))
                    startService(Intent(this@MainActivity, UpdateLocationService::class.java))
                }

            })
    }

    override fun onResume() {
        super.onResume()
        checkStatusService();
        registerReceiver()
    }

    override fun onStop() {
        unregisterReceiver(locationListener)
        super.onStop()
    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Enable Location")
            .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " + "use this app")
            .setPositiveButton("Location Settings") { paramDialogInterface, paramInt ->
                val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(myIntent)
            }
            .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> }
        dialog.show()
    }

    companion object {
        var adapter: LoggerAdapter? = null
        var list: ArrayList<String>? = ArrayList()

        private val TAG = "MainActivity"

        fun showLog(message: String) {
            Log.i("LocationApp", message)
            Log.w("LocationApp", message)
            Log.d("LocationApp", message)
            Log.e("LocationApp", message)
        }
    }


    //function to register receiver :3
    private fun registerReceiver() {
        //Register BroadcastReceiver
        //to receive event from our service
        locationListener = LocationReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(UpdateLocationService.SENDMESAGGE)
        registerReceiver(locationListener, intentFilter)
    }

    // class of receiver, the magic is here...
    class LocationReceiver : BroadcastReceiver() {
        override fun onReceive(arg0: Context, arg1: Intent) {

            showLog("=========>    " + arg1.getStringExtra("message"))

            //verify if the extra var exist
            println(arg1.hasExtra("message")) // true or false
            //another example...
            println(arg1.extras!!.containsKey("message")) // true or false
            //if var exist only print or do some stuff
            if (arg1.hasExtra("message")) {
                //do what you want to
                println(arg1.getStringExtra("message"))
                addDataToList(arg1.getStringExtra("message"))
            }
        }

        public fun addDataToList(latlng: String?) {
            list!!.add(latlng!!)
            adapter!!.notifyDataSetChanged()
        }
    }

    fun checkStatusService() {
        if (UpdateLocationService.serviceStatus != null) {
            if (UpdateLocationService.serviceStatus === true) {
                //do something
                //textview.text("Service is running");
            } else {
                //do something
                //textview.text("Service is not running");
            }
        }
    }
}