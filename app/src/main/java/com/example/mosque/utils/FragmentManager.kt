package com.example.mosque.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mosque.R

internal fun FragmentManager.removeFragment(
    tag: String,
    fadeIn: Int = R.anim.fadein,
    fadeOut: Int = R.anim.fadeout
) {
    this.findFragmentByTag(tag)?.let {
        this.beginTransaction()
            .disallowAddToBackStack()
            .setCustomAnimations(fadeIn, fadeOut)
            .remove(it)
            .commitNow()
    }
}

internal fun FragmentManager.addFragment(
    containerViewId: Int,
    fragment: Fragment,
    tag: String,
    fadeIn: Int = R.anim.fadein,
    fadeOut: Int = R.anim.fadeout
) {
    this.beginTransaction()
        .setCustomAnimations(fadeIn, fadeOut)
        .replace(containerViewId, fragment, tag)
        .addToBackStack(null)
        .commit()
}

internal fun FragmentManager.replaceFragment(
    containerViewId: Int,
    fragment: Fragment,
    tag: String,
    fadeIn: Int = R.anim.fadein,
    fadeOut: Int = R.anim.fadeout
) {
    this.beginTransaction()
        .setCustomAnimations(fadeIn, fadeOut)
        .replace(containerViewId, fragment, tag)
        .addToBackStack(tag)
        .commit()
}
