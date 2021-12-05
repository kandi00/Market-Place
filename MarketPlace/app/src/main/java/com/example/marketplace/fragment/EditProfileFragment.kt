package com.example.marketplace.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.marketplace.databinding.FragmentEditProfileBinding
import com.example.marketplace.viewmodels.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var fragment : View
    private lateinit var profilePicture : ImageView
    private lateinit var userEmail : TextView
    private lateinit var userName : TextView
    private lateinit var userPhoneNumber : TextView
    private lateinit var publishButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        fragment = binding.root

        initializeElements()
        loginViewModel.user.observe(viewLifecycleOwner){
            Log.i("modify", "modify")
            setValues()
        }
        setListeners()

        return fragment
    }

    private fun initializeElements(){
        profilePicture = binding.ivUserEditProfilePicture
        userEmail = binding.etEditProfileEmail
        userName = binding.etEditProfileUserName
        userPhoneNumber = binding.tvEditProfilePhoneNumber
        publishButton = binding.publishButton
    }

    private fun setValues(){
        userEmail.text = loginViewModel.user.value!!.email
        userName.text = loginViewModel.user.value!!.username
        userPhoneNumber.text = loginViewModel.user.value!!.phone_number
    }

    private fun setListeners(){
        publishButton.setOnClickListener{
            try{
                loginViewModel.updateUserData(userName.text.toString(), userPhoneNumber.text.toString())
                Snackbar.make(fragment, "Profile modified successfully", Snackbar.LENGTH_LONG)
                        .show()
            } catch(e : Exception) {
                Log.d("xxx", "Exception while editing profile: $e")
            }
        }
    }
}