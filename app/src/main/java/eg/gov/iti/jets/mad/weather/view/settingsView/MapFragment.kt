package eg.gov.iti.jets.mad.weather.view.settingsView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import eg.gov.iti.jets.mad.weather.R
import eg.gov.iti.jets.mad.weather.model.UserLocation
import eg.gov.iti.jets.mad.weather.utlits.SharedPrefs


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var myGoogleMap: GoogleMap
    lateinit var shared: SharedPrefs
    lateinit var loc:UserLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shared= SharedPrefs(requireContext())


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        loc= shared.getFromPrefFile()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onMapReady(p0: GoogleMap) {
        myGoogleMap = p0
        val latLang :LatLng= LatLng(loc.latidute,loc.longitude)
        myGoogleMap.addMarker(
            MarkerOptions()
            .title("place.name")
            .position(latLang))
        myGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLang))
    }

}