package com.example.kugouactivitytest

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_like.*

class LikeActivity : AppCompatActivity() {
    private val songList = ArrayList<Song>()
    private val mediaPlayer = MediaPlayer()
    private var index = 0

    private fun initSong() {
        repeat(1) {
            songList.add(Song("Bad (坏的)", "张艺兴", "Bad.mp3", R.drawable.honey))
            songList.add(Song("Amusement Park (游乐园)", "张艺兴", "Amusement Park.mp3", R.drawable.honey))
            songList.add(Song("Honey (和你)", "张艺兴", "Honey.mp3", R.drawable.honey))
            songList.add(Song("Goodbye Christmas (Eng Ver_)", "张艺兴", "Goodbye Christmas (Eng Ver_).mp3", R.drawable.christmas))
            songList.add(Song("Goodbye Christmas (Instrumental)", "张艺兴", "Goodbye Christmas (Instrumental).mp3", R.drawable.christmas))
            songList.add(Song("你的感觉 (Can you feel me)", "张艺兴", "Can you feel me.mp3", R.drawable.christmas))
            songList.add(Song("圣诞的爱 (Christmas Love)", "张艺兴", "Christmas Love.mp3", R.drawable.christmas))
            songList.add(Song("圣诞又至 (Goodbye Christmas)", "张艺兴", "Goodbye Christmas_ch.mp3", R.drawable.christmas))
            songList.add(Song("小小礼物 (Gift to XBACK)", "张艺兴", "Gift to XBACK.mp3", R.drawable.christmas))
            songList.add(Song("匕首 (HAND)", "张艺兴", "HAND.mp3", R.drawable.sheep))
            songList.add(Song("老大 (BOSS)", "张艺兴", "BOSS.mp3", R.drawable.sheep))
            songList.add(Song("太多 (Too Much)", "张艺兴", "Too Much.mp3", R.drawable.sheep))
            songList.add(Song("兴迷 (X BACK)", "张艺兴", "X BACK.mp3", R.drawable.sheep))
            songList.add(Song("羊 (SHEEP)", "张艺兴", "SHEEP.mp3", R.drawable.sheep))
            songList.add(Song("导演 (DIRECTOR)", "张艺兴", "DIRECTOR.mp3", R.drawable.sheep))
            songList.add(Song("面罩 (MASK)", "张艺兴", "MASK.mp3", R.drawable.sheep))
            songList.add(Song("桃 (PEACH)", "张艺兴", "PEACH.mp3", R.drawable.sheep))
            songList.add(Song("需要你 (I NEED U)", "张艺兴", "I NEED U.mp3", R.drawable.sheep))
            songList.add(Song("摇摆 (SHAKE)", "张艺兴", "SHAKE.mp3", R.drawable.sheep))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like)

        initSong()
        initMediaPlayer(songList[index].music)

        val layoutManager = LinearLayoutManager(this)
        recyclerViewTest.layoutManager = layoutManager
        val adapter = SongAdapter(songList)
        recyclerViewTest.adapter = adapter

        playImage.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                playImage.setImageResource(R.drawable.ic_play)
                mediaPlayer.start()
            } else {
                playImage.setImageResource(R.drawable.ic_pause)
                mediaPlayer.pause()
            }
        }

        upperImage.setOnClickListener {
            index--
            mediaPlayer.reset()
            if (index < 0) {
                index = songList.size - 1
            }
            musicManager()
        }

        nextImage.setOnClickListener {
            index++
            mediaPlayer.reset()
            if (index > songList.size - 1) {
                index = 0
            }
            musicManager()
        }
    }


    private fun initMediaPlayer(music: String) {
        val assetManager = assets
        val fd = assetManager.openFd(music)
        mediaPlayer.setDataSource(fd.fileDescriptor, fd.startOffset, fd.length)
        mediaPlayer.prepare()
        whirl(mediaPlayer.duration)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    private fun musicManager() {
        initMediaPlayer(songList[index].music)
        music_name.text = songList[index].title
        music_singer.text = songList[index].singer
        showImage.setImageResource(songList[index].imageID)
        mediaPlayer.start()
        playImage.setImageResource(R.drawable.ic_play)
    }


    inner class SongAdapter(private val songList: ArrayList<Song>) : RecyclerView.Adapter<SongAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val songTitle: TextView = view.findViewById(R.id.music_name)
            val songSinger: TextView = view.findViewById(R.id.music_singer)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item, parent, false)
            val viewHolder = ViewHolder(view)
            viewHolder.itemView.setOnClickListener {
                //获取位置
                index = viewHolder.adapterPosition
                //下标
                val list = songList[index]
                mediaPlayer.reset()
                initMediaPlayer(list.music)
                playImage.setImageResource(R.drawable.ic_play)
                mediaPlayer.start()
                music_name.text = list.title
                music_singer.text = list.singer
                showImage.setImageResource(list.imageID)
            }
            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val song = songList[position]
            holder.songTitle.text = song.title
            holder.songSinger.text = song.singer
        }

        override fun getItemCount() = songList.count()
    }

    private fun whirl(int: Int) {
        val translateAnimation = RotateAnimation(
                0f,
                360f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        )
        translateAnimation.duration = 10000
        translateAnimation.repeatCount = int / 10000
        translateAnimation.repeatMode = 1
        cardView.animation = translateAnimation
        translateAnimation.start()
    }
}
