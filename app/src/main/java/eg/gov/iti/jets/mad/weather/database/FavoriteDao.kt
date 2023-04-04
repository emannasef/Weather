package eg.gov.iti.jets.mad.weather.database

import androidx.room.*
import eg.gov.iti.jets.mad.weather.model.FavLocation
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteDao {

   /// @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(favLocation: FavLocation):Long

    @Query("SELECT * FROM fav_table")
    fun getFavLocations(): Flow<List<FavLocation>>

    @Delete
    suspend fun deleteLocation(favLocation: FavLocation)
}