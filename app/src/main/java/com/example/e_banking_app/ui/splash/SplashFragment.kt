package com.example.e_banking_app.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.e_banking_app.MainFlowActivity
import com.example.e_banking_app.R
import com.example.e_banking_app.databinding.FragmentLoginBinding

class SplashFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isLogin = checkToken()
        if (isLogin) {
            val intent = Intent(activity, MainFlowActivity::class.java)
            startActivity(intent)
            activity?.finish()
        } else {
            val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }

    private fun checkToken(): Boolean {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return false
//        val defaultValue = resources.getInteger(R.integer.saved_high_score_default_key)
        val accessToken = sharedPref.getString(getString(R.string.access_token), "")
        return accessToken?.isNotBlank() ?: false
    }
}