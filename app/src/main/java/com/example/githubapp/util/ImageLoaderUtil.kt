package com.example.githubapp.util

import android.widget.ImageView
import com.example.githubapp.R
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import javax.inject.Singleton

@Singleton
class ImageLoaderUtil{

    fun loadImage(imageView: ImageView,avatarUrl: String)  {

        val context = imageView.context
        Picasso.with(context)
            .load(avatarUrl)
            .centerCrop()
            .resize(200, 200)
            .error(R.drawable.ic_error_black_24dp)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(imageView, object : Callback {
                override fun onSuccess() { //..image loaded from cache

                }

                override fun onError() { //Try again online if cache failed
                    Picasso.with(context)
                        .load(avatarUrl)
                        .resize(200, 200)
                        .centerCrop()
                        .error(R.drawable.ic_error_black_24dp)
                        .into(imageView, object : Callback {
                            override fun onSuccess() {

                            }
                            override fun onError() {}
                        })
                }
            })
    }
}