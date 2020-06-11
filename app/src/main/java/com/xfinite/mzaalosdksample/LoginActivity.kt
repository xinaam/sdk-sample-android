package com.xfinite.mzaalosdksample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.xfinite.mzaaloauth.MzaaloAuth
import com.xfinite.mzaaloauth.MzaaloAuthLoginListener
import com.xfinite.mzaaloauth.User
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity(), MzaaloAuthLoginListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            progress.visibility = View.VISIBLE

            //Add to login Mzaalo Auth
            MzaaloAuth.login(edtUniqueId.text.toString(), JSONObject(edtUserMetadata.text.toString()),this)
        }
    }

    override fun onError(error: String) {
        progress.visibility = View.GONE
        Toast.makeText(this,"Login failed : $error",Toast.LENGTH_SHORT).show()
    }

    override fun onLoginSuccess(user: User) {
        progress.visibility = View.GONE
        Log.e("Login Info",user.toString())
        Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()


        val intent = Intent(this,HomeActivity::class.java)
        intent.putExtra("firstName",user.firstName)
        intent.putExtra("lastName",user.lastName)
        intent.putExtra("email",user.email)
        intent.putExtra("phone",user.phone)
        intent.putExtra("countryCode",user.countryCode)
        intent.putExtra("gender",user.gender)
        intent.putExtra("dob",user.dob)
        startActivity(intent)
        finish()
    }
}
