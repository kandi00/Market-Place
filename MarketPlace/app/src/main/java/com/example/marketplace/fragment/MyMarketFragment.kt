package com.example.marketplace.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.adapter.MyMarketDataAdapter
import com.example.marketplace.databinding.FragmentMyMarketBinding
import com.example.marketplace.model.Product
import com.example.marketplace.viewmodels.ListViewModel
import com.example.marketplace.viewmodels.LoginViewModel

class MyMarketFragment : Fragment(), MyMarketDataAdapter.OnItemClickListener, MyMarketDataAdapter.OnItemLongClickListener {

    private var _binding: FragmentMyMarketBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragment : View
    private lateinit var listViewModel: ListViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyMarketDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel = ViewModelProvider(requireActivity()).get(ListViewModel::class.java)
        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyMarketBinding.inflate(inflater, container, false)
        fragment = binding.root
        recyclerView = binding.recyclerViewMyMarket
        setupRecyclerView()
        listViewModel.products.observe(viewLifecycleOwner){
            adapter.setData(listViewModel.products.value!!.filter { it.username == loginViewModel.user.value!!.username } as ArrayList<Product>)
            adapter.notifyDataSetChanged()
        }
        setListeners()
        return fragment
    }

    private fun setupRecyclerView(){
        adapter = MyMarketDataAdapter(ArrayList<Product>(), this.requireContext(), this, this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        /** Get the index of product in original product list
        products.indexOf(myProduct[position]) **/
        val pos = listViewModel.products.value!!.indexOf(listViewModel.products.value!!.filter { it.username == loginViewModel.user.value!!.username }[position])
        listViewModel.currentProductPosition = pos
        findNavController().navigate(R.id.action_myMarketFragment_to_productDetailFragment)
    }

    override fun onItemLongClick(productId : String) {
        AlertDialog.Builder(context)
            .setTitle("Delete")
            .setMessage("Are you sure you want to delete this product?")
            .setPositiveButton("Yes") { _, _ -> listViewModel.removeProduct(productId)
                                                    adapter.setData(listViewModel.products.value!!.filter { it.username == loginViewModel.user.value!!.username } as ArrayList<Product>)
                                                    adapter.notifyDataSetChanged()
                                                    Toast.makeText(activity, "Product successfully removed!", Toast.LENGTH_SHORT).show() }
            .setNegativeButton("No"){ _, _ ->}
            .create()
            .show()
    }

    private fun setListeners(){
        binding.addButton.setOnClickListener{
            findNavController().navigate(R.id.action_myMarketFragment_to_addDetailMarketFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}