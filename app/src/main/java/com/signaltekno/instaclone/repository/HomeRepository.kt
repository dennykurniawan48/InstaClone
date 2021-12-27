package com.signaltekno.instaclone.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.signaltekno.instaclone.model.Comment
import com.signaltekno.instaclone.model.DataOrException
import com.signaltekno.instaclone.model.LikeData
import com.signaltekno.instaclone.model.Post
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ViewModelScoped
class HomeRepository @Inject constructor(
    private val queryProductsByName: Query
) {
    val db = FirebaseFirestore.getInstance()
    suspend fun getPostsFromFirestore(name: String): DataOrException<List<Post>, Exception> {
        val dataOrException = DataOrException<List<Post>, Exception>()

        try {
            dataOrException.data = queryProductsByName.get().await().map { document ->
                var post = document.toObject(Post::class.java)
                val comments = mutableListOf<Comment>()
                db.collection("posts").document(document.id).collection("comments").get().await().map { comment->
                    comments.add(comment.toObject(Comment::class.java))
                }
                db.collection("posts").document(document.id).collection("likes").get().await().map {
                    val like = it.toObject(LikeData::class.java)
                    Log.d("yes", like.name.toString() + " " + name)
                    if(like.name == name){
                        post.isLiked = true
                    }
                }
                post.id = document.id
                post.comments = comments
                post
            }
        } catch (e: FirebaseFirestoreException) {
            dataOrException.e = e
        }
        return dataOrException
    }
}