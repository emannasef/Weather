package eg.gov.iti.jets.mad.weather.database

import eg.gov.iti.jets.mad.weather.model.FavLocation
import eg.gov.iti.jets.mad.weather.model.MyAlert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeLocalSource(
    var favList: MutableList<FavLocation>? = mutableListOf(),
    var alertList: MutableList<MyAlert>? = mutableListOf()
) : LocalSource {

    override suspend fun insertLocation(favLocation: FavLocation) {
        favList?.add(favLocation)
    }

    override fun getFavLocations(): Flow<List<FavLocation>> {
        return flow {
            favList?.let { emit(it) }
        }
    }

    override suspend fun deleteLocation(location: FavLocation) {
       favList?.remove(location)
    }

    override suspend fun insertAlert(myAlert: MyAlert) {
       alertList?.add(myAlert)
    }

    override fun getAlerts(): Flow<List<MyAlert>> {
        return flow{
            alertList?.let { emit(it) }
        }
    }

    override suspend fun deleteAlert(myAlert: MyAlert) {
        alertList?.remove(myAlert)
    }
}