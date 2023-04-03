package eg.gov.iti.jets.mad.weather.network

import eg.gov.iti.jets.mad.weather.model.MyResponse
import eg.gov.iti.jets.mad.weather.utlits.Constants
import retrofit2.Response

interface RemoteSource {
    suspend fun getDataFromNetwork(
        lat: Double,
        lon: Double,
        language: String = Constants.EN,
        exclude: String = Constants.EXCLUDE,
        apiKey: String = Constants.API_KEY
    ): MyResponse

}