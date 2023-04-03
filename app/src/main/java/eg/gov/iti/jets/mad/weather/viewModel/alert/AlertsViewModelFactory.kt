package eg.gov.iti.jets.mad.weather.viewModel.alert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.gov.iti.jets.mad.weather.model.RepositoryInterface

class AlertsViewModelFactory (private val _iRepo: RepositoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlertsViewModel::class.java)) {
            AlertsViewModel(_iRepo) as T
        } else {
            throw java.lang.IllegalArgumentException("ViewModel Class not found")
        }

    }
}