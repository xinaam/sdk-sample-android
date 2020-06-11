package com.xfinite.mzaalosdksample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xfinite.mzaaloauth.MzaaloAuth
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
        }

        btnLogout.setOnClickListener{

            //Add to logout from Mzaalo Auth
            MzaaloAuth.logout()
            finish()
        }
    }
}
