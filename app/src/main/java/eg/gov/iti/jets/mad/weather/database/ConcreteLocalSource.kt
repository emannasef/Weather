package eg.gov.iti.jets.mad.weather.database

import android.content.Context
import eg.gov.iti.jets.mad.weather.model.FavLocation
import kotlinx.coroutines.flow.Flow

class ConcreteLocalSource(context: Context) : LocalSource {
    private val weatherDao:WeatherDao by lazy {
        WeatherDatabase.getInstance(context).getWeatherDao()
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
}