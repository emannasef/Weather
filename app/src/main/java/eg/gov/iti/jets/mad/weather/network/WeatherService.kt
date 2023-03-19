package eg.gov.iti.jets.mad.weather.network

import eg.gov.iti.jets.mad.weather.model.MyResponse
import eg.gov.iti.jets.mad.weather.utlits.Constants.MyConstants.API_KEY
import eg.gov.iti.jets.mad.weather.utlits.Constants.MyConstants.EXCLUDE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {

    @GET("onecall")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") language: String="en",
        @Query("exclude") exclude: String = EXCLUDE,
        @Query("appid") apiKey: String = API_KEY
    ): Response<MyResponse>

}


