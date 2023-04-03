package eg.gov.iti.jets.mad.weather.database

import androidx.room.Delete
import androidx.room.Query
import eg.gov.iti.jets.mad.weather.model.FavLocation
import eg.gov.iti.jets.mad.weather.model.MyAlert
import kotlinx.coroutines.flow.Flow

interface LocalSource {

    suspend fun insertLocation(favLocation: FavLocation)
    fun getFavLocations(): Flow<List<FavLocation>>
    suspend fun deleteLocation(location: FavLocation)

    suspend fun insertAlert(myAlert: MyAlert)
    fun getAlerts(): Flow<List<MyAlert>>
    suspend fun deleteAlert(myAlert: MyAlert)
}