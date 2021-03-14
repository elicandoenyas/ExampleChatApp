package com.elican.examplechatapp.model

import com.google.gson.annotations.SerializedName

class MessageResponse {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("text")
    var text: String? = null

    @SerializedName("timestamp")
    var timestamp: Int = 0

    @SerializedName("user")
    var user: UserResponse? = null

}