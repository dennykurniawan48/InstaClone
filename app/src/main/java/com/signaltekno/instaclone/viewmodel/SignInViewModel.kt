package com.signaltekno.instaclone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.signaltekno.instaclone.model.User
import com.signaltekno.instaclone.repository.DatastoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val datastoreRepository: DatastoreRepository): ViewModel() {
    val email = datastoreRepository.flowEmail
    val image = datastoreRepository.flowImage
    val name = datastoreRepository.flowName

    fun setSignInValue(email: String, displayName: String, userImg: String) {
        viewModelScope.launch {
            datastoreRepository.saveEmail(email)
            datastoreRepository.saveImage(userImg)
            datastoreRepository.saveName(displayName)
        }
    }
}