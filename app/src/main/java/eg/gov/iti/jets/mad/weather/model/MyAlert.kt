package eg.gov.iti.jets.mad.weather.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert_table")
data class MyAlert(
    @PrimaryKey(autoGenerate = true)
    var id: Int?=null,
    var startDate: String,
    var endDate: String,
    var pickedTime: String
)