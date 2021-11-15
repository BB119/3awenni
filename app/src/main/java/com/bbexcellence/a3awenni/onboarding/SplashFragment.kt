package com.bbexcellence.a3awenni.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.bbexcellence.a3awenni.MainActivity
import com.bbexcellence.a3awenni.R
import com.bbexcellence.a3awenni.login.LoginActivity


const val ON_BOARDING_FINISHED = "Finish"
const val ON_BOARDING_SHARED_PREF = "onBoarding"

class SplashFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        // Inflate the layout for this fragment

        if (onBoardingFinished()) {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }, 2000)
        }
        return view
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref =
            requireActivity().getSharedPreferences(ON_BOARDING_SHARED_PREF, Context.MODE_PRIVATE)

        return sharedPref.getBoolean(ON_BOARDING_FINISHED, false)
    }
}