package com.signaltekno.instaclone.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.signaltekno.instaclone.model.DataOrException
import com.signaltekno.instaclone.model.Post
import com.signaltekno.instaclone.repository.DatastoreRepository
import com.signaltekno.instaclone.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val profile: MutableState<DataOrException<List<Post>, Exception>> = mutableStateOf(
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

    fun getProfile() {
        viewModelScope.launch {
            loading.value = true
            datastoreRepository.flowName.collect{
                profile.value = repository.getPostsFromFirestore(it)
            }
            loading.value = false
        }
    }

    fun setLikes(id: String){
        val newData: MutableList<Post> = mutableListOf()

        data.value.data?.forEach {
            if(it.id == id) {
                it.isLiked = !it.isLiked
                Log.d("Liked", it.isLiked.toString())
            }
            newData.add(it)
        }
        Log.d("New data", newData.toString())
        data.value.data = newData
    }


}