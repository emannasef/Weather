package eg.gov.iti.jets.mad.weather.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import eg.gov.iti.jets.mad.weather.model.FavLocation


@Database(entities = arrayOf(FavLocation::class), version = 1)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
    companion object{
        private var INSTANCE: WeatherDatabase?=null
        fun getInstance(ctx: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this){
                val instance= Room.databaseBuilder(ctx.applicationContext,
                    WeatherDatabase::class.java,"weather_database").build()
                INSTANCE =instance
                instance
            }
        }
    }

}