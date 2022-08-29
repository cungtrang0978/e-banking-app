package com.example.e_banking_app.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.e_banking_app.data.factory.UserViewModelFactory
import com.example.e_banking_app.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: InfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        viewModel =
            ViewModelProvider(this, UserViewModelFactory(context!!))[InfoViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.infoResult.observe(viewLifecycleOwner) {
            it ?: return@observe

            it.success?.let { user ->
                binding.id.text = user.id_person
                binding.address.text = user.address
                binding.username.text = user.name
                binding.mail.text = user.mail
                binding.phoneNumber.text = user.phone
                binding.createdAt.text = user.created_at
                binding.citizenIdentityCard.text = user.citizen_identity_card
            }
        }
    }


}