package com.elican.examplechatapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elican.examplechatapp.model.MessageModel
import com.elican.examplechatapp.R
import com.elican.examplechatapp.view.ReceivedMessageLayout
import com.elican.examplechatapp.view.SenderMessageLayout

class MessagesAdapter(val currentUserNickname: String): ListAdapter<MessageModel, MessagesAdapter.ViewHolder>(MessagesDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.general_message_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        private val receivedMessageLayout = view.findViewById<ReceivedMessageLayout>(R.id.receivedMessageLayout)
        private val senderMessageLayout = view.findViewById<SenderMessageLayout>(R.id.senderMessageLayout)
        fun bind(messageModel: MessageModel) {
            if (messageModel.nickName == currentUserNickname) {
                // The sender of this message is current user. So we should show the sender layout
                receivedMessageLayout.visibility = View.GONE
                senderMessageLayout.visibility = View.VISIBLE
                senderMessageLayout.setMessageModel(messageModel)
            } else {
                // The sender of this message is someone other then the current user.
                // So we should show the receiver layout
                senderMessageLayout.visibility = View.GONE
                receivedMessageLayout.visibility = View.VISIBLE
                receivedMessageLayout.setMessageModel(messageModel)
            }
        }
    }
}

class MessagesDiffCallback : DiffUtil.ItemCallback<MessageModel>() {
    override fun areItemsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: MessageModel, newItem: MessageModel): Boolean {
        return oldItem == newItem
    }

}