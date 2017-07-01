package com.example.sharelocationrealtime

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.oatrice.ShareLocationRealtime.activity.ShareLocationSigninActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, ShareLocationSigninActivity::class.java)
        startActivity(intent)
        finish()
    }
}
