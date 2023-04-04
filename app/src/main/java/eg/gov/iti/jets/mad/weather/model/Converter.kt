package eg.gov.iti.jets.mad.weather.model

import androidx.room.TypeConverter
import com.google.gson.Gson

object Converter {
    @TypeConverter
    fun fromStringToData(stringData: String)= Gson().fromJson(stringData, MyResponse::class.java)
    @TypeConverter
    fun fromDataToString(myResponse: MyResponse)=Gson().toJson(myResponse)

}