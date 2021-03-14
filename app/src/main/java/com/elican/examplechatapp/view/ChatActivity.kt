package com.elican.examplechatapp.view

import android.os.Bundle
import android.os.Handler
import android.transition.TransitionManager
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.elican.examplechatapp.R
import com.elican.examplechatapp.adapter.MessagesAdapter
import com.elican.examplechatapp.getRootView
import com.elican.examplechatapp.hideKeyboard
import com.elican.examplechatapp.isKeyboardOpen
import com.elican.examplechatapp.model.MessageModel
import com.elican.examplechatapp.storage.StorageManager
import com.elican.examplechatapp.viewmodel.MessagesViewModel
import kotlinx.android.synthetic.main.chat_activity.*
import kotlinx.android.synthetic.main.toolbar.view.*

class ChatActivity: AppCompatActivity() {
    companion object {
        val nicknameExtraKey = "nick_name_extra"
    }

    private val messagesViewModel by viewModels<MessagesViewModel>()
    private var currentNickName: String = "Unknown"
    private lateinit var messagesAdapter: MessagesAdapter
    lateinit var handler: Handler
    lateinit var storageManager: StorageManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_activity)
        currentNickName = intent?.getStringExtra(nicknameExtraKey) ?: currentNickName
        messagesAdapter = MessagesAdapter(currentNickName)
        handler = Handler(mainLooper)
        storageManager = StorageManager(applicationContext)
        assignObservers()
    }

    override fun onResume() {
        super.onResume()
        assignToolbarBehavior()
        messagesRecyclerView.adapter = messagesAdapter
        sendMessageCard.setOnClickListener {
            val message = messageEntryField.text.toString()
            val currentMessages = messagesViewModel.listOfMessagesSync
            currentMessages.add(MessageModel(currentNickName, System.currentTimeMillis() / 1000, message, ""))
            messagesAdapter.submitList(currentMessages)
            Toast.makeText(applicationContext, "Message Sent!", Toast.LENGTH_SHORT).show()
            messageEntryField.text.clear()
            handler.postDelayed({
                messagesRecyclerView.layoutManager?.scrollToPosition(currentMessages.size -1)
            },100)
        }
        messagesViewModel.fetchNewMessages()
    }

    private fun assignToolbarBehavior() {
        toolbar.nicknameText.text = currentNickName
        toolbar.leaveButton.setOnClickListener {
            // Reset the current nickname
            storageManager.saveNickName("")
            finish()
        }

    }

    private fun assignObservers() {
        messagesViewModel.listOfMessages.observe(this, { listOfMessages->
            messagesRecyclerView.post {
                messagesAdapter.submitList(listOfMessages)
            }
        })
    }

}