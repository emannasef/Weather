package eg.gov.iti.jets.mad.weather.utlits

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object TempConverter{
    fun convertFromKelvinToCelsius(tempKelvin: Double): Double {
        return (tempKelvin - 273.15f).roundToInt().toDouble()
    }
    fun getTime(pattern:String,date:Long)=
        SimpleDateFormat(pattern, Locale.getDefault()).format(Date(date*1000))

}