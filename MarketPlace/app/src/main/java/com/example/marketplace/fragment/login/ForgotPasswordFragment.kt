package com.example.marketplace.fragment.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.marketplace.databinding.FragmentForgotPasswordBinding
import com.example.marketplace.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class ForgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragment: View
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var email: EditText
    private lateinit var emailMeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        fragment = binding.root
        initializeElements()
        setListeners()
        return fragment
    }

    private fun initializeElements() {
        email = binding.tvResetEmail
        emailMeButton = binding.btnEmailMe
    }

    private fun setListeners() {
        emailMeButton.setOnClickListener {
            loginViewModel.user.value.let {
                if (it != null) {
                    it.email = email.text.toString()
                }
            }

            loginViewModel.resetPassword()
            Snackbar.make(binding.root, "Check your email!", Snackbar.LENGTH_LONG)
                    .show()
        }
    }

}