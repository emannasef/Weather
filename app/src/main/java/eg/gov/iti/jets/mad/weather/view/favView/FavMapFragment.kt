package eg.gov.iti.jets.mad.weather.view.favView

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import eg.gov.iti.jets.mad.weather.R
import eg.gov.iti.jets.mad.weather.database.ConcreteLocalSource
import eg.gov.iti.jets.mad.weather.model.FavLocation
import eg.gov.iti.jets.mad.weather.model.Repository
import eg.gov.iti.jets.mad.weather.model.UserLocation
import eg.gov.iti.jets.mad.weather.network.WeatherClient
import eg.gov.iti.jets.mad.weather.utlits.SharedPrefs
import eg.gov.iti.jets.mad.weather.viewModel.fav.FavViewModel
import eg.gov.iti.jets.mad.weather.viewModel.fav.FavViewModelFactory
import java.util.*

class FavMapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var myMap: GoogleMap
    lateinit var favViewModel: FavViewModel
    lateinit var favViewModelFactory: FavViewModelFactory
    lateinit var geoCoder: Geocoder
    lateinit var address: MutableList<Address>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.fav_map_fragment) as SupportMapFragment

        mapFragment.getMapAsync(this)

        favViewModelFactory= FavViewModelFactory(Repository.getInstance(
            ConcreteLocalSource(requireContext()),WeatherClient.getInstance()
        ))
        favViewModel=ViewModelProvider(this,favViewModelFactory).get(FavViewModel::class.java)
        geoCoder = Geocoder(requireActivity(), Locale.getDefault())


    }

    override fun onMapReady(p0: GoogleMap) {
        myMap = p0

        setMapLongClick(myMap)
        setPoiClick(myMap)
    }
    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            // A snippet is additional text that's displayed after the title.
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            map.addMarker(
                MarkerOptions()
                    .position(latLng).title("dropped_pin")
                    .snippet(snippet)
            )

            showDialog(latLng)

        }
    }

    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker?.showInfoWindow()
        }
    }

    private fun showDialog(latLng: LatLng) {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Save To Favorite")
        builder.setMessage("Do You Want To Save This in Your Favorites?")

        builder.setPositiveButton("Save") { _, _ ->
//            Toast.makeText(
//                requireContext(),
//                "${latLng.latitude}++${latLng.longitude}", Toast.LENGTH_SHORT
//            ).show()

            address = geoCoder.getFromLocation(
                latLng.latitude,
                latLng.longitude,
                1
            ) as MutableList<Address>

            var fav = FavLocation(latLng.latitude,latLng.longitude,address[0].getAddressLine(0))
            favViewModel.insertFavorite(fav)
           // println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ${fav.latitude}")

        }

        builder.setNegativeButton("Cancel") { _, _ ->

        }

        builder.show()
    }

}