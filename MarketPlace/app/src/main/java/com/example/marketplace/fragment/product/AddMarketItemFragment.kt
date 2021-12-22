package com.example.marketplace.fragment.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.example.marketplace.databinding.FragmentAddMarketItemBinding
import com.google.android.material.snackbar.Snackbar

class AddMarketItemFragment : BaseMarketItemFragment<FragmentAddMarketItemBinding>() {

    override fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentAddMarketItemBinding.inflate(inflater, container, false)
    }

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

            checkInputData()

            if(flag) {
                try {
                    listViewModel.addProduct( productTitle.text.toString(),
                                            productDescription.text.toString(),
                                            productPrice.text.toString(),
                                            productTotalAmount.text.toString(),
                                            switch.isChecked,
                                            5.0,
                                            autoCompleteTextView2.text.toString(),
                                            autoCompleteTextView1.text.toString())

                    Snackbar.make(binding.root, "Item added successfully", Snackbar.LENGTH_LONG)
                            .show()
                } catch (e: Exception) {
                    Log.d("AddDetailMarketFragment", "add item exception: $e")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}