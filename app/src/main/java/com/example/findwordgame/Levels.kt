package com.example.findwordgame

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_levels.*

var boardSize = 0

class Levels : AppCompatActivity() {

    private val mHideHandler = Handler()
    private val mShowPart2Runnable = Runnable {
        supportActionBar?.show()
        fullscreen_content_controls.visibility = View.VISIBLE
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_levels)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mVisible = true
    }

    fun wayBack(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.alpha,R.anim.beta)
        finish()
    }

    fun openEightXEight(view: View) {
        startActivity(Intent(this, EightxEight::class.java))
        overridePendingTransition(R.anim.alpha,R.anim.beta)
        boardSize = 8
        finish()
    }

    fun openTenXTen(view: View) {
        startActivity(Intent(this, EightxEight::class.java))
        overridePendingTransition(R.anim.alpha,R.anim.beta)
        boardSize = 10
        finish()
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
