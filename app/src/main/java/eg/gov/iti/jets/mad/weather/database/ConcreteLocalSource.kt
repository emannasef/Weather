package eg.gov.iti.jets.mad.weather.database

import android.content.Context
import eg.gov.iti.jets.mad.weather.model.FavLocation
import eg.gov.iti.jets.mad.weather.model.MyAlert
import kotlinx.coroutines.flow.Flow

class ConcreteLocalSource(context: Context) : LocalSource {
    private val weatherDao:WeatherDao by lazy {
        WeatherDatabase.getInstance(context).getWeatherDao()
    }
    private val alertDao:AlertDao by lazy {
        WeatherDatabase.getInstance(context).getAlertDao()
    }
    override suspend fun insertLocation(favLocation: FavLocation) {
        weatherDao.insertLocation(favLocation)
    }

    override fun getFavLocations(): Flow<List<FavLocation>> {
      return weatherDao.getFavLocations()
    }

    override suspend fun deleteLocation(favLocation: FavLocation) {
        weatherDao.deleteLocation(favLocation)
    }

    override suspend fun insertAlert(myAlert: MyAlert) {
        alertDao.insertAlert(myAlert)
    }

    override fun getAlerts(): Flow<List<MyAlert>> {
        return alertDao.getAlerts()
    }

    override suspend fun deleteAlert(myAlert: MyAlert) {
        alertDao.deleteAlert(myAlert)
    }
}