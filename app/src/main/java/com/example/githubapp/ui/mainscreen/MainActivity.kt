package com.example.githubapp.ui.mainscreen

import android.os.Bundle
import com.example.githubapp.R

import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


}
