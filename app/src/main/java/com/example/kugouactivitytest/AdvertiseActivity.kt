package com.example.kugouactivitytest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kotlinx.android.synthetic.main.activity_advertise.*
import java.util.*

class AdvertiseActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private var time = 5
    private val timer = Timer()
    private val task:TimerTask = object :TimerTask(){
        override fun run() {
            runOnUiThread {
                time--
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advertise)

        handler.postDelayed({
            init()
        },5000)

        timer.schedule(task,1000,1000)

        overButton.setOnClickListener {
            handler.removeCallbacksAndMessages(null)
            init()
        }
    }

    private fun init() {
        val intent = Intent(this,LikeActivity::class.java)
        startActivity(intent)
        finish()
        timer.cancel()
    }

}