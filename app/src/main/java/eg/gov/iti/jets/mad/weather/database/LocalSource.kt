package eg.gov.iti.jets.mad.weather.database

import androidx.room.Delete
import androidx.room.Query
import eg.gov.iti.jets.mad.weather.model.FavLocation
import kotlinx.coroutines.flow.Flow

interface LocalSource {

    suspend fun insertLocation(favLocation: FavLocation)

    fun getFavLocations(): Flow<List<FavLocation>>
    suspend fun deleteLocation(location: FavLocation)
}