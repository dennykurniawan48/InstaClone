package com.signaltekno.instaclone.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Post(
    var id: String? = null,
    var username: String? = null,
    var image: String? = null,
    var profileImg: String? = null,
    var caption: String? = null,
    var comments: List<Comment> = emptyList(),
    @ServerTimestamp
    var timestamp: Date? = null,
    var isLiked: Boolean = false
)