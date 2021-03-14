package com.elican.examplechatapp.model

import com.google.gson.annotations.SerializedName

class Response {

    @SerializedName("messages")
    var messages: List<MessageResponse>? = null
}