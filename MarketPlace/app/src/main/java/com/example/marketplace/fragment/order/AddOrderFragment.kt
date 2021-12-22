package com.example.marketplace.fragment.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.marketplace.R
import com.example.marketplace.databinding.FragmentAddOrderBinding
import com.example.marketplace.model.AddOrder
import com.example.marketplace.model.Product
import com.example.marketplace.viewmodels.ListViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class AddOrderFragment : Fragment() {

    private var _binding: FragmentAddOrderBinding? = null
    private val binding get() = _binding!!
    private lateinit var listViewModel: ListViewModel
    private lateinit var fragment : View
    private lateinit var imageOfProduct : ImageView
    private lateinit var productTitle : TextView
    private lateinit var sellerName : TextView
    private lateinit var status : TextView
    private lateinit var pricePerAmount : TextView
    private lateinit var amount : TextInputLayout
    private lateinit var description : TextInputLayout
    private lateinit var totalPrice : TextView
    private lateinit var sendMyOrderButton : Button
    private lateinit var currentProduct : Product
    private var flag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)
        currentProduct = listViewModel.getCurrentProduct()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddOrderBinding.inflate(inflater, container, false)
        fragment = binding.root

        initializeElements()
        setListeners()
        setValues()

        return fragment
    }

    private fun initializeElements(){
        imageOfProduct = binding.tvAddOrderDetailImageOfProduct
        productTitle = binding.tvAddOrderDetailProductTitle
        sellerName = binding.tvAddOrderDetailOrderSellerName
        status = binding.tvAddOrderDetailOrderStatus
        pricePerAmount = binding.tvAddOrderDetailOrderPricePerAmount
        amount = binding.tvAddOrderDetailAmount
        description = binding.tvAddOrderDetailOrderDescription
        totalPrice = binding.tvAddOrderDetailOrderTotalPrice
        sendMyOrderButton = binding.sendMyOrderButton
    }

    private fun setListeners(){
        sendMyOrderButton.setOnClickListener {
            if (!amount.editText?.text.isNullOrEmpty()){
                if (flag) {
                    val order = AddOrder(productTitle.text.toString(),
                        description.editText?.text.toString(),
                        currentProduct.price_per_unit,
                        amount.editText?.text.toString(),
                        status.text.toString(),
                        currentProduct.username,
                        "link")
                    Log.i("order", order.toString())
                    try{
                        listViewModel.addOrder(order)
                        Snackbar.make(binding.root, "Order completed!", Snackbar.LENGTH_LONG)
                                .show()
                    } catch(e : Exception) {
                        Log.d("AddOrderFragment", "Exception while adding new order: $e")
                    }
                }
            } else {
                amount.helperText = "Give a valid number!"
            }

        }

        amount.editText?.addTextChangedListener {
            try {
                val res = currentProduct.price_per_unit.toInt() * amount.editText?.text.toString().toInt()
                totalPrice.text = getString(R.string.total_price, res.toString())
                amount.helperText = ""
                flag = true
            } catch (e : Exception){
                amount.helperText = getString(R.string.add_valid_number)
                totalPrice.text = getString(R.string.total_price, "0")
                flag = false
            }
        }
    }

    private fun setValues(){
        productTitle.text = currentProduct.title
        sellerName.text = getString(R.string.seller, currentProduct.username)
        pricePerAmount.text = getString(R.string.price_per_amount, currentProduct.price_per_unit, currentProduct.price_type, currentProduct.amount_type)
        totalPrice.text = getString(R.string.total_price, "0")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}