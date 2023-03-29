package eg.gov.iti.jets.mad.weather.viewModel.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.gov.iti.jets.mad.weather.model.RepositoryInterface
import eg.gov.iti.jets.mad.weather.viewModel.home.HomeViewModel

class FavViewModelFactory (private val _irepo: RepositoryInterface): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavViewModel::class.java)){
            FavViewModel(_irepo) as T
        }else{
            throw java.lang.IllegalArgumentException("ViewModel Class not found")
        }

    }
}