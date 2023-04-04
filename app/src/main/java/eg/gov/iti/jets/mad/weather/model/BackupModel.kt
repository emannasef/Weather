package eg.gov.iti.jets.mad.weather.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RoomBackupTable")
data class BackupModel(
    @PrimaryKey(autoGenerate = false)
    val id:Int?=null,
    val weather: MyResponse

)
