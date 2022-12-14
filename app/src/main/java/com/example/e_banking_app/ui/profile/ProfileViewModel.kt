package com.example.e_banking_app.ui.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_banking_app.R
import com.example.e_banking_app.data.repository.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _profileState = MutableLiveData<ProfileState>()
    val profileState: LiveData<ProfileState> = _profileState


    fun fetchProfile() {

        userRepository.getProfile(
            onSuccess = {
                _profileState.value = ProfileState(success = it)
            },
            onFailure = {
                _profileState.value = ProfileState(error = R.string.fail_to_fetch_profile)

            })
    }
}