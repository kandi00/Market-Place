package com.example.marketplace.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.adapter.TimelineDataAdapter
import com.example.marketplace.databinding.FragmentTimelineBinding
import com.example.marketplace.model.Product
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.ListViewModel
import com.example.marketplace.viewmodels.ListViewModelFactory

class TimelineFragment : Fragment(), TimelineDataAdapter.OnItemClickListener {

    private var _binding: FragmentTimelineBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragment : View
    private lateinit var listViewModel: ListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TimelineDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ListViewModelFactory(Repository())
        listViewModel = ViewModelProvider(requireActivity(), factory).get(ListViewModel::class.java)
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
        return fragment
    }

    private fun setupRecyclerView(){
        adapter = TimelineDataAdapter(ArrayList<Product>(), this.requireContext(), this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        listViewModel.currentProductPosition = position
        findNavController().navigate(R.id.action_timelineFragment_to_productDetailFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}