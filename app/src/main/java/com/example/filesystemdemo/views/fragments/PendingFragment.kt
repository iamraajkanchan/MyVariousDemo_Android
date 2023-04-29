package com.example.filesystemdemo.views.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filesystemdemo.databinding.FragmentPendingBinding
import com.example.filesystemdemo.model.CartModel
import com.example.filesystemdemo.views.activities.ViewPagerDemoActivity
import com.example.filesystemdemo.views.adapters.CartAdapter
import com.example.filesystemdemo.views.interfaces.ICartSelected
import com.example.filesystemdemo.views.interfaces.ISubmitItem

private const val PENDING_LIST_PARAM = "pending list param"

class PendingFragment : Fragment(), ICartSelected {

    private lateinit var binding: FragmentPendingBinding
    private lateinit var pendingList: ArrayList<CartModel>
    private val submitCartList = ArrayList<CartModel>()
    private lateinit var submitItemCallback: ISubmitItem
    private lateinit var adapter: CartAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        submitItemCallback = requireActivity() as ViewPagerDemoActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pendingList = it.getParcelableArrayList(PENDING_LIST_PARAM)
                ?: emptyList<CartModel>() as ArrayList<CartModel>
        }
        adapter = CartAdapter(pendingList, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPendingBinding.inflate(inflater, container, false)
        binding.rvPendingList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvPendingList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {
            submitItemCallback.addItemIntoCart(submitCartList)
            submitCartList.clear()
        }
    }

    fun updateAdapter() {
        adapter.notifyDataSetChanged()
    }

    override fun selectedCart(cart: CartModel) {
        submitCartList.add(cart)
    }

    override fun removedCart(cart: CartModel) {
        submitCartList.remove(cart)
    }

    companion object {
        fun newInstance(cartList: ArrayList<CartModel>): PendingFragment = PendingFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(PENDING_LIST_PARAM, cartList)
            }
        }
    }

}