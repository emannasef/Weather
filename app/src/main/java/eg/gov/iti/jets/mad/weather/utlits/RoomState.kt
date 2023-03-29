package eg.gov.iti.jets.mad.weather.utlits

import eg.gov.iti.jets.mad.weather.model.FavLocation

sealed class RoomState {

    class Success(val data: List<FavLocation> ):RoomState()
    class Failure(val msg:Throwable):RoomState()
    object Loading:RoomState()
}