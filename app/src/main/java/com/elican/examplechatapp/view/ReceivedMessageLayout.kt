package com.elican.examplechatapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.elican.examplechatapp.R
import com.elican.examplechatapp.generatePrettyTimeFromTimestamp
import com.elican.examplechatapp.model.MessageModel
import kotlinx.android.synthetic.main.current_user_message_layout.view.*
import kotlinx.android.synthetic.main.message_layout.view.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ReceivedMessageLayout(context: Context, attributeSet: AttributeSet?): FrameLayout(context, attributeSet) {
    var root: View = LayoutInflater.from(context).inflate(R.layout.message_layout, this, true)
    var imageFetcherThread: ExecutorService = Executors.newFixedThreadPool(3)

    fun setMessageModel(messageModel: MessageModel) {
        root.post {
            root.nickname.text = messageModel.nickName
            root.receivedMessageText.text = messageModel.message
            root.receivedMessageDateText.text = generatePrettyTimeFromTimestamp(messageModel.timestampInSeconds)
            if (messageModel.avatarUrl.isNotEmpty()) {
                imageFetcherThread.submit {
                    val drawable = Glide.with(context).load(messageModel.avatarUrl).submit().get()
                    root.userAvatar.setImageDrawable(drawable)
                }
            }
        }
    }
}