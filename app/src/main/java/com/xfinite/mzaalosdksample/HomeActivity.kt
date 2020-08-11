package com.xfinite.mzaalosdksample

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xfinite.mzaaloauth.MzaaloAuth
import com.xfinite.mzaaloplayer.MzaaloPlayer
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (intent != null)
        {
            txtUserInfo.text = "FirstName : ${intent.extras?.getString("firstName")} \n\n"+
                    "LastName : ${intent.extras?.getString("lastName")} \n\n"+
                    "Email : ${intent.extras?.getString("email")} \n\n"+
                    "Phone : ${intent.extras?.getString("phone")} \n\n"+
                    "CountryCode : ${intent.extras?.getString("countryCode")} \n\n"+
                    "Gender : ${intent.extras?.getString("gender")} \n\n"+
                    "Dob : ${intent.extras?.getString("dob")} \n\n"


            txtUserToken.text =  "Access Token : ${intent.extras?.getString("accessToken")}"
        }

        txtUserToken.setOnLongClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("",intent.extras?.getString("accessToken")) // get text from edit text
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this,"Access Token Copied",Toast.LENGTH_SHORT).show()
            true
        }

        btnLogout.setOnClickListener{

            //Add to logout from Mzaalo Auth
            MzaaloAuth.logout()
            finish()
        }

        btnRewards.setOnClickListener{
            val intent = Intent(this,RewardActivity::class.java)
            startActivity(intent)
        }

        btnPlayer.setOnClickListener{
            startActivity(Intent(this, VideoPlayerActivity::class.java))
        }
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
