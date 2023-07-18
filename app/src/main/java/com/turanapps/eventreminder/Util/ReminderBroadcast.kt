package com.turanapps.eventreminder.Util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.turanapps.eventreminder.R

class ReminderBroadcast(

): BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText(intent!!.getStringExtra("comment"))

        val builder = NotificationCompat.Builder(context, "EVENT_CHANNEL")
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(intent.getStringExtra("header"))
            .setContentText(intent.getStringExtra("comment"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(bigTextStyle)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(intent.getIntExtra("id", 0), builder.build())

    }

}

