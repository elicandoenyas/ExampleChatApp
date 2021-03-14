package com.elican.examplechatapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elican.examplechatapp.BuildConfig
import com.elican.examplechatapp.model.MessageModel
import com.elican.examplechatapp.model.MessageResponse
import com.elican.examplechatapp.network.MessageFetcherService
import com.elican.examplechatapp.network.NetworkCoordinator
import java.util.*
import java.util.concurrent.Executors

class MessagesViewModel: ViewModel() {

    private val _listOfMessages: MutableLiveData<List<MessageModel>> = MutableLiveData()
    val listOfMessages: LiveData<List<MessageModel>> = _listOfMessages
    val listOfMessagesSync: MutableList<MessageModel> = mutableListOf()
    private val networkCoordinator = NetworkCoordinator("https://jsonblob.com/api/jsonBlob/", BuildConfig.DEBUG)
    private val messageGetterService = networkCoordinator.provideRetrofit().create(MessageFetcherService::class.java)
    private val networkOperationsThread = Executors.newFixedThreadPool(5)

    // This is fixed for the context of this example project,
    // but we could set it dynamically for each user by giving it as parameter to the url
    private val userIdParam = "62455171-0fb1-11eb-9f83-ffcd873e5c3a"



    fun fetchNewMessages(userId: String = userIdParam) {
        networkOperationsThread.submit {
            val messages = fetchMessages(userId)
            _listOfMessages.postValue(messages)
            listOfMessagesSync.clear()
            listOfMessagesSync.addAll(messages)
        }
    }

    private fun fetchMessages(userId: String): List<MessageModel> {
        val call = messageGetterService.getMessages(userId)
        val response = call.execute()
        val listOfMessagesToReturn = mutableListOf<MessageModel>()
        return if (response.isSuccessful) {
            val listOfMessages = response.body()?.messages ?: emptyList()
            listOfMessages.forEach {
                listOfMessagesToReturn.add(MessageModel(it.user?.nickName ?: "Unknown User",
                    it.timestamp.toLong(), it.text ?: "No message body", it.user?.avatarUrl ?: ""))
            }
            listOfMessagesToReturn
        } else {
            emptyList()
        }
    }
}