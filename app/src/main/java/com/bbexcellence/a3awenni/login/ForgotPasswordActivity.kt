package com.bbexcellence.a3awenni.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bbexcellence.a3awenni.R
import com.bbexcellence.a3awenni.databinding.ActivityForgotPasswordBinding
import com.bbexcellence.a3awenni.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordActivity : AppCompatActivity() {
    private var _binding: ActivityForgotPasswordBinding? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        auth = Firebase.auth

        _binding?.sendPswdEmailButton?.setOnClickListener {
            //Show loading image
            _binding?.statusImage?.visibility = View.VISIBLE
            sendPswdResetEmail()
        }

    }

    private fun sendPswdResetEmail() {
        auth.sendPasswordResetEmail(_binding?.forgotPswdEmailEditText?.text.toString())
            .addOnCompleteListener { task ->
                //Remove loading image from screen
                _binding?.statusImage?.visibility = View.GONE
                if (task.isSuccessful) {
                    displayAlertAndFinishActivity(
                        R.string.pswd_reset_email_dialog_title,
                        R.string.pswd_reset_email_dialog_message
                    )
                } else {
                    displayAlert(
                        R.string.pswd_reset_prb_dialog_title,
                        R.string.pswd_reset_prb_dialog_message
                    )
                }
            }
    }

    companion object {
        private const val TAG = "ForgotPasswordActivity"
    }
}