package com.elican.examplechatapp.storage

import android.content.Context
import android.content.SharedPreferences
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StorageManager(context: Context) {

    private val sharedPrefName = "chat_shared_pref"
    private val sharedPrefMode = Context.MODE_PRIVATE
    private val nickNameKey = "nick_name_key"

    var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(sharedPrefName, sharedPrefMode)
    var sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
    private val storageOpThread: ExecutorService = Executors.newCachedThreadPool()

    fun saveNickName(nickname: String) {
        storageOpThread.submit {
            sharedPreferencesEditor.putString(nickNameKey, nickname).apply()
        }
    }

    fun getCurrentNickName(storageAccessor: StorageAccessor<String>) {
        storageOpThread.submit {
            val nickName = sharedPreferences.getString(nickNameKey, "")!!
            storageAccessor.onStorageDataRetrieved(nickName)
        }
    }

    interface StorageAccessor<T> {
        fun onStorageDataRetrieved(data: T)
    }
}