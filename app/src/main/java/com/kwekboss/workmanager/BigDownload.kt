package com.kwekboss.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters


// Here is where your workLogic goes
class BigDownload(context: Context, workerParams: WorkerParameters):Worker(context, workerParams) {
    override fun doWork(): Result{
        try {
            for(i in 1..5000){
                Log.i("down", "Still Downloading $i")
                }
            callNotification()
            return Result.success()

         } catch (e:Exception){
            return Result.failure()
        }
     }

    fun callNotification() {
        // Checking if the android version is 8 or above to create channel for it first as required in android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                cHANNEL_ID, cHANNEL_Name,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
        // creating an intent for notification to lunch activity.

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = TaskStackBuilder.create(applicationContext).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        // Setting up the notification

        val notificationBuild = NotificationCompat.Builder(applicationContext, cHANNEL_ID)
            .setContentTitle("Worker Notification")
            .setContentText("Work Started")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_star_rate_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(applicationContext)
            .notify(nOTIFICATION_ID,notificationBuild)
    }


}