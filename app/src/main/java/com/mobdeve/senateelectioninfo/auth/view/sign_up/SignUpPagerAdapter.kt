package com.mobdeve.senateelectioninfo.auth.view.sign_up

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SignUpPagerAdapter(
    fragmentActivity: FragmentActivity
): FragmentStateAdapter(fragmentActivity) {
    private val pages = listOf(
        SignUpPage1Fragment::class.java,
        SignUpPage2Fragment::class.java,
        SignUpPage3Fragment::class.java
    )

    private val fragments = mutableListOf<Fragment>()

    override fun createFragment(position: Int): Fragment {
        val fragment = pages[position].newInstance()
        fragments.add(fragment)
        return fragment
    }

    override fun getItemCount(): Int {
        return pages.size
    }

    fun getFragment(position: Int): Fragment {
        return fragments[position]
    }
}