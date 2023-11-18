package com.dicoding.habitapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dicoding.habitapp.R
import com.dicoding.habitapp.ui.detail.DetailHabitActivity
import com.dicoding.habitapp.utils.HABIT_ID
import com.dicoding.habitapp.utils.HABIT_TITLE
import com.dicoding.habitapp.utils.NOTIFICATION_CHANNEL_ID

class NotificationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val habitId = inputData.getInt(HABIT_ID, 0)
    private val habitTitle = inputData.getString(HABIT_TITLE)

    override fun doWork(): Result {
        val prefManager = androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val shouldNotify = prefManager.getBoolean(applicationContext.getString(R.string.pref_key_notify), false)

        //TODO 12 : If notification preference on, show notification with pending intent
        if (shouldNotify){
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notifName = applicationContext.getString(R.string.name_notif)

            val newIntent = Intent(applicationContext, DetailHabitActivity::class.java)
            newIntent.putExtra(HABIT_ID, habitId)
            val pendingIntent = TaskStackBuilder.create(applicationContext)
                .addNextIntent(newIntent)
                .getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)

            val newNotif = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle(habitTitle)
                .setContentText(applicationContext.getString(R.string.notify_content))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val newChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, notifName, NotificationManager.IMPORTANCE_DEFAULT)
                newNotif.setChannelId(NOTIFICATION_CHANNEL_ID)
                notificationManager.createNotificationChannel(newChannel)
            }

            notificationManager.notify(habitId, newNotif.build())
        }

        return Result.success()
    }
}
