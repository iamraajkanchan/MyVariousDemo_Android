package com.example.filesystemdemo.views.activities

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.filesystemdemo.databinding.ActivityViewPagerDemoWithFragmentBinding
import com.example.filesystemdemo.views.fragments.ViewPagerDemoFragment

class ViewPagerDemoActivityWithFragment : FragmentActivity() {

    private lateinit var binding: ActivityViewPagerDemoWithFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerDemoWithFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragment = ViewPagerDemoFragment.newInstance()
        supportFragmentManager.beginTransaction().add(binding.fcvFragmentContainer.id, fragment)
            .commit()
    }
}