package eg.gov.iti.jets.mad.weather.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "weather_table")
data class FavLocation(@PrimaryKey var latitude:Double, var longitude:Double, var address:String = ""):
    Serializable