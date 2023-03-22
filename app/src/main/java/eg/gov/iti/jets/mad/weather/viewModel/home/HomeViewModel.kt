package eg.gov.iti.jets.mad.weather.viewModel.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.mad.weather.model.RepositoryInterface
import eg.gov.iti.jets.mad.weather.utlits.ApiState
import eg.gov.iti.jets.mad.weather.utlits.Constants.MyConstants.lat
import eg.gov.iti.jets.mad.weather.utlits.Constants.MyConstants.lon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val _irepo: RepositoryInterface) : ViewModel() {

    private var _stateFlow = MutableStateFlow<ApiState>(ApiState.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    init {
     //   getWeatherOverNetwork(lat, lon, "ar")
        getWeatherOverNetwork(lat, lon, "en")
    }

    private fun getWeatherOverNetwork(lat: Double, lon: Double, language: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _irepo.getAllDataOverNetwork(lat, lon, language).catch { e ->
                _stateFlow.value = ApiState.Failure(e)
            }.collect { data ->
               //   println("########################${data.daily}")
                println("########################${data.hourly}")
                _stateFlow.value = ApiState.Success(data)
            }

        }
    }
}