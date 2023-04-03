package eg.gov.iti.jets.mad.weather.network

import eg.gov.iti.jets.mad.weather.model.MyResponse
import retrofit2.Response

class FakeWeatherClient(var myResponse: MyResponse):RemoteSource {
    override suspend fun getDataFromNetwork(
        lat: Double,
        lon: Double,
        language: String,
        exclude: String,
        apiKey: String
    ): MyResponse {
       return myResponse

    }
}