package com.bedu.clasekotlinavanzado

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.bedu.clasekotlinavanzado.databinding.ActivityMainBinding


//Notificaciones Ejemplo 1: https://github.com/beduExpert/Kotlin-Avanzado-Santander-2021/blob/main/Sesion-06/Ejemplo-01/Readme.md



class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy{ ActivityMainBinding.inflate(layoutInflater) }

    companion object{

        var NOTIFICATION_ID=0

        //el nombre de la acción a ejecutar por el botón en la notificación
        const val CHANNEL_COURSES = "channel_courses"

        //el nombre de la acción a ejecutar por el botón en la notificación
        const val CHANNEL_DIVERSES = "channel_diverses"

        //el nombre de la acción a ejecutar por el botón en la notificación
        const val ACTION_RECEIVED = "action_received"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Para android Oreo en adelante, es obligatorio registrar el canal de notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setNotificationChannel()
            setNotificationChannelDiversos()
        }

        binding.run{
            //Aqu[i colocamos nuestros listeners
            btnNotify.setOnClickListener { simpleNotification() }
            btnNotify.setOnLongClickListener { touchAndActionNotification()
            true}
            btnActionNotify.setOnClickListener { touchNotification() }
            btnNotifyWithBtn.setOnClickListener { actionNotification() }
        }
    }

    //Nos pide que sea el API Oreo para funcionar el NotificationChannel
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotificationChannel(){
        //Nombre con el cual va a aparecer la notificación
        val name = getString(R.string.channel_courses)
        val descriptionText = getString(R.string.courses_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_COURSES,name,importance).apply { description = descriptionText }
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotificationChannelDiversos(){
        //Nombre con el cual va a aparecer la notificación
        val name = getString(R.string.channel_diverse)
        val descriptionText = getString(R.string.courses_diverse_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_DIVERSES,name,importance).apply { description = descriptionText }
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun simpleNotification(){
        //Seteamos un canal pero en versiones anteriores a oreno sólo se ejecuta
        val notification = NotificationCompat.Builder(this, CHANNEL_COURSES)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setColor(ContextCompat.getColor(this,R.color.triforce))
            .setContentTitle(getString(R.string.simple_title))
            .setContentText(getString(R.string.simple_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        //Ya tenemos la notificación pero hay que registrarla
        NotificationManagerCompat.from(this).run {
            notify(++NOTIFICATION_ID,notification)
        }
    }

    private fun touchNotification(){

        val intent = Intent(this, Act2_ReturnFormNotification::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        //Haremos un intent que se pueda ejecutar desde otra aplicación
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0) // El cero al final es una bandera

        val notification = NotificationCompat.Builder(this, CHANNEL_COURSES)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setColor(ContextCompat.getColor(this, R.color.teal_700))
            .setContentTitle(getString(R.string.action_title))
            .setContentText(getString(R.string.action_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)//Establecemos el intent
            .setAutoCancel(true)//Borramos la notificación al momento de entrar al intent
            .build()

        NotificationManagerCompat.from(this).run {
            notify(++NOTIFICATION_ID,notification)
        }
    }

    private fun actionNotification() {

        //Similar al anterior, definimos un intent comunicándose con NotificationReceiver
        val acceptIntent = Intent(this, NotificationReceiver::class.java).apply {
            action = ACTION_RECEIVED
        }

        //creamos un PendingIntent que describe el pending anterior
        val acceptPendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, acceptIntent, 0)

        val notification = NotificationCompat.Builder(this, CHANNEL_COURSES)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setColor(ContextCompat.getColor(this, R.color.teal_700))
            .setContentTitle(getString(R.string.button_title))
            .setContentText(getString(R.string.button_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(R.drawable.ic_launcher_foreground, getString(R.string.button_text),acceptPendingIntent)//Establecemos el intent
            .build()

        NotificationManagerCompat.from(this).run {
            notify(++NOTIFICATION_ID,notification)
        }

    }

    private fun touchAndActionNotification(){

        val intent = Intent(this, Act2_ReturnFormNotification::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        //Similar al anterior, definimos un intent comunicándose con NotificationReceiver
        val acceptIntent = Intent(this, NotificationReceiver::class.java).apply {
            action = ACTION_RECEIVED
        }

        //Haremos un intent que se pueda ejecutar desde otra aplicación
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        //creamos un PendingIntent que describe el pending anterior
        val acceptPendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, acceptIntent, 0)

        val notification = NotificationCompat.Builder(this, CHANNEL_COURSES)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setColor(ContextCompat.getColor(this, R.color.teal_700))
            .setContentTitle(getString(R.string.action_title))
            .setContentText(getString(R.string.action_body))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)//Establecemos el intent
            .setAutoCancel(true)//Borramos la notificación al momento de entrar al intent
            .addAction(R.drawable.ic_launcher_foreground, getString(R.string.button_text),acceptPendingIntent)//Establecemos el intent
            .build()

        NotificationManagerCompat.from(this).run {
            notify(++NOTIFICATION_ID,notification)
        }
    }

}