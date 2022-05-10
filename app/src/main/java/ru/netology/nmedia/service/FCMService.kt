package ru.netology.nmedia.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import ru.netology.nmedia.R
import ru.netology.nmedia.dto.Action
import ru.netology.nmedia.dto.Like
import ru.netology.nmedia.dto.NewPost
import kotlin.random.Random


class FCMService : FirebaseMessagingService() {


    private val gson by lazy {
        Gson()
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_remote_name)
            val descriptionText = getString(R.string.channel_remote_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val data = message.data
        val serializedAction = data[Action.KEY]
        val action = Action.values().find { it.key == serializedAction }
        when (action) {
            Action.Like -> onLike(data)
            null -> Unit
            Action.NEW_POST -> onNewPost(data)

        }
    }

    override fun onNewToken(token: String) {
        Log.d("onNewToken", Gson().toJson(token))
    }


    private fun onLike(data: Map<String?, String?>) {

        val serializedContent = data[LIKE_CONTENT_KEY]
        val like = gson.fromJson(serializedContent, Like::class.java)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(
                getString(
                    R.string.notification_user_liked,
                    like.userName,
                    like.postAuthor,
                )
            )

            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }

    private fun onNewPost(data: Map<String?, String?>) {

        val serializedContent = data[LIKE_CONTENT_KEY]
        val post = gson.fromJson(serializedContent, NewPost::class.java)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(
                getString(
                    R.string.notification_new_post,
                    post.userName,
                )
            )
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(post.text)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this)
            .notify(Random.nextInt(100_000), notification)
    }


    private companion object {
        private const val LIKE_CONTENT_KEY = "content"
        private const val CHANNEL_ID = "remote"
    }

}