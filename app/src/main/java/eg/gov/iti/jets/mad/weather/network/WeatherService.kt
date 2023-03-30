package eg.gov.iti.jets.mad.weather.network

import eg.gov.iti.jets.mad.weather.model.MyResponse
import eg.gov.iti.jets.mad.weather.utlits.Constants

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {

    @GET("onecall")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") language: String="en",
        @Query("exclude") exclude: String = Constants.EXCLUDE,
        @Query("appid") apiKey: String = Constants.API_KEY
    ): Response<MyResponse>

}


