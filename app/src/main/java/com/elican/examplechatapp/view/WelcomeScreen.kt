package com.elican.examplechatapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elican.examplechatapp.R
import com.elican.examplechatapp.storage.StorageManager
import kotlinx.android.synthetic.main.welcome_screen.*

class WelcomeScreen: AppCompatActivity() {

    lateinit var storageManager: StorageManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_screen)
        storageManager = StorageManager(applicationContext)
        storageManager.getCurrentNickName(object: StorageManager.StorageAccessor<String> {
            override fun onStorageDataRetrieved(data: String) {
                if (data != "") {
                    startChatActivityWithNickname(data)
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()
        assignUIBehaviors()
    }

    private fun assignUIBehaviors() {
        continueBtn.setOnClickListener {
            val nickname = nicknameText.text.toString()
            if (nickname.length < 3) {
                Toast.makeText(applicationContext, "Please enter a nickname longer than 3 letters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            storageManager.saveNickName(nickname)
            startChatActivityWithNickname(nickname)
        }
    }

    private fun startChatActivityWithNickname(nickname: String) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra(ChatActivity.nicknameExtraKey, nickname)
        startActivity(intent)
    }
}