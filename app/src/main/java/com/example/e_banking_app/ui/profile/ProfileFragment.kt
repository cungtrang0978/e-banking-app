package com.example.e_banking_app.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.e_banking_app.MainActivity
import com.example.e_banking_app.R
import com.example.e_banking_app.data.factory.UserViewModelFactory
import com.example.e_banking_app.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val changePasswordBtn get() = binding.changePassword
    private val checkBalanceBtn get() = binding.checkAccount
    private val logoutBtn get() = binding.logout
    private val cardManagementBtn get() = binding.cardManagement
    private val changeLanguageBtn get() = binding.changeLanguage
    private val infoBtn get() = binding.infoBtn


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(
                this,
                UserViewModelFactory(context!!)
            )[ProfileViewModel::class.java]

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.name


        profileViewModel.fetchProfile()
        profileViewModel.profileState.observe(viewLifecycleOwner) { state ->
            state.success?.let {
                textView.text = it.name
            }
            R.string.citizen_identity_card

            state.error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changePasswordBtn.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToChangePasswordFragment()
            findNavController().navigate(action)
        }

        logoutBtn.setOnClickListener {
            logout()
        }

        cardManagementBtn.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToCardManagementFragment()
            findNavController().navigate(action)
        }

        changeLanguageBtn.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToLanguageFragment()
            findNavController().navigate(action)
        }

        infoBtn.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToInfoFragment()
            findNavController().navigate(action)
        }

        checkBalanceBtn.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToCheckBalanceFragment()
            findNavController().navigate(action)
        }
    }

    private fun logout() {
        val sharedPref =
            activity?.getSharedPreferences(getString(R.string.access_token), Context.MODE_PRIVATE)
                ?: return
//        with(sharedPref.edit()) {
//            clear()
//            apply()
//        }
        sharedPref.edit().clear().commit()
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}