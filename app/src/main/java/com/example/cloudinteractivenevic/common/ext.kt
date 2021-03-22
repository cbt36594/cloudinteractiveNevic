package com.example.cloudinteractivenevic.common

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.cloudinteractivenevic.R

fun NavController.navigateForward(
    resId: Int,
    args: Bundle? = null,
    isSingleTop: Boolean = false,
    useDefaultAnim: Boolean = false
) =
    navigate(
        resId,
        args,
        NavOptions.Builder()
            .setLaunchSingleTop(isSingleTop)
            .setDefaultAnim(useDefaultAnim)
            .build()
    )

fun NavOptions.Builder.setDefaultAnim(useDefaultAnim: Boolean) = apply {
    if (useDefaultAnim) {
        setEnterAnim(R.anim.fade_in)
        setExitAnim(R.anim.fade_out)
        setPopEnterAnim(R.anim.fade_in)
        setPopExitAnim(R.anim.fade_out)
    }
}