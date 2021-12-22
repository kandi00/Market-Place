package com.example.marketplace.fragment.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.marketplace.R
import com.example.marketplace.databinding.FragmentBaseMarketItemBinding
import com.example.marketplace.util.Constants
import com.example.marketplace.viewmodels.ListViewModel
import com.google.android.material.textfield.TextInputEditText

abstract class BaseMarketItemFragment<VB : ViewBinding> : Fragment() {

    protected var _binding : VB? = null
    protected val binding get() = _binding!!
    private lateinit var fragmentBaseMarketItemBinding : FragmentBaseMarketItemBinding
    protected lateinit var listViewModel: ListViewModel
    protected lateinit var productDetailImage : ImageView
    protected lateinit var switch : Switch
    protected lateinit var productTitle : TextInputEditText
    protected lateinit var autoCompleteTextView1 : AutoCompleteTextView
    protected lateinit var autoCompleteTextView2 : AutoCompleteTextView
    protected lateinit var productPrice : TextInputEditText
    protected lateinit var productTotalAmount : TextInputEditText
    protected lateinit var productDescription : TextInputEditText
    protected lateinit var button : Button
    protected var flag = true

    abstract fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onCreateViewBinding(inflater, container)
        fragmentBaseMarketItemBinding = FragmentBaseMarketItemBinding.bind(binding.root)
        initializeElements()
        setListeners()
        return binding.root
    }

    private fun initializeElements(){
        productDetailImage = fragmentBaseMarketItemBinding.ivAddProductDetailImage
        switch = fragmentBaseMarketItemBinding.switch1
        productTitle = fragmentBaseMarketItemBinding.etAddProductDetailTitle1
        autoCompleteTextView1 = fragmentBaseMarketItemBinding.autoCompleteTextView1
        autoCompleteTextView2 = fragmentBaseMarketItemBinding.autoCompleteTextView2
        productPrice =  fragmentBaseMarketItemBinding.etAddProductDetailPriceAmount1
        productTotalAmount = fragmentBaseMarketItemBinding.etAddProductDetailTotalAmount1
        productDescription = fragmentBaseMarketItemBinding.etAddProductDetailDescription1
        button = fragmentBaseMarketItemBinding.launchMyFareButton

        switch.textOff = getString(R.string.inactive)
        switch.textOn = getString(R.string.active)
    }

    protected fun setAutoCompleteTextViewAdapters(){
        val currencies = resources.getStringArray(R.array.currencies)
        val arrayAdapter1 = ArrayAdapter(requireContext(), R.layout.dropdown_item, currencies)
        fragmentBaseMarketItemBinding.autoCompleteTextView1.setAdapter(arrayAdapter1)

        val units = resources.getStringArray(R.array.units)
        val arrayAdapter2 = ArrayAdapter(requireContext(), R.layout.dropdown_item, units)
        fragmentBaseMarketItemBinding.autoCompleteTextView2.setAdapter(arrayAdapter2)
    }

    private fun setListeners() {

        switch.setOnClickListener {
            if (switch.isChecked) {
                switch.text = Constants.ACTIVE
            } else {
                switch.text = Constants.INACTIVE
            }
        }
    }

    protected fun checkInputData(){
        flag = true
        if (productTitle.text.toString().length > 30 || productDescription.text.toString().length > 150 || productPrice.text.toString().length > 10) {
            Toast.makeText(context, getString(R.string.char_limit), Toast.LENGTH_SHORT).show()
            flag = false
        }

        if (productTitle.text.toString().isEmpty()) {
            flag = false; fragmentBaseMarketItemBinding.etAddProductDetailTitle.helperText = getString(R.string.mandatory_title)
        } else {
            fragmentBaseMarketItemBinding.etAddProductDetailTitle.helperText = ""
        }
        if (productDescription.text.toString().isEmpty()) {
            flag = false; fragmentBaseMarketItemBinding.etAddProductDetailDescription.helperText = getString(R.string.fair_price)
        } else {
            fragmentBaseMarketItemBinding.etAddProductDetailDescription.helperText = ""
        }
        if (productPrice.text.toString().isEmpty()) {
            flag = false; fragmentBaseMarketItemBinding.etAddProductDetailPriceAmount.helperText = getString(R.string.add_description)
        } else {
            fragmentBaseMarketItemBinding.etAddProductDetailPriceAmount.helperText = ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}