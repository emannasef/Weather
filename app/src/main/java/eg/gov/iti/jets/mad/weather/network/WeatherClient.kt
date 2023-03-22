package eg.gov.iti.jets.mad.weather.network

import eg.gov.iti.jets.mad.weather.model.MyResponse
import retrofit2.Response

class WeatherClient private constructor() : RemoteSource{

    companion object{
        private var instance:WeatherClient?=null
        fun getInstance():WeatherClient{
            return instance?: synchronized(this){
                val temp =WeatherClient()
                instance=temp
                return temp
            }
        }
    }

    val weatherService:WeatherService by lazy {
        RetrofitHelper.retrofit.create(WeatherService::class.java)
    }

    override suspend fun getDataFromNetwork(
        lat: Double,
        lon: Double,
        language: String,
        exclude: String,
        apiKey: String
    ): Response<MyResponse> {
        return weatherService.getCurrentWeather(lat, lon, language,exclude, apiKey)
    }


}