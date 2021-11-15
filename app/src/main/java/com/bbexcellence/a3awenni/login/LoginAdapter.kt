package com.bbexcellence.a3awenni.login

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.bbexcellence.a3awenni.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

class LoginAdapter(private val context: Context, fm: FragmentManager) :
FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return TAB_TITLES.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return LoginTabFragment()
            1 -> return SignupTabFragment()
        }
        return LoginTabFragment()
    }
}