package eg.gov.iti.jets.mad.weather.utlits

import android.os.Build
import androidx.annotation.RequiresApi
import eg.gov.iti.jets.mad.weather.R
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

class Converter{
   companion object{
//       fun convertFromKelvinToCelsius(tempKelvin: Double): Double {
//
//               return (tempKelvin!! - 273.15f).roundToInt().toDouble()
//
//       }


       fun convertFromKelvinToCelsius(tempKelvin: Double): Int {
           val tempCelsius = (tempKelvin - 273.15f).roundToInt()
           return tempCelsius
       }
       fun getTime(pattern:String,date:Long): String =
           SimpleDateFormat(pattern, Locale.getDefault()).format(Date(date*1000))

       fun getIcon(icon:String): Int{
           return     when (icon) {
               "01d", "01n" -> return   R.drawable.wi_day_sunny
               "02d", "02n" -> return  R.drawable.wi_day_cloudy
               "03d", "03n" -> return  R.drawable.wi_cloud
               "04d", "04n" -> return  R.drawable.wi_cloudy
               "5d" , "5n"  -> return  R.drawable.wi_cloudy_windy
               "09d", "09n" -> return  R.drawable.wi_showers
               "10d", "10n" -> return  R.drawable.wi_rain
               "11d", "11n" -> return  R.drawable.wi_storm_showers
               "13d", "13n" -> return  R.drawable.wi_snow_wind
               else -> return   R.drawable.baseline_cloud_24        }
       }

       @RequiresApi(Build.VERSION_CODES.O)
       fun getDay(dt: Int, timezone: String, format: String = "EEE"): String {
           val zoneId = ZoneId.of(timezone)
           val instant = Instant.ofEpochSecond(dt.toLong())
           val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
           return instant.atZone(zoneId).format(formatter)
       }



   }
}