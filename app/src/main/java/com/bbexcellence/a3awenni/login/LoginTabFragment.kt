package com.bbexcellence.a3awenni.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import com.bbexcellence.a3awenni.MainActivity
import com.bbexcellence.a3awenni.R
import com.bbexcellence.a3awenni.databinding.LoginTabFragmentBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.concurrent.fixedRateTimer


private enum class LoginCredential {
    EMAIL,
    PASSWORD
}

const val NAME_MIN_NO_OF_CHARACTERS = 2

class LoginTabFragment : Fragment() {
    private var _binding: LoginTabFragmentBinding? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginTabFragmentBinding.inflate(inflater, container, false)

        // Initialize Firebase Auth
        auth = Firebase.auth

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Initialize layout data variable
        _binding?.loginTabFragment = this

        // Hide keyboard when user clicks outside the edit texts
        hideKeyboardForClickOutside(_binding!!.loginEmailEditText)
        hideKeyboardForClickOutside(_binding!!.loginPasswordEditText)
    }

    /*
    Hides keyboard when the user clicks outside the edit text
     */
    private fun hideKeyboardForClickOutside(editText: EditText) {
        editText.setOnFocusChangeListener { v, _ -> hideKeyboard(v) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    * Checks the user's credentials, and moves to main screen if they're correct
    */
    fun onSubmitCredentials() {
        // Get email and password text layouts
        val emailLayout = _binding!!.loginEmailTextInputLayout
        val passwordLayout = _binding!!.loginPasswordTextInputLayout

        // Get email and password entered by the user
        val emailEditText = _binding?.loginEmailEditText!!
        val passwordEditText = _binding?.loginPasswordEditText!!

        // Check if email and password are valid and correct
        if (checkInputValidity(emailEditText, emailLayout, LoginCredential.EMAIL) &&
            checkInputValidity(passwordEditText, passwordLayout, LoginCredential.PASSWORD)
        ) {
            // Show loading image
            _binding?.statusImage?.visibility = View.VISIBLE
            checkLoginCorrect(
                emailEditText.text.toString().trim(),
                passwordEditText.text.toString().trim()
            )
        }
    }

    private fun checkLoginCorrect(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login Tab Fragment", "signInWithEmail:success")
                    // Remove loading image from screen
                    _binding?.statusImage?.visibility = View.GONE
                    loginVerifiedUser()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Login Tab Fragment", "signInWithEmail:failure", task.exception)
                    // Remove loading image from screen
                    _binding?.statusImage?.visibility = View.GONE
                    displayAlert(
                        R.string.wrong_login_cred_dialog_title,
                        R.string.wrong_login_cred_dialog_message
                    )
                }
            }
    }

    fun openPswdResetActivity() {
        val intent = Intent(context, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

    private fun loginVerifiedUser() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    /*
    Checks if text field input is valid
     */
    private fun checkInputValidity(
        inputEditText: EditText,
        inputLayout: TextInputLayout,
        inputType: LoginCredential
    ): Boolean {
        val inputText = inputEditText.text.toString().trim()
        val isInputValid = when (inputType) {
            LoginCredential.EMAIL -> inputText.isEmailValid()
            LoginCredential.PASSWORD -> inputText.isPasswordValid()
        }
        if (isInputValid) {
            setErrorTextField(inputLayout, inputEditText, false, inputType)
        } else {
            setErrorTextField(inputLayout, inputEditText, true, inputType)
        }

        return isInputValid
    }

    /*
    Hides keyboard
     */
    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /*
    * Sets and resets the text field error status.
    */
    private fun setErrorTextField(
        editTextLayout: TextInputLayout,
        inputEditText: EditText,
        error: Boolean,
        credentialType: LoginCredential
    ) {
        val errorMessageStringId = when (credentialType) {
            LoginCredential.EMAIL -> R.string.try_again_email
            LoginCredential.PASSWORD -> R.string.try_again_password
        }

        if (error) {
            editTextLayout.isErrorEnabled = true
            editTextLayout.error = getString(errorMessageStringId)
            inputEditText.text = null
        } else {
            editTextLayout.isErrorEnabled = false
        }
    }
}
