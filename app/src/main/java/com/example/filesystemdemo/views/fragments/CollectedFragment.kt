package com.example.filesystemdemo.views.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filesystemdemo.R
import com.example.filesystemdemo.databinding.FragmentCollectedBinding
import com.example.filesystemdemo.model.CartModel
import com.example.filesystemdemo.views.adapters.CartAdapter
import com.example.filesystemdemo.views.interfaces.ICartSelected

private const val COLLECTED_LIST_PARAM = "collected list param"

class CollectedFragment : Fragment(), ICartSelected {

    private lateinit var binding: FragmentCollectedBinding
    private lateinit var collectedList: ArrayList<CartModel>
    private lateinit var adapter: CartAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            collectedList = it.getParcelableArrayList(COLLECTED_LIST_PARAM)
                ?: emptyList<CartModel>() as ArrayList<CartModel>
        }
        adapter = CartAdapter(collectedList, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectedBinding.inflate(inflater, container, false)
        binding.rvCollectedList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvCollectedList.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    override fun selectedCart(cart: CartModel) {
        // This method is not implemented because the checkboxes are invincible in this fragment.
    }

    override fun removedCart(cart: CartModel) {
        // This method is not implemented because the checkboxes are invincible in this fragment.
    }

    companion object {
        fun newInstance(collectedList: ArrayList<CartModel>): CollectedFragment =
            CollectedFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(COLLECTED_LIST_PARAM, collectedList)
                }
            }
    }
}