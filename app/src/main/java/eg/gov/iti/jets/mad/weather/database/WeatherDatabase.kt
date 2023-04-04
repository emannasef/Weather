package eg.gov.iti.jets.mad.weather.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import eg.gov.iti.jets.mad.weather.model.*


@Database(
    entities = arrayOf(FavLocation::class, MyAlert::class, BackupModel::class),
    version = 2,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun getFavoriteDao(): FavoriteDao
    abstract fun getAlertDao(): AlertDao
    abstract fun getBackupDao(): BackupDao

    companion object {
        private var INSTANCE: WeatherDatabase? = null
        fun getInstance(ctx: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    WeatherDatabase::class.java, "my_weather_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}