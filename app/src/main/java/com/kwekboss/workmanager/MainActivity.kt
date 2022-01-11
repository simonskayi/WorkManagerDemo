package com.kwekboss.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Build.VERSION_CODES.O
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

// setting up notification
const val cHANNEL_ID = "notify"
const val cHANNEL_Name = "ChannelName"
const val nOTIFICATION_ID = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

// Work function Called
        setOneTimeRequest()
    }

    fun setOneTimeRequest() {
        //Step1   //setting constraints(ie Conditions before workStarts)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()//Should be the last instance called

        //Step 2  // setting up OneTimeWorkRequestBuilder
        val startDownload =
            OneTimeWorkRequest.Builder(BigDownload::class.java).setConstraints(constraints)
                .build()

        //Step 3 //creating an instance of workManager and calling enqueue
        WorkManager.getInstance(applicationContext)
            .enqueue(startDownload)
    }

/* creating a notification in main activity
fun callnotification() {
        if (Build.VERSION.SDK_INT >= O) {
            val channel = NotificationChannel(
                cHANNEL_ID, cHANNEL_Name,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        val notificationBuild = NotificationCompat.Builder(applicationContext, cHANNEL_ID)
            .setContentTitle("Worker Notifcation")
            .setContentText("Upload has Succeeded")
            .setSmallIcon(R.drawable.ic_baseline_star_rate_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

       NotificationManagerCompat.from(this@MainActivity)
            .notify(nOTIFICATION_ID,notificationBuild)
    }
    */


}
