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
import com.bbexcellence.a3awenni.MainActivity
import com.bbexcellence.a3awenni.R
import com.bbexcellence.a3awenni.databinding.SignupTabFragmentBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

private enum class SignUpCredential {
    NAME,
    EMAIL,
    PASSWORD,
    PASSWORD_2
}

const val PSWD_MIN_NO_OF_CHARACTERS = 5

class SignupTabFragment : Fragment() {
    private var _binding: SignupTabFragmentBinding? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignupTabFragmentBinding.inflate(inflater, container, false)

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Check if user is signed in (non-null) and update UI accordingly.
        // Initialize Firebase Auth
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            signUpVerifiedUser()
        }

        //Initialize layout data variable
        _binding?.signUpTabFragment = this

        // Hide keyboard when user clicks outside the edit texts
        hideKeyboardForClickOutside(_binding!!.signupNameEditText)
        hideKeyboardForClickOutside(_binding!!.signupEmailEditText)
        hideKeyboardForClickOutside(_binding!!.signupPasswordEditText)
        hideKeyboardForClickOutside(_binding!!.signupConfPasswordEditText)
    }

    /*
    Hides keyboard when the user clicks outside the edit text
     */
    private fun hideKeyboardForClickOutside(editText: EditText) {
        editText.setOnFocusChangeListener { v, _ -> hideKeyboard(v) }
    }

    /*
    Remove data binding and phone number text listener when the fragment is about
    to be destroyed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    * Checks the user's credentials, and moves to main screen if they're correct
    */
    fun onSubmitCredentials() {

        // Get user input text layouts
        val nameLayout = _binding!!.signupNameTextInputLayout
        val emailLayout = _binding!!.signupEmailTextInputLayout
        val passwordLayout = _binding!!.signupPasswordTextInputLayout
        val confPasswordLayout = _binding!!.signupConfPasswordTextInputLayout

        // Get user inputs
        val nameEditText = _binding?.signupNameEditText!!
        val emailEditText = _binding?.signupEmailEditText!!
        val passwordEditText = _binding?.signupPasswordEditText!!
        val confPasswordEditText = _binding?.signupConfPasswordEditText

        // Check if email, name and password are valid and correct
        if (checkInputValidity(
                inputEditText = nameEditText,
                inputLayout = nameLayout,
                inputType = SignUpCredential.NAME
            ) &&
            checkInputValidity(
                inputEditText = emailEditText,
                inputLayout = emailLayout,
                inputType = SignUpCredential.EMAIL
            ) &&
            checkInputValidity(
                inputEditText = passwordEditText,
                inputLayout = passwordLayout,
                inputType = SignUpCredential.PASSWORD
            ) &&
            checkInputValidity(
                inputEditText = confPasswordEditText!!,
                inputLayout = confPasswordLayout,
                inputType = SignUpCredential.PASSWORD
            ) &&
            checkInputValidity(
                passwordEditText, confPasswordEditText, passwordLayout, confPasswordLayout,
                SignUpCredential.PASSWORD_2
            )
        ) {
            //Show loading image
            _binding?.statusImage?.visibility = View.VISIBLE
            //Sign Up
            checkSignUpCorrect(
                nameEditText.text.toString().trim(),
                emailEditText.text.toString().trim(),
                passwordEditText.text.toString().trim()
            )
        }
    }

    private fun checkSignUpCorrect(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Signup Tab Fragment", "createUserWithEmail:success")
                    val user = auth.currentUser

                    // Update the user's name
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }

                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("SignUp Tab Fragment", "User profile updated.")
                                // Remove loading image from screen
                                _binding?.statusImage?.visibility = View.GONE
                                signUpVerifiedUser()
                            }
                        }
                } else {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Signup Tab Fragment", "createUserWithEmail:fail")
                    // Remove loading image from screen
                    _binding?.statusImage?.visibility = View.GONE
                    displayAlert(
                        R.string.email_exists_dialog_title,
                        R.string.email_exists_dialog_message
                    )
                }
            }
    }

    private fun signUpVerifiedUser() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    /*
    Checks if text field input is valid
     */
    private fun checkInputValidity(
        inputEditText: EditText,
        secondPasswordEditText: EditText? = null,
        inputLayout: TextInputLayout,
        secondInputLayout: TextInputLayout? = null,
        inputType: SignUpCredential
    ): Boolean {
        val inputText = inputEditText.text.toString().trim()
        val isInputValid = when (inputType) {
            SignUpCredential.NAME -> inputText.isNameValid()
            SignUpCredential.EMAIL -> inputText.isEmailValid()
            SignUpCredential.PASSWORD -> inputText.isPasswordValid()
            SignUpCredential.PASSWORD_2 -> inputText.arePasswordsValid(
                secondPasswordEditText!!.text.toString().trim()
            )
        }
        if (isInputValid) {
            setErrorTextField(inputLayout, inputEditText, false, inputType)
            if (secondInputLayout != null) {
                setErrorTextField(secondInputLayout, secondPasswordEditText!!, false, inputType)
            }
        } else {
            setErrorTextField(inputLayout, inputEditText, true, inputType)
            if (secondInputLayout != null) {
                setErrorTextField(secondInputLayout, secondPasswordEditText!!, true, inputType)
            }
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
        editText: EditText,
        error: Boolean,
        credentialType: SignUpCredential
    ) {
        val errorMessageStringId = when (credentialType) {
            SignUpCredential.NAME -> R.string.try_again_name
            SignUpCredential.EMAIL -> R.string.try_again_email
            SignUpCredential.PASSWORD -> R.string.try_again_password
            SignUpCredential.PASSWORD_2 -> R.string.try_again_passwords
        }

        if (error) {
            editTextLayout.isErrorEnabled = true
            editTextLayout.error = getString(errorMessageStringId)
            editText.text = null
        } else {
            editTextLayout.isErrorEnabled = false
        }
    }
}