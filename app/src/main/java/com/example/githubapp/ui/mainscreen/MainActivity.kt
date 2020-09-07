package com.example.githubapp.ui.mainscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubapp.R
import com.example.githubapp.ui.repolist.RepoListFragment
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Load List Fragment
            supportFragmentManager.beginTransaction().add(
            R.id.screenContainer, RepoListFragment.newInstance()
        ).commit()
    }


}
