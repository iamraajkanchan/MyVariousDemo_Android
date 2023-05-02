package com.example.filesystemdemo.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.filesystemdemo.databinding.FragmentViewPagerDemoBinding
import com.example.filesystemdemo.model.CartModel
import com.example.filesystemdemo.views.interfaces.ISubmitItem
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerDemoFragment : Fragment(), ISubmitItem {
    private lateinit var binding: FragmentViewPagerDemoBinding
    private val cartList: ArrayList<CartModel> = ArrayList()
    private lateinit var pendingList: ArrayList<CartModel>
    private lateinit var collectedList: ArrayList<CartModel>
    private lateinit var pendingFragment: NewPendingFragment
    private lateinit var collectedFragment: CollectedFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createCartList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerDemoBinding.inflate(inflater, container, false)
        configureViewPager()
        return binding.root
    }

    private fun createCartList() {
        cartList.add(CartModel(false, "Bottle", 3.00))
        cartList.add(CartModel(true, "Rug", 123.50))
        cartList.add(CartModel(false, "Lock", 12.12))
        cartList.add(CartModel(true, "Scissors", 7.24))
        cartList.add(CartModel(false, "Key Chain", 1.38))
    }

    private fun configureViewPager() {
        val adapter = ScreenSlidePagerAdapter(requireActivity())
        binding.viewPagerCartOptions.adapter = adapter
        TabLayoutMediator(binding.tabCartOptions, binding.viewPagerCartOptions) { tab, position ->
            when (position) {
                0 -> tab.text = "Pending"
                1 -> tab.text = "Collected"
            }
        }.attach()
        pendingList = cartList.filter { !it.isPaid } as ArrayList<CartModel>
        pendingFragment = NewPendingFragment(pendingList, this)
        collectedList = cartList.filter { it.isPaid } as ArrayList<CartModel>
        collectedFragment = CollectedFragment.newInstance(collectedList)
    }

    override fun addItemIntoCart(items: ArrayList<CartModel>) {
        pendingList.removeAll(items.toSet())
        pendingFragment.updateAdapter()
        binding.viewPagerCartOptions.setCurrentItem(1, true)
        collectedList.addAll(items)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ViewPagerDemoFragment()
    }

    inner class ScreenSlidePagerAdapter(myActivity: FragmentActivity) :
        FragmentStateAdapter(myActivity) {
        override fun getItemCount(): Int = 2
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> pendingFragment
                1 -> collectedFragment
                else -> Fragment()
            }
        }
    }
}