package com.example.marketplace.fragment.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.databinding.FragmentProfileBinding
import com.example.marketplace.viewmodels.LoginViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var fragment : View
    private lateinit var profilePicture : ImageView
    private lateinit var userEmail : TextView
    private lateinit var userName : TextView
    private lateinit var userPhoneNumber : TextView
    private lateinit var editProfileButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        fragment = binding.root
        loginViewModel.user.observe(viewLifecycleOwner){
            setValues()
        }
        initializeElements()
        setOnClickListeners()
        setValues()

        return fragment
    }

    private fun initializeElements(){
        profilePicture = binding.ivUserProfilePicture
        userEmail = binding.tvUserEmail
        userName = binding.tvUserName
        userPhoneNumber = binding.tvUserPhoneNumber
        editProfileButton = binding.ivEditProfileIcon
    }

    private fun setValues(){
        userEmail.text = loginViewModel.randomUser.email
        userName.text = loginViewModel.randomUser.username
        userPhoneNumber.text = loginViewModel.randomUser.phone_number
    }

    private fun setOnClickListeners(){
        userEmail.setOnClickListener{
            sendEmail()
        }

        userPhoneNumber.setOnClickListener{
            dialPhoneNumber(loginViewModel.randomUser.phone_number)
        }

        if (loginViewModel.user.value!!.username == loginViewModel.randomUser.username) {
            editProfileButton.visibility = View.VISIBLE
        }

        editProfileButton.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
    }

    private fun sendEmail(){
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(loginViewModel.randomUser.email)) // recipients
            putExtra(Intent.EXTRA_SUBJECT, "Order")
        }
        if (activity?.let { intent.resolveActivity(it.packageManager) } != null) {
            startActivity(intent)
        }
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (activity?.let { intent.resolveActivity(it.packageManager) } != null) {
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}