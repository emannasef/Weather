package eg.gov.iti.jets.mad.weather.view.settingsView

import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import eg.gov.iti.jets.mad.weather.R
import eg.gov.iti.jets.mad.weather.model.UserLocation
import eg.gov.iti.jets.mad.weather.utlits.Constants
import eg.gov.iti.jets.mad.weather.utlits.SharedPrefs
import java.util.*


class MapSettingFragment : Fragment(), OnMapReadyCallback {

    private lateinit var myGoogleMap: GoogleMap
    lateinit var shared: SharedPrefs
    lateinit var loc: UserLocation
    private lateinit var geoCoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shared = SharedPrefs(requireContext())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        geoCoder  = Geocoder(requireActivity())
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment

        mapFragment.getMapAsync(this)

        loc =shared.getLocFromPrefFile()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_map, container, false)
    }

    override fun onMapReady(p0: GoogleMap) {
        myGoogleMap = p0

        val latLang: LatLng = LatLng(loc.latidute, loc.longitude)
        myGoogleMap.addMarker(
            MarkerOptions()
                .title("Iam here")
                .position(latLang)
        )
        myGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLang, 10f))
        setMapLongClick(myGoogleMap)
        setPoiClick(myGoogleMap)
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

          //  showDialog(latLng)
            val address =geoCoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            address?.let {
                if (it.isNotEmpty()){
                    var data = it[0]
                    val userLocation = UserLocation(
                        data.latitude,
                        data.longitude
                    )
                    showDialog(userLocation);
                }

            }

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

    private fun showDialog(userLocation: UserLocation) {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Current Location")
        builder.setMessage("Do you want to save your location?")


        builder.setPositiveButton("Save") { _, _ ->
            Toast.makeText(
                requireContext(),
                "${userLocation.latidute}${userLocation.longitude}", Toast.LENGTH_SHORT
            ).show()
            shared.saveLocInPrefFile(userLocation.latidute.toFloat(),userLocation.longitude.toFloat())
            println("LOCCCCCCCCCCCCCCCCCCCCCCCC${userLocation.latidute},${userLocation.longitude}")
            findNavController().navigate(R.id.action_mapFragment_to_homeFragment)

        }

        builder.setNegativeButton("Cancel") { _, _ ->

        }

        builder.show()
    }


}