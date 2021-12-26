package com.signaltekno.instaclone.viewmodel

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.signaltekno.instaclone.model.DataOrException
import com.signaltekno.instaclone.model.Post
import com.signaltekno.instaclone.repository.DatastoreRepository
import com.signaltekno.instaclone.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val datastoreRepository: DatastoreRepository
): ViewModel() {
    var loading = mutableStateOf(false)

    val data: MutableState<DataOrException<List<Post>, Exception>> = mutableStateOf(
        DataOrException(
            listOf(),
            Exception("")
        )
    )

    fun getPosts() {
        viewModelScope.launch {
            loading.value = true
            datastoreRepository.flowName.collect{
                data.value = repository.getPostsFromFirestore(it)
            }
            loading.value = false
        }
    }
}