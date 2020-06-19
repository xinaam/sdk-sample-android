package com.xfinite.mzaalosdksample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.xfinite.mzaaloplayer.MzaaloPlayer
import com.xfinite.mzaaloplayer.MzaaloPlayerContentTypes
import com.xfinite.mzaaloplayer.core.MZVideoPlayer
import com.xfinite.mzaaloplayer.core.MZVideoPlayerInitListener

class VideoPlayerActivity : AppCompatActivity(), MZVideoPlayerInitListener {

    private lateinit var mzaaloPlayer: MZVideoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        mzaaloPlayer=MzaaloPlayer.createVideoPlayer(this)
        mzaaloPlayer.initialize("55ca20b0-147f-4c1f-80b0-0e7060464360", MzaaloPlayerContentTypes.MOVIE,this)
    }

    override fun onError(error: String) {
        Toast.makeText(this, "Error on player load $error", Toast.LENGTH_SHORT).show()
    }

    override fun onReadyToStart() {
        mzaaloPlayer.start()
    }

    override fun onStop() {
        mzaaloPlayer.clean()
        super.onStop()
    }
}