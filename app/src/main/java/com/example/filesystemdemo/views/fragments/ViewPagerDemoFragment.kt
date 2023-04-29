package com.example.filesystemdemo.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filesystemdemo.R
import com.example.filesystemdemo.databinding.FragmentViewPagerDemoBinding

class ViewPagerDemoFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerDemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ViewPagerDemoFragment()
    }
}