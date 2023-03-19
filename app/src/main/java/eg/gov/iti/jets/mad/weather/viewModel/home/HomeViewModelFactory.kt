package eg.gov.iti.jets.mad.weather.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.gov.iti.jets.mad.weather.model.RepositoryInterface

class HomeViewModelFactory (private val _irepo: RepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            HomeViewModel(_irepo)as T
        }else{
            throw java.lang.IllegalArgumentException("ViewModel Class not found")
        }

    }
}