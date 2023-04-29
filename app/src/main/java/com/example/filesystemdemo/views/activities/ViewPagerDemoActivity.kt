package com.example.filesystemdemo.views.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.filesystemdemo.databinding.ActivityViewPagerDemoBinding
import com.example.filesystemdemo.model.CartModel
import com.example.filesystemdemo.views.fragments.CollectedFragment
import com.example.filesystemdemo.views.fragments.PendingFragment
import com.example.filesystemdemo.views.interfaces.ISubmitItem
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerDemoActivity : FragmentActivity(), ISubmitItem {

    private lateinit var binding: ActivityViewPagerDemoBinding
    private val cartList: ArrayList<CartModel> = ArrayList()
    private lateinit var pendingList: ArrayList<CartModel>
    private lateinit var collectedList: ArrayList<CartModel>
    private lateinit var pendingFragment: PendingFragment
    private lateinit var collectedFragment: CollectedFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createCartList()
        configureViewPager()
    }

    override fun onStart() {
        super.onStart()
    }

    private fun createCartList() {
        cartList.add(CartModel(false, "Bottle", 3.00))
        cartList.add(CartModel(true, "Rug", 123.50))
        cartList.add(CartModel(false, "Lock", 12.12))
        cartList.add(CartModel(true, "Scissors", 7.24))
        cartList.add(CartModel(false, "Key Chain", 1.38))
    }

    private fun configureViewPager() {
        val adapter = ScreenSlidePagerAdapter(this@ViewPagerDemoActivity)
        binding.viewPagerCartOptions.adapter = adapter
        TabLayoutMediator(binding.tabCartOptions, binding.viewPagerCartOptions) { tab, position ->
            when (position) {
                0 -> tab.text = "Pending"
                1 -> tab.text = "Collected"
            }
        }.attach()
        pendingList = cartList.filter { !it.isPaid } as ArrayList<CartModel>
        pendingFragment = PendingFragment.newInstance(pendingList)
        collectedList = cartList.filter { it.isPaid } as ArrayList<CartModel>
        collectedFragment = CollectedFragment.newInstance(collectedList)
    }

    override fun addItemIntoCart(items: ArrayList<CartModel>) {
        pendingList.removeAll(items.toSet())
        pendingFragment.updateAdapter()
        collectedList.addAll(items)
    }

    inner class ScreenSlidePagerAdapter(myActivity: ViewPagerDemoActivity) :
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