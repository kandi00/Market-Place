package com.example.marketplace.fragment
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.marketplace.databinding.FragmentProductDetailBinding
import com.example.marketplace.viewmodels.ListViewModel

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var imageOfProduct : ImageView
    private lateinit var profilePictureOfSeller : ImageView
    private lateinit var nameOfSeller : TextView
    private lateinit var title : TextView
    private lateinit var pricePerAmount : TextView
    private lateinit var shortDescription : TextView
    private lateinit var listViewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        listViewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)
        initializeElements()
        return binding.root
    }

    private fun initializeElements(){
        imageOfProduct = binding.ivImageOfTheProduct
        profilePictureOfSeller = binding.ivProfilePictureOfSeller
        nameOfSeller = binding.tvNameOfSeller
        title = binding.tvTitle
        pricePerAmount = binding.tvPricePerAmount
        shortDescription = binding.tvShortDescription

//        val current = Product(3.5, "L", "RON", "manyi_1635877013127", "manyi",
//        true, "50","1","60 fokos","szilvapálinka", listOf(),1635877013218,)
        val current = listViewModel.getCurrentProduct()
        nameOfSeller.text = current.username
        title.text = current.title
        pricePerAmount.text = "${current.price_per_unit} ${current.price_type} / ${current.amount_type}"
        shortDescription.text = current.description
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}