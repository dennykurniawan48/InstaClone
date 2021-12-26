package com.signaltekno.instaclone.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Comment(
    val comment: String? = null,
    val name: String? = null,
    val profileImg: String? = null,
    @ServerTimestamp
    val timestamp: Date? = null
)
