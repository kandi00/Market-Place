package com.example.marketplace.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marketplace.databinding.FragmentMyMarketBinding

class MyMarketFragment : Fragment() {

    private var _binding: FragmentMyMarketBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragment : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyMarketBinding.inflate(inflater, container, false)
        fragment = binding.root
        return fragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}