package com.bbexcellence.a3awenni.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bbexcellence.a3awenni.MainActivity
import com.bbexcellence.a3awenni.R
import com.bbexcellence.a3awenni.databinding.ActivityLoginBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase


const val RC_SIGN_IN = 100
open class LoginActivity : AppCompatActivity() {

    private var binding: ActivityLoginBinding? = null
    lateinit var tabs: TabLayout
    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager
    private lateinit var provider: OAuthProvider.Builder
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val loginAdapter = LoginAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding?.loginViewPager!!

        viewPager.adapter = loginAdapter
        tabs = binding?.loginTabLayout!!
        tabs.setupWithViewPager(viewPager)

        // Initialize Firebase Auth
        auth = Firebase.auth

        //Setting up login buttons
        binding?.fabFacebook?.setOnClickListener {
            // Show loading image
            //binding?.statusImage?.visibility = View.VISIBLE
            authenticateFbUser()
        }
        binding?.fabTwitter?.setOnClickListener {
            authenticateTwitterUser()
        }
        binding?.fabGoogle?.setOnClickListener {
            authenticateGoogleUser()
        }

        animateScreen()
    }

    private fun authenticateGoogleUser() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("74565081141-gppg07v5bi4v85rlvuqjap2klt11m87p.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun authenticateTwitterUser() {
        //Initialize Twitter Auth
        provider = OAuthProvider.newBuilder("twitter.com")
        // Target specific email with login hint.
        provider.addCustomParameter("lang", "fr");
        val pendingResultTask = auth.pendingAuthResult
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                .addOnSuccessListener {
                    loginVerifiedUser()
                }
                .addOnFailureListener {
                    // Handle failure.
                    displayAlert(
                        R.string.wrong_login_cred_dialog_title,
                        R.string.wrong_login_cred_dialog_message
                    )
                }
        } else {
            // There's no pending result so you need to start the sign-in flow.
            // See below.
            auth.startActivityForSignInWithProvider(this, provider.build())
                .addOnSuccessListener {
                    loginVerifiedUser()
                }
                .addOnFailureListener {
                    // Handle failure.
                    displayAlert(
                        R.string.wrong_login_cred_dialog_title,
                        R.string.wrong_login_cred_dialog_message
                    )
                }
        }
    }

    private fun loginVerifiedUser() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun authenticateFbUser() {
        // Callback registration
        callbackManager = CallbackManager.Factory.create()
        val loginManager = LoginManager.getInstance()
        loginManager.logInWithReadPermissions(this, listOf("email", "public_profile"))
        loginManager.registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {

                override fun onSuccess(result: LoginResult?) {
                    //binding?.statusImage?.visibility = View.VISIBLE
                    Log.d(TAG, "facebook:onSuccess:$result")
                    handleFacebookAccessToken(result!!.accessToken)
                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(error: FacebookException) {
                    // App code
                    displayAlert(
                        R.string.wrong_login_cred_dialog_title,
                        R.string.wrong_login_cred_dialog_message
                    )
                }
            })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    loginVerifiedUser()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    displayAlert(
                        R.string.wrong_login_cred_dialog_title,
                        R.string.wrong_login_cred_dialog_message
                    )
                }
            }
    }

    private fun animateScreen() {
        val facebookButton = binding?.fabFacebook!!
        val googleButton = binding?.fabGoogle!!
        val twitterButton = binding?.fabTwitter!!

        animateScreenWidgets(
            tabs,
            300f,
            0f,
            0f,
            1f,
            1000,
            100
        )
        animateScreenWidgets(
            facebookButton,
            300f,
            0f,
            0f,
            1f,
            1000,
            400
        )
        animateScreenWidgets(
            googleButton,
            300f,
            0f,
            0f,
            1f,
            1000,
            600
        )
        animateScreenWidgets(
            twitterButton,
            300f,
            0f,
            0f,
            1f,
            1000,
            800
        )
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                displayAlert(R.string.wrong_login_cred_dialog_title,
                    R.string.wrong_login_cred_dialog_message)
            }
        } else {
            // Pass the activity result back to the Facebook SDK
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    loginVerifiedUser()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    displayAlert(R.string.wrong_login_cred_dialog_title,
                    R.string.wrong_login_cred_dialog_message)
                }
            }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}