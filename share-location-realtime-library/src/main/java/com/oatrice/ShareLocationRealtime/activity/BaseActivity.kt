package com.oatricedev.bikebuffet.common

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.oatrice.ShareLocationRealtime.R

/**
 * Created by vanirut on 21/12/2559.
 */

open class BaseActivity : AppCompatActivity() {

    private var exit: Boolean = false

    override fun onBackPressed() {
        doubleTabToExit()
    }

    protected val rootView: ViewGroup
        get() = findViewById(android.R.id.content) as ViewGroup

    fun openActivity(cls: Class<*>) {
        startActivity(Intent(this, cls))
        finish()
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(rootView.windowToken, 0)
    }

    fun doubleTabToExit() {
        if (exit) {
            finish()
        }

        exit = true
        Toast.makeText(this, getString(R.string.double_tab_to_exit), Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ exit = false }, 3000)
    }
}
