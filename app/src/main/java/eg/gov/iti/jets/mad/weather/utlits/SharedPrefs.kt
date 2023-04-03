package eg.gov.iti.jets.mad.weather.utlits

import android.content.Context
import android.content.SharedPreferences
import eg.gov.iti.jets.mad.weather.model.UserLocation


class SharedPrefs(var context: Context) {


    fun saveLocInPrefFile(lat: Float, lon: Float) {
        var sharedPrefs: SharedPreferences =
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        var editor = sharedPrefs.edit()
        editor.putFloat(Constants.LAT_KEY, lat)
        editor.putFloat(Constants.LON_KEY, lon)
        editor.apply()
    }

    fun getLocFromPrefFile(): UserLocation {

        val sharedPreferences =
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

        val lat = sharedPreferences.getFloat(Constants.LAT_KEY, 30.065681f)
        val lon = sharedPreferences.getFloat(Constants.LON_KEY, 30.642067f)


        return UserLocation(lat.toDouble(), lon.toDouble())
    }

    fun setLang(lang: String) {
        var sharedPrefs: SharedPreferences =
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        var editor = sharedPrefs.edit()
        editor.putString(Constants.LANGUAGE, lang)
        editor.apply()
    }

    fun getLang():String {
        val sharedPreferences =
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.LANGUAGE, Constants.EN).toString()

    }

    fun setLocation(location: String) {
        var sharedPrefs: SharedPreferences =
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        var editor = sharedPrefs.edit()
        editor.putString(Constants.LOCATION, location)
        editor.apply()
    }

    fun getLocation():String {
        val sharedPreferences =
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.LOCATION, Constants.GPS).toString()

    }

    fun setTemp(temp: String) {
        var sharedPrefs: SharedPreferences =
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        var editor = sharedPrefs.edit()
        editor.putString(Constants.TEMPERATURE, temp)
        editor.apply()
    }

    fun getTemp():String {
        val sharedPreferences =
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.TEMPERATURE, Constants.KELVIN).toString()

    }

    fun setWindSpeed(windUnit: String) {
        var sharedPrefs: SharedPreferences =
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        var editor = sharedPrefs.edit()
        editor.putString(Constants.WINDUNIT, windUnit)
        editor.apply()
    }

    fun getWindSpeed():String {
        val sharedPreferences =
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.WINDUNIT, Constants.METER_SEC).toString()

    }

    fun setAlert(alert: String) {
        var sharedPrefs: SharedPreferences =
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        var editor = sharedPrefs.edit()
        editor.putString(Constants.ALERT, alert)
        editor.apply()
    }
    fun getAlert(): String {
        val sharedPreferences =
            context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(Constants.ALERT, Constants.ALARM).toString()
    }


}