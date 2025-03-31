package com.iraklyoda.transferapp.presentation.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.iraklyoda.transferapp.R

fun ImageView.loadImage(
    source: Any?,
    placeholder: Int = R.drawable.ic_launcher_background,
    errorImage: Int = R.drawable.ic_launcher_foreground
) {
    Glide.with(this)
        .load(source)
        .apply(
            RequestOptions()
                .placeholder(placeholder)
                .error(errorImage)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        )
        .into(this)
}