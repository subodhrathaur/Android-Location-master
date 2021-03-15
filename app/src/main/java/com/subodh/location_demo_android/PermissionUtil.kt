package com.subodh.location_demo_android

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat

class PermissionUtil {

    private val PREFS_FILENAME = "permission"
    private val TAG = "PermissionUtil"

    private fun shouldAskPermission(context: Context, permission: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionResult = ActivityCompat.checkSelfPermission(context, permission)
            if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                return true
            }
        }
        return false
    }

    fun checkPermission(context: Context, permission: String, listener: PermissionListener) {

        Log.i(TAG, "CheckPermission for $permission")

        if (shouldAskPermission(context, permission)) {
            // Load history permission
            val sharedPreference = context.getSharedPreferences(PREFS_FILENAME, 0)
            val numberShowPermissionDialog = sharedPreference.getInt(permission, 0)

            if (numberShowPermissionDialog == 0) {
                (context as? Activity)?.let {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(it, permission)) {
                        Log.e(TAG, "User has denied permission but not permanently")
                        listener.onPermissionPreviouslyDenied(numberShowPermissionDialog)
                    } else {
                        Log.e(TAG, "Permission denied permanently.")
                        listener.onPermissionDisabledPermanently(numberShowPermissionDialog)
                    }
                } ?: kotlin.run {
                    listener.onNeedPermission()
                }
            } else {
                // Is FirstTime
                listener.onNeedPermission()
            }
            // Save history permission
            sharedPreference.edit().putInt(permission, numberShowPermissionDialog + 1).apply()

        } else {
            listener.onPermissionGranted()
        }

    }
}