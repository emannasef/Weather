package eg.gov.iti.jets.mad.weather.viewModel.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.mad.weather.model.RepositoryInterface
import eg.gov.iti.jets.mad.weather.utlits.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val _irepo: RepositoryInterface) : ViewModel() {

    private var _stateFlow = MutableStateFlow<ApiState>(ApiState.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    fun getWeatherOverNetwork(lat: Double, lon: Double, language: String) {
        println("%%%%%%%%%%%%%%%%%%%%%%%%%LAT${lat}^^^^^^^^^^^^^^^^^LON$lon")
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getDataOverNetwork(lat, lon, language).catch { e ->
                _stateFlow.value = ApiState.Failure(e)
            }.collect { data ->
               //   println("########################${data.daily}")
              //  println("########################${data.hourly}")
                _stateFlow.value = ApiState.Success(data)
            }

        }
    }
}