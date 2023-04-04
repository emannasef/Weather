package eg.gov.iti.jets.mad.weather.view.alertView

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import eg.gov.iti.jets.mad.weather.R
import eg.gov.iti.jets.mad.weather.database.ConcreteLocalSource
import eg.gov.iti.jets.mad.weather.model.Repository
import eg.gov.iti.jets.mad.weather.network.WeatherClient
import eg.gov.iti.jets.mad.weather.utlits.Constants
import eg.gov.iti.jets.mad.weather.utlits.Constants.CHANNEL_ID
import eg.gov.iti.jets.mad.weather.utlits.Constants.NOTIFICATION_ID
import eg.gov.iti.jets.mad.weather.utlits.SharedPrefs
import java.util.*
import android.provider.Settings


class MyWorker(private var context: Context, var workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    private lateinit var repository: Repository
    private lateinit var sharedPrefs: SharedPrefs

    private val endDate = inputData.getLong("workerEndDate", 0L)
    private val nowDate = Calendar.getInstance().timeInMillis
    private lateinit var description: String


    override suspend fun doWork(): Result {
        try {
            if (nowDate > endDate) {
                sharedPrefs = SharedPrefs(context)
                repository =
                    Repository.getInstance(
                        ConcreteLocalSource(context),
                        WeatherClient.getInstance()
                    )
                val response = repository.getDataForWorkManger(
                    sharedPrefs.getLocFromPrefFile().latidute,
                    sharedPrefs.getLocFromPrefFile().longitude,
                    sharedPrefs.getLang()
                )
                println("@@@@@@@@@@@@@@@@@@@@@@@@@$response")
                if (response.alerts.isNullOrEmpty()) {
                    description = response.current!!.weather.get(0).description
                    println("@@@@@@@@@@@@@@@@@@@@@@@@@$description")
                } else {
                    description = response.alerts[0].description
                    println("@@@@@@@@@@@@@@@@@@@@@@@@@$description")
                }

                println("Description:#######################$description")
                if (sharedPrefs.getAlert() == Constants.NOTIFICATION) {
                    println("NOOOOOOOOOOOOOOOOOOOOOTIIIIIIIIIIIIFICATIONNNNNNNNNNNNNNNNNNNNNN")
                    showNotification(description)
                } else {
                    println("AAAAAAAAAAAAAAAAALLLLLLLLLLLLLLLLAAAAAAAAAAAARRRRRRTTTTTTTTTT")
                    checkOverlayPermission()
                    startAlarm(description)
                }


                return Result.success(workDataOf("1" to "success"))
            } else
                return Result.failure(workDataOf("0" to "failure"))
        } catch (e: Exception) {
            return Result.failure(workDataOf("0" to e.message))
        }

    }

    private fun showNotification(description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.cloud)
            .setContentTitle("Weather App")
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }

    }

    fun startAlarm(description: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Settings.canDrawOverlays(context)) {
                // start the service based on the android version
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val intent = Intent(context, ForegroundService::class.java)
                    intent.putExtra("dec", description)
                    println("MMMMMMMMMMMMMMMMDISCRIPTIONNNNNNNNNNNNNN$description")

                    applicationContext.startForegroundService(intent)

                } else {
                    val intent = Intent(context, ForegroundService::class.java)
                    intent.putExtra("dec", description)
                    println("MMMMMMMMMMMMMMMMDISCRIPTIONNNNNNNNNNNNNN$description")
                    applicationContext.startService(intent)
                }
            }
        } else {
            val intent = Intent(context, ForegroundService::class.java)
            intent.putExtra("dec", description)
            println("MMMMMMMMMMMMMMMMDISCRIPTIONNNNNNNNNNNNNN$description")

            applicationContext.startService(intent)
        }
    }

    fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                // send user to the device settings
                val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                applicationContext.startActivity(myIntent)
            }
        }
    }


}