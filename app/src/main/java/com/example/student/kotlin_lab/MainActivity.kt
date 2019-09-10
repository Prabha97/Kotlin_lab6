package com.example.student.kotlin_lab

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    val CHANNEL_ID = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)

            mChannel.description = descriptionText

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    fun ShowNotification(view: View){
        val name = findViewById<EditText>(R.id.editName)
        val age = findViewById<EditText>(R.id.editAge)
        Log.v("Name",name.text.toString())
        var status = ""

        if(age.text.toString().toInt()<20){
            status = "Group1"
        }else if(age.text.toString().toInt()<30){
            status = "Group2"
        }else{
            status = "Group3"
        }
        Log.v("Message","Hello, "+name.text.toString()+"! Your group is "+status)
        addNotification(name.text.toString(),status)
    }

    private fun addNotification(pname:String,page:String){
        var builder = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("My notification")
            .setContentText("Welcome to Notification App")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Hello, "+pname+"! You are selected to the "+page))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationIntent = Intent(this, NotificationView::class.java)
        val contentIntent = PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(contentIntent);

        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        notificationManager.notify(0,builder.build())
        Log.v("Notification","Come to the Function")
    }
}
