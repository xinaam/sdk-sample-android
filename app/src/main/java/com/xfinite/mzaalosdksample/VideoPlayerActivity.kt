package com.xfinite.mzaalosdksample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xfinite.mzaaloplayer.MzaaloPlayer
import com.xfinite.mzaaloplayer.MzaaloPlayerContentTypes
import com.xfinite.mzaaloplayer.core.MZVideoPlayer
import com.xfinite.mzaaloplayer.core.MZVideoPlayerInitListener
import kotlinx.android.synthetic.main.activity_video_player.*

class VideoPlayerActivity : AppCompatActivity(), MZVideoPlayerInitListener {

    private lateinit var mzaaloPlayer: MZVideoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        mzaaloPlayer=MzaaloPlayer.createVideoPlayer(this)
    }

    override fun onError(error: String) {
        progressBar.hide()
        Toast.makeText(this, "Error on player load $error", Toast.LENGTH_SHORT).show()
    }

    override fun onReadyToStart() {
        progressBar.hide()
        mzaaloPlayer.start()
        btnInitialise.visibility=View.GONE
    }

    override fun onStop() {
        mzaaloPlayer.clean()
        super.onStop()
    }

    fun initialize(view: View) {
        mzaaloPlayer.initialize(contentId.text.toString(), MzaaloPlayerContentTypes.MOVIE,this)
        mzaaloPlayer.setPlaybackControllerCallback {
            currentState.text="Last captured state: "+it.name
        }
        progressBar.show()
    }

    fun pause(view: View) {
        mzaaloPlayer.pause()
    }

    fun resume(view: View) {
        mzaaloPlayer.resume()
    }

    fun seekTo(view: View) {
        try {
            val pos=seekPos.text.toString().toLong()
            mzaaloPlayer.seekTo(pos)
        }catch (e:NumberFormatException){
            Toast.makeText(this, "Number format exception", Toast.LENGTH_SHORT).show()
        }
    }

    fun getDuration(view: View) {
        output.text = mzaaloPlayer.getDuration().toString()
    }

    fun getCurrentPosition(view: View) {
        output.text=mzaaloPlayer.getCurrentPosition().toString()
    }
}