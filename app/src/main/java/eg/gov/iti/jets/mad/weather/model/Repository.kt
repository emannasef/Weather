package eg.gov.iti.jets.mad.weather.model

import eg.gov.iti.jets.mad.weather.database.LocalSource
import eg.gov.iti.jets.mad.weather.network.RemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

    override suspend fun getDataOverNetwork(
        lat: Double,
        lon: Double,
        language: String
    ): Flow<MyResponse> {
//        return flowOf(
//            remoteSource.getHoursFromNetwork(lat= lat, lon = lon , language = language).body() ?: MyResponse(
//                listOf(), listOf(),lat,lon,"Africa/Cairo",7200
//            )

        return flow {
          emit(
              remoteSource.getDataFromNetwork(lat= lat, lon = lon , language = language)
          )
        }

    }

    override suspend fun insertLocation(favLocation: FavLocation) {
        localSource.insertLocation(favLocation)
    }

    override suspend fun getFavLocations(): Flow<List<FavLocation>> {
        return localSource.getFavLocations()
    }

    override suspend fun deleteLocation(location: FavLocation) {
        localSource.deleteLocation(location)
    }

    override suspend fun insertAlert(myAlert: MyAlert) {
        localSource.insertAlert(myAlert)
    }

    override suspend fun getAlerts(): Flow<List<MyAlert>> {
      return  localSource.getAlerts()
    }

    override suspend fun deleteAlert(alert: MyAlert) {
        localSource.deleteAlert(alert)
    }

    override suspend fun getDataForWorkManger(lat: Double, lon: Double,lang:String): MyResponse {
        return remoteSource.getDataFromNetwork(lat,lon,lang)
    }


}