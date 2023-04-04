package eg.gov.iti.jets.mad.weather.database

import android.content.Context
import eg.gov.iti.jets.mad.weather.model.BackupModel
import eg.gov.iti.jets.mad.weather.model.FavLocation
import eg.gov.iti.jets.mad.weather.model.MyAlert
import eg.gov.iti.jets.mad.weather.model.MyResponse
import kotlinx.coroutines.flow.Flow

class ConcreteLocalSource(context: Context) : LocalSource {
    private val favoriteDao: FavoriteDao by lazy {
        WeatherDatabase.getInstance(context).getFavoriteDao()
    }
    private val alertDao: AlertDao by lazy {
        WeatherDatabase.getInstance(context).getAlertDao()
    }

    private val backupDao: BackupDao by lazy {
        WeatherDatabase.getInstance(context).getBackupDao()
    }

    override suspend fun insertLocation(favLocation: FavLocation) {
        favoriteDao.insertLocation(favLocation)
    }

    override fun getFavLocations(): Flow<List<FavLocation>> {
        return favoriteDao.getFavLocations()
    }

    override suspend fun deleteLocation(favLocation: FavLocation) {
        favoriteDao.deleteLocation(favLocation)
    }

    override suspend fun insertAlert(myAlert: MyAlert) {
        alertDao.insertAlert(myAlert)
    }

    override fun getAlerts(): Flow<List<MyAlert>> {
        return alertDao.getAlerts()
    }

    override suspend fun deleteAlert(myAlert: MyAlert) {
        alertDao.deleteAlert(myAlert)
    }

    override suspend fun insertDataToBackup(backupModel: BackupModel) {
        backupDao.insertDataToBackup(backupModel)
    }

    override suspend fun getBackupData():Flow<BackupModel > {
        return backupDao.getBackupData()
    }


}