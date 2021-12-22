package com.example.marketplace.fragment.register

import android.os.Bundle
import android.util.Log
import android.util.Patterns
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
import com.example.marketplace.databinding.FragmentRegisterBinding
import com.example.marketplace.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragment: View
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var registerUserName: EditText
    private lateinit var registerEmail: EditText
    private lateinit var registerPassword: EditText
    private lateinit var registerPhoneNumber: EditText
    private lateinit var registerButton: Button
    private lateinit var login: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        fragment = binding.root

        initializeElements()
        setListeners()

        loginViewModel.registrationCode.observe(viewLifecycleOwner) { registrationCode ->
            if (registrationCode == 200) {
                /** Activate registered user */
                loginViewModel.activate(registerUserName.text.toString())
            }
        }

        loginViewModel.activation.observe(viewLifecycleOwner) { activation ->
            if (activation) {
                Log.i("activation", activation.toString())
                /** Registered and activated user has to log in */
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }

        return fragment
    }

    private fun initializeElements() {
        registerUserName = binding.tvRegisterUserName
        registerEmail = binding.tvRegisterEmail
        registerPassword = binding.tvRegisterPassword
        registerPhoneNumber = binding.tvRegisterPhoneNumber
        registerButton = binding.buttonRegister
        login = binding.tvLogIn
    }

    private fun setListeners() {
        login.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        registerButton.setOnClickListener {
            if (validateInputData()) {
                lifecycleScope.launch {
                    try {
                        loginViewModel.register(
                            registerUserName.text.toString(),
                            registerEmail.text.toString(),
                            registerPassword.text.toString(),
                            registerPhoneNumber.text.toString().toLong()
                        )
                    } catch (e: Exception) {
                        Snackbar.make(binding.root,"Username , email or phone number already used!", Snackbar.LENGTH_LONG)
                                .show()
                    }
                }
            }
        }
    }

    private fun validateInputData(): Boolean {
        registerUserName.error = null
        registerEmail.error = null
        registerPassword.error = null
        registerPhoneNumber.error = null

        when {
            registerUserName.text.toString().isEmpty() -> {
                registerUserName.error = getString(R.string.invalid_user_name)
                return false
            }
            registerEmail.text.toString()
                .isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(registerEmail.text.toString())
                .matches() -> {
                registerEmail.error = getString(R.string.invalid_email)
                return false
            }
            registerPassword.text.toString().isEmpty() -> {
                registerPassword.error = getString(R.string.invalid_password)
                return false
            }
            registerPhoneNumber.text.toString()
                .isEmpty() || !Patterns.PHONE.matcher(registerPhoneNumber.text.toString())
                .matches() -> {
                registerPhoneNumber.error = getString(R.string.invalid_phone_number)
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
