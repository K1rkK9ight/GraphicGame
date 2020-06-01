package com.example.findwordgame

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_options.*

var soundControl = true
var hardMode = false

@SuppressLint("WrongViewCast")
class Options : AppCompatActivity() {

    private val SOUND_PREFERENCES = "userSettings"
    private val SOUND_PREFERENCES_CONTROL = "soundControl"
    private val HARD_MODE_PREFERENCES = "userPlaySettings"
    private val HARD_MODE_PREFERENCES_CONTROL = "hardModeControl"
    lateinit var pref1: SharedPreferences
    lateinit var pref2: SharedPreferences
    private val mHideHandler = Handler()
    private val mShowPart2Runnable = Runnable {
        supportActionBar?.show()
        fullscreen_content_controls.visibility = View.VISIBLE
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        pref1 = getSharedPreferences(SOUND_PREFERENCES, MODE_PRIVATE)
        pref2 = getSharedPreferences(HARD_MODE_PREFERENCES, MODE_PRIVATE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_options)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mVisible = true
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        switch1.setChecked(pref1.getBoolean(SOUND_PREFERENCES_CONTROL, true))
        switch1.setOnClickListener {
            if (switch1.isChecked()) {
                soundControl = true
                player!!.start()
                val editor = getSharedPreferences(
                    SOUND_PREFERENCES,
                    MODE_PRIVATE
                ).edit()
                editor.putBoolean(SOUND_PREFERENCES_CONTROL, true)
                editor.apply()
                switch1.setChecked(true)
            } else {
                soundControl = false
                player!!.pause()
                val editor = getSharedPreferences(
                    SOUND_PREFERENCES,
                    MODE_PRIVATE
                ).edit()
                editor.putBoolean(SOUND_PREFERENCES_CONTROL, false)
                editor.apply()
                switch1.setChecked(false)
            }
        }
        switch2.setChecked(pref2.getBoolean(HARD_MODE_PREFERENCES_CONTROL, true))
        switch2.setOnClickListener {
            if (switch2.isChecked()) {
                hardMode = true
                val editor = getSharedPreferences(
                    HARD_MODE_PREFERENCES,
                    MODE_PRIVATE
                ).edit()
                editor.putBoolean(HARD_MODE_PREFERENCES_CONTROL, true)
                editor.apply()
                switch2.setChecked(true)
            } else {
                hardMode = false
                val editor = getSharedPreferences(
                    HARD_MODE_PREFERENCES,
                    MODE_PRIVATE
                ).edit()
                editor.putBoolean(HARD_MODE_PREFERENCES_CONTROL, false)
                editor.apply()
                switch2.setChecked(false)
            }
        }
    }

    fun backToMain(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.alpha, R.anim.beta)
        finish()
    }

    override fun onPause() {
        super.onPause()
        val editor1 = pref1.edit()
        editor1.putBoolean(SOUND_PREFERENCES_CONTROL, soundControl)
        editor1.apply()
        val editor2 = pref2.edit()
        editor2.putBoolean(HARD_MODE_PREFERENCES_CONTROL, hardMode)
        editor2.apply()
    }

    override fun onResume() {
        super.onResume()
        if (pref1.contains(SOUND_PREFERENCES_CONTROL)) {
            soundControl = pref1.getBoolean(SOUND_PREFERENCES_CONTROL, true)
        }
        if (pref2.contains(HARD_MODE_PREFERENCES_CONTROL)) {
            hardMode = pref2.getBoolean(HARD_MODE_PREFERENCES_CONTROL, true)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delayedHide(100)
    }

    private fun hide() {
        supportActionBar?.hide()
        fullscreen_content_controls.visibility = View.GONE
        mVisible = false
        mHideHandler.removeCallbacks(mShowPart2Runnable)
    }

    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }
}
