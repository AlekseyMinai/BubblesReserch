package com.alesno.bubblebuttonreserch

import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.LocusId
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

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
    fun createNotification(context: Context): Notification {
        val bubbleIntent = createBubbleIntent(context)
        val bubbleData = createBubbleMetaData(context, bubbleIntent)
        val person = createSamplePerson(context)
        val style = createMessagingStyle(person)
        val shortcutManager = context.getSystemService(ShortcutManager::class.java)
        val shortcut = createShortcut(context, person)
        shortcutManager.dynamicShortcuts = listOf(shortcut)


        val notification = Notification.Builder(context, "123")
            //.setContentIntent(bubbleIntent)
            .setSmallIcon(R.drawable.ic_snowflake)
            .setCategory(Notification.CATEGORY_CALL)
            .setContentTitle("Title")
            .setContentText("Content text")
            .setLocusId(LocusId(shortcut.id))
            .setBubbleMetadata(bubbleData)
            .setStyle(style)
            .addPerson(person)
            .setShowWhen(true)
            //.setShortcutId(shortcut.id)
            .build()
//        val notificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        NotificationManagerCompat.from(context).apply {
//            notificationManager.notify(1, notification)
//        }
        return notification
    }

    private fun createBubbleIntent(context: Context): PendingIntent {
        val target = Intent(context, BubbleActivity::class.java)
            .setAction(Intent.ACTION_VIEW)
        return PendingIntent.getActivity(context, 0, target,0)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun createBubbleMetaData(context: Context, bubbleIntent: PendingIntent) =
        Notification.BubbleMetadata.Builder()
            .setIntent(bubbleIntent)
            .setIcon(Icon.createWithResource(context, R.drawable.ic_snowflake))
            .setDesiredHeight(600)
            .setAutoExpandBubble(true)
            .setSuppressNotification(true)
            .build()

    @RequiresApi(Build.VERSION_CODES.P)
    private fun createSamplePerson(context: Context) =
        Person.Builder()
            .setName("Name")
            .setIcon(Icon.createWithResource(context, R.drawable.ic_snowflake))
            .setImportant(true)
            .build()

    @RequiresApi(Build.VERSION_CODES.P)
    private fun createMessagingStyle(person: Person) =
        Notification.MessagingStyle(person).addMessage(
            Notification.MessagingStyle.Message(
                "TestText",
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
            .setShortLabel("wtf")
            .setLongLabel("asdf")
            .setIcon(Icon.createWithResource(context, R.drawable.ic_sn))
            .setPerson(person)
            .setActivity(ComponentName(context, MainActivity::class.java))
            .build()


}