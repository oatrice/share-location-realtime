package com.oatrice.ShareLocationRealtime.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.oatrice.ShareLocationRealtime.BuildConfig
import com.oatrice.ShareLocationRealtime.R
import com.oatrice.ShareLocationRealtime.dao.Constants.GOOGLE_SIGNIN_REQUEST_CODE
import com.oatricedev.bikebuffet.common.BaseActivity
import kotlinx.android.synthetic.main.activity_share_location_signin.*
import timber.log.Timber

class ShareLocationSigninActivity : BaseActivity(),
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    private var progress: ProgressDialog? = null

    private var googleSignInOptions: GoogleSignInOptions? = null
    private var mGoogleApiClient: GoogleApiClient? = null

    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_location_signin)
        Timber.i("AAA onCreate")
        Timber.i("AAA onCreate: " + BuildConfig.DEBUG)

        setupProgressBar()
        setupGoogleApiClient()
        setupListener()
    }

    override fun onStop() {
        super.onStop()
        removeProgressDialog()
    }

    override fun onClick(view: View?) {

        when(view!!.id) {
            R.id.btGoogleLogin -> googleLogin()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        checkResultGoogleSignin(data!!, requestCode)
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    private fun googleLogin() {
        Timber.i("AAA googleLogin")
        showProgressBar()
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, GOOGLE_SIGNIN_REQUEST_CODE)
    }

    private fun checkResultGoogleSignin(data: Intent, requestCode: Int) {
        Timber.i("AAA checkResultGoogleSignin")
        if (requestCode == GOOGLE_SIGNIN_REQUEST_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                val account = result.signInAccount
                fireBaseAuthWithGoogle(account!!)

            } else {
                hideProgressBar()
                Toast.makeText(this, R.string.error_signin, Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun fireBaseAuthWithGoogle(account: GoogleSignInAccount) {
        Timber.i("AAA fireBaseAuthWithGoogle")
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            task ->
            hideProgressBar()

            if (task.isSuccessful) {
                pushGoogleUserDataToDatabase(account, task.result.user.uid)
                goToNextActivity()

            } else {
                Toast.makeText(this, R.string.error_signin, Toast.LENGTH_SHORT).show()

            }

        }

    }

    private fun pushGoogleUserDataToDatabase(account: GoogleSignInAccount, uid: String) {

    }

    private fun goToNextActivity() {
        openActivity(MapsActivity::class.java)

    }

    private fun removeProgressDialog() {
        if (progress != null) {
            progress!!.dismiss()
            progress = null
        }
    }

    private fun setupProgressBar() {
        Timber.i("AAA setupProgressBar")
        progress = ProgressDialog(this)
        progress!!.setTitle("")
        progress!!.setMessage("")
        progress!!.isIndeterminate = true

    }

    private fun showProgressBar() {
        Timber.i("AAA showProgressBar")
        if (progress != null)
            progress!!.show()
    }

    private fun hideProgressBar() {
        Timber.i("AAA hideProgressBar")
        if (progress != null)
            progress!!.dismiss()
    }

    private fun setupGoogleApiClient() {
        Timber.i("AAA setupGoogleApiClient")

        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions!!)
                .build()

    }

    private fun setupListener() {
        Timber.i("AAA setupListener")
        btGoogleLogin.setOnClickListener(this)

    }
}
