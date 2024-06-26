package com.mywild.whatsdeleted.data.repository

import androidx.lifecycle.LiveData
import com.mywild.whatsdeleted.data.database.Database
import com.mywild.whatsdeleted.data.model.Chat
import com.mywild.whatsdeleted.data.model.DeletedMessage
import com.mywild.whatsdeleted.utility.isValidTitle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val database: Database
) {

    suspend fun saveMessage(chat: Chat) {
        withContext(Dispatchers.IO) {
            if (chat.message.isValidTitle()) {
                //fetch last message and make comparison to avoid duplicates
                val lastMessage = database.userDao().getLastMessage(chat.user)
                if (lastMessage != null) {
                    if (chat.message != lastMessage.message && chat.time != lastMessage.time) database.userDao().save(chat)
                } else {
                    database.userDao().save(chat)
                }
            }
        }
    }

    suspend fun fetchChats(): LiveData<List<Chat>> {
        return withContext(Dispatchers.IO) {
            database.userDao().getChats()
        }
    }

    suspend fun fetchMessagesByUser(user: String,app: String): LiveData<List<Chat>> {
        return withContext(Dispatchers.IO) {
            database.userDao().getMessagesByUser(user,app)
        }
    }

    suspend fun lastMessage(user: String): DeletedMessage? {
        return withContext(Dispatchers.IO) {
            database.userDao().getLastMessage(user)
        }
    }

    suspend fun messageIsDeleted(id: String) {
        withContext(Dispatchers.IO) {
            database.userDao().messageIsDeleted(id)
        }
    }
}