package eg.gov.iti.jets.mad.weather.utlits

import eg.gov.iti.jets.mad.weather.model.FavLocation
import eg.gov.iti.jets.mad.weather.model.MyAlert

sealed class RoomState {

    class Success(val data: List<FavLocation> ):RoomState()
    class SuccessAlert(val alertData: List<MyAlert> ):RoomState()
    class Failure(val msg:Throwable):RoomState()
    object Loading:RoomState()
}