package com.example.marketplace.fragment.product

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.activity.MainActivity
import com.example.marketplace.adapter.TimelineDataAdapter
import com.example.marketplace.databinding.FragmentTimelineBinding
import com.example.marketplace.model.Product
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.ListViewModel
import com.example.marketplace.viewmodels.ListViewModelFactory
import com.example.marketplace.viewmodels.LoginViewModel

class TimelineFragment : Fragment(), TimelineDataAdapter.OnItemClickListener {

    private var _binding: FragmentTimelineBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragment : View
    private lateinit var listViewModel: ListViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TimelineDataAdapter
    private lateinit var myActivity: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ListViewModelFactory(requireActivity(), Repository())
        listViewModel = ViewModelProvider(requireActivity(), factory).get(ListViewModel::class.java)
        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTimelineBinding.inflate(inflater, container, false)
        fragment = binding.root
        recyclerView = binding.recyclerView
        setupRecyclerView()
        listViewModel.products.observe(viewLifecycleOwner){
            adapter.setData(listViewModel.products.value as ArrayList<Product>)
            adapter.notifyDataSetChanged()
        }
        setupToolBar()
        return fragment
    }

    private fun setupRecyclerView(){
        adapter = TimelineDataAdapter(ArrayList<Product>(), this.requireContext(), this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(productId: String) {
        listViewModel.currentProductId = productId
        findNavController().navigate(R.id.action_timelineFragment_to_productDetailFragment)
    }

    override fun onOrderNowButtonClick(productId: String) {
        listViewModel.currentProductId = productId
        findNavController().navigate(R.id.action_timelineFragment_to_addOrderDetailFragment)
    }

    private fun setupToolBar(){
        /** setup profile icon */
        (myActivity as MainActivity).profileIcon.isVisible = true
        (myActivity as MainActivity).profileIcon.setOnMenuItemClickListener {
            /** Set current user's data for profile fragment */
            loginViewModel.randomUser.username = loginViewModel.user.value!!.username
            loginViewModel.randomUser.email = loginViewModel.user.value!!.email
            loginViewModel.randomUser.phone_number = loginViewModel.user.value!!.phone_number
            findNavController().navigate(R.id.action_timelineFragment_to_profileFragment)
            true
        }

        /** setup search icon and view */
        (myActivity as MainActivity).searchIcon.isVisible = true
        (myActivity as MainActivity).searchIcon.setOnMenuItemClickListener{
            (myActivity as MainActivity).searchView.visibility = View.VISIBLE
            (myActivity as MainActivity).toolBar.visibility = View.GONE
            (myActivity as MainActivity).searchView.isIconified = false
            return@setOnMenuItemClickListener true
        }

        (myActivity as MainActivity).searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(query: String): Boolean {
                filter(query)
                return true
            }
        })

        (myActivity as MainActivity).searchView.setOnCloseListener {
            (myActivity as MainActivity).searchView.visibility = View.GONE
            (myActivity as MainActivity).toolBar.visibility = View.VISIBLE
            true
        }
    }

    private fun filter(searchTerm: String) {
        val list = ArrayList<Product>()
        list.addAll(listViewModel.products.value!!.filter{ it.title.contains(searchTerm, true) })
        adapter.setData(list)
        adapter.notifyDataSetChanged()
    }

    override fun onPause() {
        (myActivity as MainActivity).searchIcon.isVisible = false
        (myActivity as MainActivity).profileIcon.isVisible = false
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}