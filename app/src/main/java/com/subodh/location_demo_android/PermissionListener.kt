package com.subodh.location_demo_android

interface PermissionListener {
    fun onNeedPermission()
    fun onPermissionPreviouslyDenied(numberDenyPermission: Int)
    fun onPermissionDisabledPermanently(numberDenyPermission: Int)
    fun onPermissionGranted()
}