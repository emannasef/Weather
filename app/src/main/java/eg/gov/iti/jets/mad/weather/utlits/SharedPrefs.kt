package eg.gov.iti.jets.mad.weather.utlits

import android.content.Context
import android.content.SharedPreferences
import eg.gov.iti.jets.mad.weather.model.UserLocation
import eg.gov.iti.jets.mad.weather.utlits.Constants.MyConstants.LAT_KEY
import eg.gov.iti.jets.mad.weather.utlits.Constants.MyConstants.LON_KEY
import eg.gov.iti.jets.mad.weather.utlits.Constants.MyConstants.PREF_NAME


class SharedPrefs(var context: Context) {


    fun saveInPrefFile(lat: Float, lon: Float) {
        var sharedPrefs: SharedPreferences=
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        var editor = sharedPrefs.edit()
        editor.putFloat(LAT_KEY, lat)
        editor.putFloat(LON_KEY, lon)
        editor.apply()
    }
    fun getFromPrefFile(): UserLocation {

        val sharedPreferences =
            context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val lat = sharedPreferences.getFloat(LAT_KEY, 30.065681f)
        val lon = sharedPreferences.getFloat(LON_KEY, 30.642067f)


        return UserLocation(lat.toDouble(),lon.toDouble())
    }

}