package com.example.kotlin45

import android.content.pm.PackageManager
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

fun ImageView.setImage(uri: String) {
    Glide.with(this)
        .load(uri)
        .circleCrop()
        .into(this)
}

fun Fragment.permissionCheckAndRequest(
    requestPermissionLauncher: ActivityResultLauncher<Array<String>>,
    permission : Array<String>,
): Boolean{
    for (per in permission) {
        if (ContextCompat.checkSelfPermission(requireContext(), per)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(permission)
            return false
        }
    }
    return true
}