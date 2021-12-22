package com.example.marketplace.fragment.login

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragment: View
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var editTextLoginName: EditText
    private lateinit var editTextLoginPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button
    private lateinit var forgotPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = LoginViewModelFactory(Repository())
        loginViewModel =
            ViewModelProvider(requireActivity(), factory).get(LoginViewModel::class.java)
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

        loginViewModel.token.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_loginFragment_to_timelineFragment)
        }
        return fragment
    }

    private fun initializeElements() {
        editTextLoginName = binding.edittextNameLoginFragment
        editTextLoginPassword = binding.edittextPasswordLoginFragment
        loginButton = binding.buttonLoginFragment
        signUpButton = binding.signUpButtonLoginFragment
        forgotPassword = binding.tvClickHere
    }

    private fun setListeners() {
        loginButton.setOnClickListener {
            if (validateInputData()) {
                loginViewModel.user.value.let {
                    if (it != null) {
                        it.username = editTextLoginName.text.toString()
                        it.username = "testUser"
                    }
                    if (it != null) {
                        it.password = editTextLoginPassword.text.toString()
                        it.password = "C97jl0utAucTURZZzKwT"
                    }
                }
                lifecycleScope.launch {
                    try {
                        loginViewModel.login()
                    } catch (e: Exception) {
                        Snackbar.make(binding.root,"Incorrect user name or password!", Snackbar.LENGTH_LONG)
                                .show()
                    }
                }
            }
        }

        signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

    private fun validateInputData(): Boolean {
        editTextLoginName.error = null
        editTextLoginPassword.error = null

        when {
            editTextLoginName.text.toString().isEmpty() -> {
                editTextLoginName.error = getString(R.string.invalid_user_name)
                return false
            }
            editTextLoginPassword.text.toString().isEmpty() -> {
                editTextLoginPassword.error = getString(R.string.invalid_password)
                return false
            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}