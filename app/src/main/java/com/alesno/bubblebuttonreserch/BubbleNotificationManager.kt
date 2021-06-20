package com.alesno.bubblebuttonreserch

import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.Bitmap
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import com.alesno.bubblebuttonreserch.ui.BubbleActivity
import com.alesno.bubblebuttonreserch.ui.MainActivity
import com.alesno.bubblebuttonreserch.ui.conversation.viewstate.Participant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object BubbleNotificationManager {

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "ChannelName"
            val descriptionText = "Description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("123", name, importance).apply {
                description = descriptionText
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    setAllowBubbles(true)
                }
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    suspend fun createNotification(context: Context, participant: Participant, conversationId: String): Notification {
        val avatarBitmap = context.getBitmapFromUrlOrError(participant.avatarUrl)
        val icon = Icon.createWithAdaptiveBitmap(avatarBitmap)

        val bubbleIntent = createBubbleIntent(context, conversationId)
        val bubbleData = createBubbleMetaData(context, bubbleIntent, icon)
        val person = createSamplePerson(participant, icon)
        val style = createMessagingStyle(person, "Тут может быть последнее сообщение")
        val shortcutManager = context.getSystemService(ShortcutManager::class.java)
        val shortcut = createShortcut(context, person)
        //shortcutManager.dynamicShortcuts = listOf(shortcut)

        val notification = Notification.Builder(context, "123")
            .setContentIntent(bubbleIntent)
            .setSmallIcon(R.drawable.ic_snowflake)
            .setCategory(Notification.CATEGORY_MESSAGE)
            //.setLocusId(LocusId(shortcut.id))
            .setBubbleMetadata(bubbleData)
            .setStyle(style)
            //.addPerson(person)
            .setShowWhen(true)
            .setOnlyAlertOnce(true)
            //.setShortcutId(shortcut.id)
            .build()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        NotificationManagerCompat.from(context).apply {
            notificationManager.notify(conversationId.toInt(), notification)
        }
        return notification
    }

    private suspend fun Context.getBitmapFromUrlOrError(url: String): Bitmap {
        return suspendCoroutine { continuation ->
            Glide.with(this)
                .asBitmap()
                .load(url)
                .circleCrop()
                .listener(object : RequestListener<Bitmap> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        val errorBitmap = ContextCompat.getDrawable(
                            this@getBitmapFromUrlOrError,
                            R.drawable.patric
                        )?.toBitmap(42, 42)
                        errorBitmap?.let { continuation.resume(it) }
                            ?: continuation.resumeWithException(createException())
                        return true
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        resource?.let { continuation.resume(it) }
                            ?: continuation.resumeWithException(createException())
                        return true
                    }

                    private fun createException() =
                        RuntimeException("Something went wrong when bitmap creating")

                }).submit()
        }
    }

    private fun createBubbleIntent(context: Context, conversationId: String): PendingIntent {
        val contentUri = "https://android.example.com/chat/$conversationId".toUri()

        val target = Intent(context, BubbleActivity::class.java)
            .setAction(Intent.ACTION_VIEW)
            .setData(contentUri)
        return PendingIntent.getActivity(context, 0, target, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun createBubbleMetaData(context: Context, bubbleIntent: PendingIntent, icon: Icon) =
        Notification.BubbleMetadata.Builder()
            .setIntent(bubbleIntent)
            .setIcon(icon)
            .setDesiredHeight(600)
            //.setAutoExpandBubble(true)
            .setSuppressNotification(true)
            .build()

    @RequiresApi(Build.VERSION_CODES.P)
    private fun createSamplePerson(participant: Participant, icon: Icon) =
        Person.Builder()
            .setName(participant.name)
            .setIcon(icon)
            .setImportant(true)
            .build()

    @RequiresApi(Build.VERSION_CODES.P)
    private fun createMessagingStyle(person: Person, text: String) =
        Notification.MessagingStyle(person).addMessage(
            Notification.MessagingStyle.Message(
                text,
                System.currentTimeMillis(),
                person
            )
        )

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun createShortcut(context: Context, person: Person) =
        ShortcutInfo.Builder(context, "shortcut")
            .setLongLived(true)
            .setIntent(
                Intent(Intent.ACTION_MAIN, Uri.EMPTY, context, BubbleActivity::class.java).setFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TASK
                )
            )
            .setIcon(Icon.createWithResource(context, R.drawable.ic_sn))
            .setPerson(person)
            .setActivity(ComponentName(context, MainActivity::class.java))
            .build()


}