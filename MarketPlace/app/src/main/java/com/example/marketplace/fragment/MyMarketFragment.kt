package com.example.marketplace.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.marketplace.databinding.FragmentMyMarketBinding

class MyMarketFragment : Fragment() {

    private lateinit var binding : FragmentMyMarketBinding
    private lateinit var fragment : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyMarketBinding.inflate(inflater, container, false)
        fragment = binding.root
        return fragment
    }
}