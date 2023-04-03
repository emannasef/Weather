package eg.gov.iti.jets.mad.weather.database

import androidx.room.*
import eg.gov.iti.jets.mad.weather.model.MyAlert
import eg.gov.iti.jets.mad.weather.model.MyResponse
import kotlinx.coroutines.flow.Flow


@Dao
interface AlertDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlert(alert: MyAlert)

    @Query("SELECT * FROM alert_table")
    fun getAlerts(): Flow<List<MyAlert>>

    @Delete
    suspend fun deleteAlert( alert: MyAlert)


}