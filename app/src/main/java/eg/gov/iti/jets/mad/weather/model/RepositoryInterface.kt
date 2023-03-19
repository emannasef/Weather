package eg.gov.iti.jets.mad.weather.model

import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {

    suspend fun getAllDataOverNetwork(lat:Double,lon:Double,language:String): Flow<MyResponse>
}