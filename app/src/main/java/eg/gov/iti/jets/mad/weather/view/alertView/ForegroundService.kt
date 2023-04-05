package eg.gov.iti.jets.mad.weather.view.alertView

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import eg.gov.iti.jets.mad.weather.R


class ForegroundService() : Service() {
    var desc:String=""
    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }
    override fun onCreate() {
        super.onCreate()
        // create the custom or default notification
        // based on the android version

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startMyOwnForeground(desc) else startForeground(
            1,
            Notification()
        )



    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
       val desc= intent.getStringExtra("dec").toString()
        println("Discriptioooooooooooooooooonnnnnnnn$desc")
        startMyOwnForeground(desc)
        // create an instance of Window class
        // and display the content on screen
        val window = Window(this,desc)
        window.open()
        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startMyOwnForeground(description:String) {
        val NOTIFICATION_CHANNEL_ID = "example.permanence"
        val channelName = "Background Service"
        val chan = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_MIN
        )
        val manager = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(chan)
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        val notification: Notification = notificationBuilder.setOngoing(true)
            .setContentTitle("Service running")
            .setContentText(description) // this is important, otherwise the notification will show the way
            // you want i.e. it will show some default notification
            .setSmallIcon(R.drawable.imageheader)
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(2, notification)
    }


}
