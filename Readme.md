# Android Location Demo


### Add these interface to your file

```Kotlin
GoogleApiClient.ConnectionCallbacks,
    com.google.android.gms.location.LocationListener,
    GoogleApiClient.OnConnectionFailedListener
```

### Put below code on your class lavel

```Kotlin
 private var mGoogleApiClient: GoogleApiClient? = null
 private var mLocationRequest: LocationRequest? = null
```

### Then initialize both class in your ```oncreate()```
```Kotlin
            mGoogleApiClient = GoogleApiClient.Builder(this!!)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build()
            mGoogleApiClient!!.connect()

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
```

### In ```updateLocation(lat: Double, lng: Double) ``` method you will get the location of your device

```Kotlin
Log.v("DeviceLocation",lat+""+lng)
```


![alt text](https://github.com/subodhrathaur/Android-Location-master/blob/master/andorid_location.png?raw=true)


