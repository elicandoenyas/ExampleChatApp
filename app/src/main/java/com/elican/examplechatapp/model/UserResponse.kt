package com.elican.examplechatapp.model

import com.google.gson.annotations.SerializedName

class UserResponse {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("avatarURL")
    var avatarUrl: String? = null

    @SerializedName("nickname")
    var nickName: String? = null
}