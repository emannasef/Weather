package eg.gov.iti.jets.mad.weather.viewModel.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.mad.weather.model.FavLocation
import eg.gov.iti.jets.mad.weather.model.RepositoryInterface
import eg.gov.iti.jets.mad.weather.utlits.ApiState
import eg.gov.iti.jets.mad.weather.utlits.RoomState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavViewModel(private val _irepo: RepositoryInterface) : ViewModel() {

    private var _favStateFlow = MutableStateFlow<RoomState>(RoomState.Loading)
    val favStateFlow = _favStateFlow.asStateFlow()

    fun insertFavorite(favLocation: FavLocation){
        viewModelScope.launch (Dispatchers.IO){
            _irepo.insertLocation(favLocation)
        }
    }

    fun getFavLocations() {
        viewModelScope.launch(Dispatchers.IO) {

            _irepo.getFavLocations().catch {
                    e->
                _favStateFlow.value = RoomState.Failure(e)
            }.collect { data ->
                _favStateFlow.value = RoomState.Success(data)
            }
        }
    }

    fun deleteFromFav(location: FavLocation){
        viewModelScope.launch (Dispatchers.IO){
            _irepo.deleteLocation(location)
        }
    }
}