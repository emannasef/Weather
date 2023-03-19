package eg.gov.iti.jets.mad.weather.model

import eg.gov.iti.jets.mad.weather.database.LocalSource
import eg.gov.iti.jets.mad.weather.network.RemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository private constructor(var localSource: LocalSource, var remoteSource: RemoteSource): RepositoryInterface {

    companion object{
        private  var instance :Repository?=null
        fun getInstance( localSource: LocalSource, remoteSource: RemoteSource):Repository{
            return instance?:synchronized(this){
                val temp = Repository(localSource,remoteSource)
                instance=temp
                return temp

            }
        }
    }

//    override suspend fun getHoursOverNetwork(
//        lat: Double,
//        lon: Double,
//        language: String
//    ): Flow<List<Hourly>> {
//        return flowOf(  remoteSource.getHoursFromNetwork(lat= lat, lon = lon , language = language).body()?.hourly
//            ?: listOf())
//    }

    override suspend fun getAllDataOverNetwork(
        lat: Double,
        lon: Double,
        language: String
    ): Flow<MyResponse> {
        return flowOf(
            remoteSource.getHoursFromNetwork(lat= lat, lon = lon , language = language).body() ?: MyResponse(
                listOf(), listOf(),31.88855,31.88888,"hj",5
            )
        )
    }


}