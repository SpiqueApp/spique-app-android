package com.aaron.spique.ui.main

import android.os.Bundle
import com.aaron.spique.R
import com.aaron.spique.ui.shared.SpeakingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: SpeakingActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}