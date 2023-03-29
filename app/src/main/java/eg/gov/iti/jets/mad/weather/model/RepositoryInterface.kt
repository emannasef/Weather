package eg.gov.iti.jets.mad.weather.model

import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {

    suspend fun getDataOverNetwork(lat:Double, lon:Double, language:String): Flow<MyResponse>

    suspend fun insertLocation(favLocation: FavLocation)
    suspend fun getFavLocations(): Flow<List<FavLocation>>
    suspend fun deleteLocation(location: FavLocation)
}