package com.example.sharelocationrealtime

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.oatrice.ShareLocationRealtime.ShareLocationSigninActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, ShareLocationSigninActivity::class.java)
        startActivity(intent)
        finish()
    }
}
