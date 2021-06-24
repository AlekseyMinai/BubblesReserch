package com.alesno.bubblebuttonreserch

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Person
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.LocusId
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.alesno.bubblebuttonreserch.ui.BubbleActivity
import com.alesno.bubblebuttonreserch.ui.MainActivity
import com.alesno.bubblebuttonreserch.ui.conversation.viewstate.Participant

object BubbleNotificationManager2 {

    @RequiresApi(Build.VERSION_CODES.R)
    fun createNotification(
        context: Context,
        participant: Participant,
        conversationId: String
    ): Notification {
//        val icon = Icon.createWithAdaptiveBitmap(
//            context.resources.assets.open("cat.jpg").use { input ->
//                BitmapFactory.decodeStream(input)
//            }
//        )
        val icon = Icon.createWithAdaptiveBitmapContentUri(participant.avatarUrl)

        val bubbleIntent = BubbleNotificationManager.createBubbleIntent(context, conversationId)
        val person = BubbleNotificationManager.createSamplePerson(participant, icon)
        val style = BubbleNotificationManager.createMessagingStyle(person, "Привет, как дела?")

        val shortcutManager = context.getSystemService(ShortcutManager::class.java)
        val shortcut = createShortcut(context, person, icon, conversationId)
        shortcutManager.addDynamicShortcuts(listOf(shortcut))

        val bubbleData = createBubbleMetaData(bubbleIntent, icon)

        val notification = Notification.Builder(context, "123")
            .setContentIntent(bubbleIntent)
            .setContentText("Title")
            .setSmallIcon(R.drawable.ic_snowflake)
            .setCategory(Notification.CATEGORY_MESSAGE)
            .setLocusId(LocusId(shortcut.id))
            .setBubbleMetadata(bubbleData)
            .setStyle(style)
            .addPerson(person)
            .setShowWhen(true)
            .setOnlyAlertOnce(true)
            .setShortcutId(shortcut.id)
            .build()
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        NotificationManagerCompat.from(context).apply {
            notificationManager.notify(conversationId.toInt(), notification)
        }
        return notification
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun createShortcut(context: Context, person: Person, icon: Icon, conversationId: String) =
        ShortcutInfo.Builder(context, "shortcut")
            .setLongLived(true)
            .setIntent(
                Intent(context, BubbleActivity::class.java)
                    .setAction(Intent.ACTION_VIEW)
                    .setData(
                        Uri.parse(
                            "https://android.example.com/chat/$conversationId"
                        )
                    )
            )
            .setIcon(icon)
            .setShortLabel(person.name.toString())
            .setPerson(person)
            .setActivity(ComponentName(context, MainActivity::class.java))
            .build()

    @RequiresApi(Build.VERSION_CODES.R)
    private fun createBubbleMetaData(bubbleIntent: PendingIntent, icon: Icon) =
        Notification.BubbleMetadata.Builder(bubbleIntent, icon)
            .setDesiredHeight(600)
            //.setAutoExpandBubble(true)
            //.setSuppressNotification(true)
            .build()

}