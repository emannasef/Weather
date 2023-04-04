package eg.gov.iti.jets.mad.weather.model

import eg.gov.iti.jets.mad.weather.database.FakeLocalSource
import eg.gov.iti.jets.mad.weather.network.FakeWeatherClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeRepository (var fakeLocalSource: FakeLocalSource,var fakeWeatherClient: FakeWeatherClient):RepositoryInterface{
    override suspend fun getDataOverNetwork(
        lat: Double,
        lon: Double,
        language: String
    ): Flow<MyResponse> {
      return flowOf(fakeWeatherClient.myResponse)
    }

    override suspend fun insertDataToBackup(backupModel: BackupModel) {
       fakeLocalSource.insertDataToBackup(backupModel)
    }

    override suspend fun getBackupData(): Flow<BackupModel> {
     return flow { fakeLocalSource.myBackupModel }
    }

    override suspend fun insertLocation(favLocation: FavLocation) {
        fakeLocalSource.insertLocation(favLocation)
    }

    override suspend fun getFavLocations(): Flow<List<FavLocation>> {
        return flow {
            fakeLocalSource.favList
        }
    }

    override suspend fun deleteLocation(location: FavLocation) {
        fakeLocalSource.deleteLocation(location)
    }

    override suspend fun insertAlert(myAlert: MyAlert) {
        fakeLocalSource.insertAlert(myAlert)
    }

    override suspend fun getAlerts(): Flow<List<MyAlert>> {
        return flow {
            fakeLocalSource.alertList
        }
    }

    override suspend fun deleteAlert(alert: MyAlert) {
        fakeLocalSource.deleteAlert(alert)
    }

    override suspend fun getDataForWorkManger(lat: Double, lon: Double, lang: String): MyResponse {
        return fakeWeatherClient.myResponse
    }
}