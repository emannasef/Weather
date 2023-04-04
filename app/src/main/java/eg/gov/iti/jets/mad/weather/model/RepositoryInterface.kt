package eg.gov.iti.jets.mad.weather.model

import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {

    suspend fun getDataOverNetwork(lat:Double, lon:Double, language:String): Flow<MyResponse>

    suspend fun insertDataToBackup(backupModel: BackupModel)
    suspend fun getBackupData() : Flow<BackupModel>

    suspend fun insertLocation(favLocation: FavLocation)
    suspend fun getFavLocations(): Flow<List<FavLocation>>
    suspend fun deleteLocation(location: FavLocation)


    suspend fun insertAlert(myAlert: MyAlert)
    suspend fun getAlerts(): Flow<List<MyAlert>>
    suspend fun deleteAlert(alert: MyAlert)
    suspend  fun getDataForWorkManger(lat:Double, lon:Double,lang:String):MyResponse

}