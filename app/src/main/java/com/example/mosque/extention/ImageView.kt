package com.example.mosque.extention

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mosque.R


fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
}

internal fun ImageView.loadImage( url:String, progressDrawable: CircularProgressDrawable){
    val options =  RequestOptions()
        .transform(CenterCrop(), RoundedCorners(8))
        .placeholder(progressDrawable)
        .error(R.drawable.no_image)

    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}



internal fun ImageView.loadImageProvider(url : String, progressDrawable: CircularProgressDrawable) {


    var requestOptions = RequestOptions()
    requestOptions = requestOptions
        .placeholder(progressDrawable)
        .fitCenter()
        .transform(RoundedCorners(5))


    Glide.with(this.context)
        .asBitmap()
        .load(url)

        .diskCacheStrategy(DiskCacheStrategy.ALL)

        .priority(Priority.IMMEDIATE)
        .error(R.drawable.ic_logo)
        .apply(requestOptions)
        .into(this)
}



internal fun ImageView.loadAsset(drawable : Int, progressDrawable: CircularProgressDrawable) {
    var requestOptions = RequestOptions()
    requestOptions = requestOptions
        .placeholder(progressDrawable)
        .fitCenter()
        .transform(CenterCrop(), RoundedCorners(5))
    Glide.with(this.context)
        .setDefaultRequestOptions(requestOptions)
        .asBitmap()
        .load(drawable)
        .placeholder(progressDrawable)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.IMMEDIATE)
        .error(R.drawable.ic_logo)
        .into(this)
}

