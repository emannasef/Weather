package eg.gov.iti.jets.mad.weather.network

import eg.gov.iti.jets.mad.weather.utlits.Constants.MyConstants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}