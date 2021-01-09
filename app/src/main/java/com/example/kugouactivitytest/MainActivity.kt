package com.example.kugouactivitytest

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {

    private val mediaPlayer = MediaPlayer()
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMediaPlayer()
        mediaPlayer.start() //播放音频

        handler.postDelayed({
            init()
        },2000)
    }
    //初始化
    private fun initMediaPlayer() {
        val assetManager = assets
        val fd = assetManager.openFd("music.mp3")
        mediaPlayer.setDataSource(fd.fileDescriptor,fd.startOffset,fd.length)
        mediaPlayer.prepare()
    }
    private fun init() {
        val intent = Intent(this,AdvertiseActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}