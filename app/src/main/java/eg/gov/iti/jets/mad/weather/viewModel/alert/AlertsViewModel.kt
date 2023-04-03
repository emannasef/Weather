package eg.gov.iti.jets.mad.weather.viewModel.alert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.mad.weather.model.MyAlert
import eg.gov.iti.jets.mad.weather.model.RepositoryInterface
import eg.gov.iti.jets.mad.weather.utlits.RoomState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AlertsViewModel(private val _iRepo: RepositoryInterface) : ViewModel() {

    private var _alertStateFlow = MutableStateFlow<RoomState>(RoomState.Loading)
    val alertStateFlow = _alertStateFlow.asStateFlow()

    init {
        getAlerts()
    }
    fun insertAlert(myAlert: MyAlert) {
        viewModelScope.launch(Dispatchers.IO) {
            _iRepo.insertAlert(myAlert)
        }
    }

    fun deleteAlert(myAlert: MyAlert) {
        viewModelScope.launch(Dispatchers.IO) {
            _iRepo.deleteAlert(myAlert)
        }
    }

    fun getAlerts() {
        viewModelScope.launch(Dispatchers.IO) {
            _iRepo.getAlerts().catch { e ->
                _alertStateFlow.value = RoomState.Failure(e)
            }.collect { data ->
                _alertStateFlow.value = RoomState.SuccessAlert(data)
            }
        }
    }
}