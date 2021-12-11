package com.example.marketplace.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.marketplace.R
import com.example.marketplace.databinding.FragmentOrderDetailBinding
import com.example.marketplace.model.Order
import com.example.marketplace.viewmodels.ListViewModel

class OrderDetailFragment : Fragment() {

    private var _binding: FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var listViewModel: ListViewModel
    private lateinit var imageOfProduct : ImageView
    private lateinit var productTitle : TextView
    private lateinit var sellerName : TextView
    private lateinit var status : TextView
    private lateinit var pricePerAmount : TextView
    private lateinit var amount : TextView
    private lateinit var totalPrice : TextView
    private lateinit var description : TextView
    private lateinit var currentOrder : Order


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)
        currentOrder = listViewModel.getCurrentOrder()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        initializeElements()
        setValues()
        return binding.root
    }

    private fun initializeElements(){
        productTitle = binding.tvOrderDetailProductTitle
        sellerName = binding.tvOrderDetailOrderSellerName
        status = binding.tvOrderDetailOrderStatus
        pricePerAmount = binding.tvOrderDetailOrderPricePerAmount
        amount = binding.tvOrderDetailAmount
        totalPrice = binding.tvOrderDetailOrderTotalPrice
        description = binding.tvOrderDetailDescription
    }

    private fun setValues(){
        productTitle.text = currentOrder.title
        sellerName.text = getString(R.string.seller, currentOrder.owner_username)
        status.text = currentOrder.status
        pricePerAmount.text = currentOrder.price_per_unit
        amount.text = currentOrder.units
        try{
            totalPrice.text = (currentOrder.price_per_unit.toInt() * currentOrder.units.toInt()).toString()}
        catch(e : Exception){
            totalPrice.text = currentOrder.units
        }
        description.text = currentOrder.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}