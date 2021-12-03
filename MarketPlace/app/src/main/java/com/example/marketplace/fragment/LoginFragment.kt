package com.example.marketplace.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.databinding.FragmentLoginBinding
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.LoginViewModel
import com.example.marketplace.viewmodels.LoginViewModelFactory
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragment : View
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var editTextLoginName : EditText
    private lateinit var editTextLoginPassword : EditText
    private lateinit var loginButton : Button
    private lateinit var signUpButton : Button
    private lateinit var forgotPassword : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = LoginViewModelFactory(this.requireContext(), Repository())
        loginViewModel = ViewModelProvider(requireActivity(), factory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        fragment = binding.root
        initializeElements()
        setListeners()

        loginViewModel.token.observe(viewLifecycleOwner){
            //Log.d("xxx", "navigate to list")
            findNavController().navigate(R.id.action_loginFragment_to_timelineFragment)
        }
        return fragment
    }

    private fun initializeElements(){
        editTextLoginName = binding.edittextNameLoginFragment
        editTextLoginPassword = binding.edittextPasswordLoginFragment
        loginButton = binding.buttonLoginFragment
        signUpButton = binding.signUpButtonLoginFragment
        forgotPassword = binding.tvClickHere
    }

    private fun setListeners(){
        loginButton.setOnClickListener {
            loginViewModel.user.value.let {
                if (it != null) {
                    //it.username = editTextLoginName.text.toString()
                    it.username="testUser"
                }
                if (it != null) {
//                    it.password = editTextLoginPassword.text.toString()
                    it.password = "testuser123"
                }
            }
            lifecycleScope.launch {
                loginViewModel.login()
            }
        }

        signUpButton.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        forgotPassword.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}