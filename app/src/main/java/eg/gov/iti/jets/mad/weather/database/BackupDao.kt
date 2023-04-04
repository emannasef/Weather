package eg.gov.iti.jets.mad.weather.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eg.gov.iti.jets.mad.weather.model.BackupModel
import eg.gov.iti.jets.mad.weather.model.MyResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface BackupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataToBackup(backupModel: BackupModel)

    @Query("SELECT * FROM RoomBackupTable")
    fun getBackupData() : Flow<BackupModel>

}