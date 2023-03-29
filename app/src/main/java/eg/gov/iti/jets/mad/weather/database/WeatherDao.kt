package eg.gov.iti.jets.mad.weather.database

import androidx.room.*
import com.google.android.gms.maps.model.LatLng
import eg.gov.iti.jets.mad.weather.model.FavLocation
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLocation(favLocation: FavLocation):Long

    @Query("SELECT * FROM weather_table")
    fun getFavLocations(): Flow<List<FavLocation>>

    @Delete
    suspend fun deleteLocation(favLocation: FavLocation)
}