package com.example.marketplace.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.marketplace.R
import com.example.marketplace.databinding.FragmentAddDetailMarketItemBinding
import com.example.marketplace.model.NewProduct
import com.example.marketplace.util.Constants
import com.example.marketplace.viewmodels.ListViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class AddDetailMarketItemFragment : Fragment() {

    private var _binding: FragmentAddDetailMarketItemBinding ? = null
    private val binding get() = _binding!!
    private lateinit var listViewModel: ListViewModel
    private lateinit var fragment : View
    private lateinit var productDetailImage : ImageView
    private lateinit var productDetailUploadImage : ImageView
    private lateinit var switch : Switch
    private lateinit var productTitle : TextInputEditText
    private lateinit var autoCompleteTextView1 : AutoCompleteTextView
    private lateinit var autoCompleteTextView2 : AutoCompleteTextView
    private lateinit var productPrice : TextInputEditText
    private lateinit var productTotalAmount : TextInputEditText
    private lateinit var productDescription : TextInputEditText
    private lateinit var button : Button

    private val getImage = registerForActivityResult(
        ActivityResultContracts.GetContent()) {
        productDetailImage.setImageURI(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddDetailMarketItemBinding.inflate(inflater, container, false)

        initializeElements()
        setListeners()

        fragment = binding.root
        return fragment
    }

    private fun initializeElements(){
        productDetailImage = binding.ivAddProductDetailImage
        productDetailUploadImage = binding.ivAddProductDetailUploadImage
        switch = binding.switch1
        productTitle = binding.etAddProductDetailTitle1
        autoCompleteTextView1 = binding.autoCompleteTextView1
        autoCompleteTextView2 = binding.autoCompleteTextView2
        productPrice =  binding.etAddProductDetailPriceAmount1
        productTotalAmount = binding.etAddProductDetailTotalAmount1
        productDescription = binding.etAddProductDetailDescription1
        button = binding.launchMyFareButton

        switch.textOff = "Inactive"
        switch.textOn = "Active"

        val currencies = resources.getStringArray(R.array.currencies)
        val arrayAdapter1 = ArrayAdapter(requireContext(), R.layout.dropdown_item, currencies)
        binding.autoCompleteTextView1.setAdapter(arrayAdapter1)

        val units = resources.getStringArray(R.array.units)
        val arrayAdapter2 = ArrayAdapter(requireContext(), R.layout.dropdown_item, units)
        binding.autoCompleteTextView2.setAdapter(arrayAdapter2)
    }

    private fun setListeners(){

        productDetailUploadImage.setOnClickListener{
            getImage.launch("image/*")
            productDetailUploadImage.visibility = View.INVISIBLE
        }

        switch.setOnClickListener{
            if(switch.isChecked){
                switch.text = Constants.ACTIVE
            } else{
                switch.text = Constants.INACTIVE
            }
        }

        button.setOnClickListener{

            var flag = true
            if(productTitle.text.toString().length > 30 || productDescription.text.toString().length > 150 || productPrice.text.toString().length > 10){
                Toast.makeText(context, "Character limit exceeded!", Toast.LENGTH_SHORT).show()
                flag = false
            }

            if(productTitle.text.toString().isEmpty()) { flag = false; binding.etAddProductDetailTitle.helperText = "Your fare needs to have a title" } else { binding.etAddProductDetailTitle.helperText = "" }
            if(productDescription.text.toString().isEmpty()) { flag = false; binding.etAddProductDetailDescription.helperText = "Give your fare a fair price" } else { binding.etAddProductDetailDescription.helperText = "" }
            if(productPrice.text.toString().isEmpty()) { flag = false; binding.etAddProductDetailPriceAmount.helperText = "Describe your product, like its origin, made for..." } else { binding.etAddProductDetailPriceAmount.helperText = "" }

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
                        Snackbar.make(fragment, "Item added successfully", Snackbar.LENGTH_LONG)
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