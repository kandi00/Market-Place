package com.example.marketplace.fragment.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marketplace.databinding.FragmentEditDetailMarketItemBinding
import com.example.marketplace.model.UpdateProduct
import com.example.marketplace.util.Constants
import com.google.android.material.snackbar.Snackbar

class EditDetailMarketItemFragment : BaseMarketItemFragment<FragmentEditDetailMarketItemBinding>() {
    private var savedView: View? = null

    override fun onCreateViewBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentEditDetailMarketItemBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setUIElementsValues()
    }

    private fun setUIElementsValues(){
        val currentProduct = listViewModel.getCurrentProduct()
        switch.isChecked = currentProduct.is_active
        if(switch.isChecked){
            switch.text = Constants.ACTIVE
        } else{
            switch.text = Constants.INACTIVE
        }

        productTitle.setText(currentProduct.title)
        autoCompleteTextView1.setText(currentProduct.price_type)
        autoCompleteTextView2.setText(currentProduct.amount_type)

        setAutoCompleteTextViewAdapters()

        productPrice.setText(currentProduct.price_per_unit)
        productTotalAmount.setText(currentProduct.units)
        productDescription.setText(currentProduct.description)
    }

    private fun setListeners(){

        button.setOnClickListener{

           checkInputData()

            if(flag) {
                try{
                  listViewModel.updateProduct(UpdateProduct(productPrice.text.toString(),
                                                          switch.isChecked,
                                                          productTitle.text.toString(),
                                                          autoCompleteTextView2.text.toString(),
                                                          autoCompleteTextView1.text.toString(),
                                                          productTotalAmount.text.toString(),
                                                          productDescription.text.toString()))
                    Snackbar.make(requireView(), "Product updated successfully", Snackbar.LENGTH_LONG)
                            .show()
                } catch(e : Exception) {
                    Log.d("EditMarketItem", "Exception while updating product: $e")
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}