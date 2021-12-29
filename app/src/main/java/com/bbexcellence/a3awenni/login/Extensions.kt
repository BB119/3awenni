package com.bbexcellence.a3awenni.login

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun String.isPasswordValid(): Boolean {
    return !TextUtils.isEmpty(this) &&
            (this.length > PSWD_MIN_NO_OF_CHARACTERS)
}

fun String.isNameValid(): Boolean {
    return !TextUtils.isEmpty(this) &&
            (this.length > NAME_MIN_NO_OF_CHARACTERS)
}

fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.arePasswordsValid(secondPassword: String): Boolean {
    return this.isPasswordValid() &&
            secondPassword.isPasswordValid() &&
            this.equals(secondPassword)
}

fun Fragment.displayAlert(titleId: Int, messageId: Int) {
    MaterialAlertDialogBuilder(requireContext()).apply {
        setTitle(titleId)
        setMessage(messageId)
        setCancelable(false)
        setPositiveButton(android.R.string.ok, null)
        show()
    }
}

fun Fragment.animateScreenWidgets(
    view: View,
    initialTranslationY: Float,
    finalTranslationY: Float,
    initialAlpha: Float,
    finalAlpha: Float,
    animationDuration: Long,
    startDelay: Long
) {
    view.translationY = initialTranslationY
    view.alpha = initialAlpha
    view.animate()
        .translationY(finalTranslationY)
        .alpha(finalAlpha)
        .setDuration(animationDuration)
        .setStartDelay(startDelay)
        .start()

}

fun AppCompatActivity.animateScreenWidgets(
    view: View,
    initialTranslationY: Float,
    finalTranslationY: Float,
    initialAlpha: Float,
    finalAlpha: Float,
    animationDuration: Long,
    startDelay: Long
) {
    view.translationY = initialTranslationY
    view.alpha = initialAlpha
    view.animate()
        .translationY(finalTranslationY)
        .alpha(finalAlpha)
        .setDuration(animationDuration)
        .setStartDelay(startDelay)
        .start()

}

fun AppCompatActivity.displayAlert(titleId: Int, messageId: Int) {
    MaterialAlertDialogBuilder(this)
        .setTitle(titleId)
        .setMessage(messageId)
        .setCancelable(false)
        .setPositiveButton(android.R.string.ok, null)
        .show()
}

fun AppCompatActivity.displayAlertAndFinishActivity(titleId: Int, messageId: Int) {
    MaterialAlertDialogBuilder(this)
        .setTitle(titleId)
        .setMessage(messageId)
        .setCancelable(false)
        .setPositiveButton(android.R.string.ok) { _, _ ->
            this.finish()
        }
        .show()
}

/**
 * Hide keyboard when user clicks outside edit text
 */
fun EditText.hideKeyboardWhenClickOutside(context: Context?) {
    this.setOnFocusChangeListener { view, _ ->
        val inputMethodManager =
            context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}