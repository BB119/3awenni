package com.bbexcellence.a3awenni.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.bbexcellence.a3awenni.R
import com.bbexcellence.a3awenni.login.LoginActivity


class ThirdScreenFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
        enterTransition = inflater.inflateTransition(R.transition.slide_left)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third_screen, container, false)

        view.findViewById<Button>(R.id.screen_3_finish_button).setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            onBoardingFinished()
        }

        return view
    }

    private fun onBoardingFinished() {
        val sharedPref =
            requireActivity().getSharedPreferences(ON_BOARDING_SHARED_PREF, Context.MODE_PRIVATE)
                ?: return
        val edit = sharedPref.edit()
        edit.putBoolean(ON_BOARDING_FINISHED, true)
        edit.apply()
    }
}