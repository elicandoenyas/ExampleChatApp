package com.elican.examplechatapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.elican.examplechatapp.model.MessageModel
import com.elican.examplechatapp.R
import com.elican.examplechatapp.generatePrettyTimeFromTimestamp
import kotlinx.android.synthetic.main.current_user_message_layout.view.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SenderMessageLayout(context: Context, attributeSet: AttributeSet?): FrameLayout(context, attributeSet) {
    var root: View = LayoutInflater.from(context).inflate(R.layout.current_user_message_layout, this, true)
    var imageFetcherThread: ExecutorService = Executors.newFixedThreadPool(3)

    fun setMessageModel(messageModel: MessageModel) {
        root.post {
            root.currentUserNickname.text = messageModel.nickName
            root.currentUserMessage.text = messageModel.message
            root.currentUserDate.text = generatePrettyTimeFromTimestamp(messageModel.timestampInSeconds)
            if (messageModel.avatarUrl.isNotEmpty()) {
                imageFetcherThread.submit {
                    val drawable = Glide.with(context).load(messageModel.avatarUrl).submit().get()
                    root.currentUserAvatar.setImageDrawable(drawable)
                }
            }
        }
    }
}