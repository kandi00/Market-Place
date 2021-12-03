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
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.databinding.FragmentProductDetailBinding
import com.example.marketplace.model.Product
import com.example.marketplace.viewmodels.ListViewModel
import com.example.marketplace.viewmodels.LoginViewModel

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
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var currentProduct : Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)
        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        currentProduct = listViewModel.getCurrentProduct()
        loginViewModel.getUserInfo(currentProduct.username)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        initializeElements()
        setValues()
        setListeners()
        return binding.root
    }

    private fun initializeElements(){
        imageOfProduct = binding.ivImageOfTheProduct
        profilePictureOfSeller = binding.ivProfilePictureOfSeller
        nameOfSeller = binding.tvNameOfSeller
        title = binding.tvTitle
        pricePerAmount = binding.tvPricePerAmount
        shortDescription = binding.tvShortDescription
    }

    @SuppressLint("SetTextI18n")
    private fun setValues(){
        nameOfSeller.text = currentProduct.username
        title.text = currentProduct.title
        pricePerAmount.text = "${currentProduct.price_per_unit} ${currentProduct.price_type} / ${currentProduct.amount_type}"
        shortDescription.text = currentProduct.description
    }

    private fun setListeners(){
        profilePictureOfSeller.setOnClickListener{
            findNavController().navigate(R.id.action_productDetailFragment_to_profileFragment)
        }

        nameOfSeller.setOnClickListener{
            findNavController().navigate(R.id.action_productDetailFragment_to_profileFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}