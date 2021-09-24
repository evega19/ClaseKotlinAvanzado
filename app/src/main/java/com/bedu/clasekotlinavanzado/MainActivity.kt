package com.bedu.clasekotlinavanzado

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.bedu.clasekotlinavanzado.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener

import com.google.firebase.messaging.FirebaseMessaging


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

        //Es para crear grupos
        val GRUPO_SIMPLE = "GRUPO_SIMPLE"
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

        getFCMtoFirebase()

        binding.run{
            //Aqu[i colocamos nuestros listeners
            btnNotify.setOnClickListener { simpleNotification() }
            btnNotify.setOnLongClickListener { touchAndActionNotification()
            true}
            btnActionNotify.setOnClickListener { touchNotification() }
            btnNotifyWithBtn.setOnClickListener { actionNotification() }
            btnExpandable.setOnClickListener { expandableNotification() }
            btnCustom.setOnClickListener { custonNotification()  }
            btnCancelNoti.setOnClickListener { cancelNotification() }
        }
    }

    private fun getFCMtoFirebase(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("Error", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.e("FCM_TOKEN", token.toString())
            Toast.makeText(baseContext,"FCM token: $token", Toast.LENGTH_SHORT).show()
        })
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

        var notification2 = NotificationCompat.Builder(this, CHANNEL_DIVERSES)
            .setSmallIcon(R.drawable.ic_launcher_foreground) //seteamos el ícono de la push notification
            .setColor(ContextCompat.getColor(this,R.color.triforce)) //definimos el color del ícono y el título de la notificación
            .setContentTitle(getString(R.string.simple_title_2)) //seteamos el título de la notificación
            .setContentText(getString(R.string.simple_body_2)) //seteamos el cuerpo de la notificación
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) //Ponemos una prioridad por defecto
            .setGroup(GRUPO_SIMPLE)
            .build()

        var notification3 = NotificationCompat.Builder(this, CHANNEL_DIVERSES)
            .setSmallIcon(R.drawable.ic_launcher_foreground) //seteamos el ícono de la push notification
            .setColor(ContextCompat.getColor(this,R.color.triforce)) //definimos el color del ícono y el título de la notificación
            .setContentTitle(getString(R.string.simple_title_3)) //seteamos el título de la notificación
            .setContentText(getString(R.string.simple_body_3)) //seteamos el cuerpo de la notificación
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) //Ponemos una prioridad por defecto
            .setGroup(GRUPO_SIMPLE)
            .build()

        val summaryNotification = NotificationCompat.Builder(this@MainActivity, CHANNEL_DIVERSES)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setGroup(GRUPO_SIMPLE)
            .setGroupSummary(true)
            .build()

        //Ya tenemos la notificación pero hay que registrarla
        NotificationManagerCompat.from(this).run {
            notify(20, notification)
            notify(21, notification2)
            notify(22, notification3)
            notify(23, summaryNotification)
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

    private fun expandableNotification() {

        val notification = NotificationCompat.Builder(this, CHANNEL_COURSES)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setColor(ContextCompat.getColor(this,R.color.triforce))
            .setContentTitle(getString(R.string.simple_title))
            .setContentText(getString(R.string.large_text))
            .setLargeIcon(getDrawable(R.drawable.ic_launcher_foreground)?.toBitmap())//ícono grande a la derecha
            .setStyle(NotificationCompat.BigTextStyle()//este estilo define al expandible
                .bigText(getString(R.string.large_text))) //Cuerpo de la notificación cuando se expande
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(this).run {
            notify(42,notification)
        }
    }

    private fun custonNotification() {
        val notificationLayout = RemoteViews(packageName,R.layout.notification_custon)
        val notificationLayoutExpanded = RemoteViews(packageName,R.layout.notification_custon_expanded)

        val notification = NotificationCompat.Builder(this, CHANNEL_DIVERSES)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayoutExpanded)
            .build()

        NotificationManagerCompat.from(this).run{
            notify(50,notification)
        }
    }

    private fun cancelNotification() {
        with(NotificationManagerCompat.from(this)) {
            cancelAll()
        }
    }


}