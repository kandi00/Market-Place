package com.example.marketplace.fragment.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.adapter.MyFaresDataAdapter
import com.example.marketplace.databinding.FragmentMyFaresBinding
import com.example.marketplace.model.Order
import com.example.marketplace.viewmodels.ListViewModel
import com.example.marketplace.viewmodels.LoginViewModel
import com.google.android.material.tabs.TabLayout

class MyFaresFragment : Fragment(), MyFaresDataAdapter.OnItemClickListener {

    private var _binding: FragmentMyFaresBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragment : View
    private lateinit var listViewModel: ListViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyFaresDataAdapter
    private lateinit var tabLayout : TabLayout

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
        _binding = FragmentMyFaresBinding.inflate(inflater, container, false)
        fragment = binding.root
        recyclerView = binding.recyclerViewMyFares
        tabLayout = binding.myFaresTabLayout
        setupRecyclerView()

        //listViewModel.orders.observe(viewLifecycleOwner){
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if(tab?.position == 0){
                        adapter.setData(listViewModel.orders.value!!.filter { it.owner_username.trim{it == '\"'} == loginViewModel.user.value!!.username } as ArrayList<Order>, true)
                        adapter.notifyDataSetChanged()
                    } else {
                        adapter.setData(listViewModel.orders.value!!.filter { it.username == loginViewModel.user.value!!.username } as ArrayList<Order>, false)
                        adapter.notifyDataSetChanged()
                    }
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // Handle tab reselect
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    // Handle tab unselect
                }
            })
        //}
        return fragment
    }

    private fun setupRecyclerView(){
        adapter = MyFaresDataAdapter(listViewModel.orders.value!!.filter { it.owner_username.trim{it == '\"'} ==  loginViewModel.user.value!!.username} as ArrayList<Order>, true, this.requireContext(), this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(orderId: String) {
        listViewModel.currentOrderId = orderId
        findNavController().navigate(R.id.action_myFaresFragment_to_orderDetailFragment)
    }

    override fun onUpdateOrderClick(orderId: String, status: String) {
        listViewModel.updateOrder(orderId, status)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}