package com.example.filesystemdemo.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.filesystemdemo.R
import com.example.filesystemdemo.model.CartModel
import com.example.filesystemdemo.databinding.CartItemBinding
import com.example.filesystemdemo.views.interfaces.ICartSelected

class CartAdapter(
    private val cartList: List<CartModel>,
    private val cartSelectedCallback: ICartSelected
) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartList[position])
    }

    override fun getItemCount(): Int = cartList.size

    override fun onViewRecycled(holder: CartViewHolder) {
        super.onViewRecycled(holder)
        holder.resetCheckBox()
    }

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CartModel) {
            binding.tvItemValue.text = data.itemName
            binding.tvItemPrice.text = "$${data.itemPrice}"
            if (data.isPaid) {
                binding.chkItemChecked.visibility = View.GONE
            } else {
                binding.chkItemChecked.visibility = View.VISIBLE
            }
            binding.chkItemChecked.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    binding.chkItemChecked.setBackgroundResource(R.drawable.icon_check_box_checked)
                    cartSelectedCallback.selectedCart(data)
                    data.isPaid = true
                } else {
                    binding.chkItemChecked.background = null
                    cartSelectedCallback.removedCart(data)
                    data.isPaid = false
                }
            }
        }

        fun resetCheckBox() {
            binding.chkItemChecked.setOnCheckedChangeListener(null)
            binding.chkItemChecked.isChecked = false
        }
    }
}