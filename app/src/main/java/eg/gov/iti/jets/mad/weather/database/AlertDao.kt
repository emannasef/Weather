package eg.gov.iti.jets.mad.weather.database

import androidx.room.*
import eg.gov.iti.jets.mad.weather.model.MyResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface AlertDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlert(alert: MyResponse.Alert)

    @Query("SELECT * FROM AlertTable")
    fun getAlerts(): Flow<List<MyResponse.Alert>>

    @Delete
    suspend fun deleteAlert( alert: MyResponse.Alert)


}