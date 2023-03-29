package eg.gov.iti.jets.mad.weather.view.favView

import eg.gov.iti.jets.mad.weather.model.FavLocation

interface OnAdapterClickListener {
    fun deleteFav(favLocation: FavLocation)

    fun viewData(favLocation: FavLocation)
}