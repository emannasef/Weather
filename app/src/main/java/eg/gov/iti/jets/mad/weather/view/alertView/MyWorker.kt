package eg.gov.iti.jets.mad.weather.view.alertView

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import eg.gov.iti.jets.mad.weather.MainActivity
import eg.gov.iti.jets.mad.weather.R
import eg.gov.iti.jets.mad.weather.database.ConcreteLocalSource
import eg.gov.iti.jets.mad.weather.model.MyResponse
import eg.gov.iti.jets.mad.weather.model.Repository
import eg.gov.iti.jets.mad.weather.network.WeatherClient
import eg.gov.iti.jets.mad.weather.utlits.Constants
import eg.gov.iti.jets.mad.weather.utlits.Constants.CHANNEL_ID
import eg.gov.iti.jets.mad.weather.utlits.Constants.NOTIFICATION_ID
import eg.gov.iti.jets.mad.weather.utlits.SharedPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

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
                println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
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
                description = if (response.alerts.isNullOrEmpty()) ({
                    response.current?.weather?.get(0)?.description
                }.toString())
                else {
                    response.alerts[0].description
                }

                println("Description:$description")

                if (sharedPrefs.getAlert() == Constants.NOTIFICATION) {
                    println("Description:#######################$description")

                } else {
                   println("EEEEEEEEEEEEEEMMMMMMMMMMMMMMMMMMAAAAAAAAAAAAAAAAAANNNNNNNNNNNNNNN")
                }

                showNotification(description)
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




}