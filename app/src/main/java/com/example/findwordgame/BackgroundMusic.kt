package com.example.findwordgame

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Switch

var player: MediaPlayer? = null

class BackgroundMusic : Service() {


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        player = MediaPlayer.create(this, R.raw.first)
        player!!.isLooping = true
    }

    override fun onDestroy() {
        player!!.stop()
    }

    override fun onStart(intent: Intent, startid: Int) {
        player!!.start()
    }


}
