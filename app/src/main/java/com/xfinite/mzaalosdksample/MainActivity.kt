package com.xfinite.mzaalosdksample

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xfinite.mzaaloauth.MzError
import com.xfinite.mzaaloauth.MzaaloAuth
import com.xfinite.mzaaloauth.MzaaloEnvironment
import com.xfinite.mzaaloplayer.MzaaloPlayer
import com.xfinite.mzaaloplayer.MzaaloPlayerInitListener
import com.xfinite.mzaalorewards.MzaaloRewards
import com.xfinite.mzaalorewards.MzaaloRewardsInitListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MzaaloPlayerInitListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInitialize.setOnClickListener {
            progress.visibility = View.VISIBLE

            //Add to initialize Mzaalo Rewards
            MzaaloPlayer.init(this, edtPartnerCode.text.toString(), this,getSelectedEnvironment())

        }
    }

    private fun getSelectedEnvironment(): MzaaloEnvironment {
        return when (spinnerEnvironment.selectedItem.toString()) {
            MzaaloEnvironment.STAGING.name -> {
                MzaaloEnvironment.STAGING
            }

            MzaaloEnvironment.PRODUCTION.name -> {
                MzaaloEnvironment.PRODUCTION
            }

            else -> {
                MzaaloEnvironment.STAGING
            }
        }
    }

    override fun onError(error: MzError) {
        progress.visibility = View.GONE
        Toast.makeText(this,  "Error - ${error.code}", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess() {
        progress.visibility = View.GONE
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.global_overflow,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.isInitSuccessful->{
                Toast.makeText(this,MzaaloPlayer.isInitSuccessful().toString(), Toast.LENGTH_SHORT).show()
                true
            }

            R.id.getUser->{
                Toast.makeText(this, MzaaloAuth.getUser().toString(), Toast.LENGTH_LONG).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
