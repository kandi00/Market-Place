package com.example.marketplace.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.marketplace.databinding.FragmentAddDetailMarketItemBinding
import com.example.marketplace.model.NewProduct
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class AddDetailMarketItemFragment : BaseMarketItemFragment<FragmentAddDetailMarketItemBinding>() {

    override fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentAddDetailMarketItemBinding.inflate(inflater, container, false)
    }

//    private var _binding: FragmentAddDetailMarketItemBinding? = null
//    private val binding get() = _binding!!
    private lateinit var productDetailUploadImage : ImageView

    private val getImage = registerForActivityResult(
        ActivityResultContracts.GetContent()) {
        productDetailImage.setImageURI(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeElements()
        setListeners()
    }

    private fun initializeElements(){
        productDetailUploadImage = binding.ivAddProductDetailUploadImage
        setAutoCompleteTextViewAdapters()
    }

    private fun setListeners(){

        productDetailUploadImage.setOnClickListener{
            getImage.launch("image/*")
            productDetailUploadImage.visibility = View.INVISIBLE
        }

        button.setOnClickListener{

            checkData()

            if(flag) {
                val product = NewProduct(
                    //listOf(),
                    productTitle.text.toString(),
                    productDescription.text.toString(),
                    productPrice.text.toString(),
                    productTotalAmount.text.toString(),
                    switch.isChecked,
                    5.0,
                    autoCompleteTextView2.text.toString(),
                    autoCompleteTextView1.text.toString()
                )
                Log.i("product", product.toString())

                lifecycleScope.launch {
                    try {
                        listViewModel.addProduct(product)
                        Snackbar.make(binding.root, "Item added successfully", Snackbar.LENGTH_LONG)
                                .show()
                    } catch (e: Exception) {
                        Log.d("xxx", "add item exception: $e")
                    }
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}