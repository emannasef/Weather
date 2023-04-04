package eg.gov.iti.jets.mad.weather.utlits

fun getTemp(temp: Double,sharedPrefs:SharedPrefs): Int {
    return when (sharedPrefs.getTemp()) {
        Constants.CELSIUS -> {
            Utlits.convertFromKelvinToCelsius(temp)
        }
        Constants.FAHRENHEIT -> {
            Utlits.convertFromKelvinToFahrenheit(temp)
        }
        else -> {
            temp.toInt()
        }
    }
}

 fun changeGrade(sharedPrefs:SharedPrefs):String{
     return when (sharedPrefs.getTemp()) {
        Constants.CELSIUS -> "C"
        Constants.FAHRENHEIT -> "F"
        else -> "K"

    }
}

fun getWindSpeed(wind: Double,sharedPrefs: SharedPrefs): Double {
    val windUnit = sharedPrefs.getWindSpeed()
    return if (windUnit == Constants.MILE_HOUR) {
        Utlits.convertMeterspersecToMilesperhour(wind)
    } else {
        wind
    }
}