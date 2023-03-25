package eg.gov.iti.jets.mad.weather

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.provider.Settings
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import eg.gov.iti.jets.mad.weather.databinding.ActivityMainBinding
import eg.gov.iti.jets.mad.weather.utlits.SharedPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


const val PERMISSION_ID = 44

class MainActivity : AppCompatActivity() {
    lateinit var drawer: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var binding: ActivityMainBinding

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var shared: SharedPrefs

    lateinit var geoCoder: Geocoder
    //lateinit var address: MutableList<Address>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shared = SharedPrefs(this@MainActivity)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawer = binding.drawerLayout
        navigationView = binding.navigatorLayout

        val actionBar = supportActionBar

        actionBar?.setHomeAsUpIndicator(R.drawable.baseline_menu_24)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        var navController = findNavController(this, R.id.nav_host_fragment)
        setupWithNavController(navigationView, navController)



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        geoCoder = Geocoder(this, Locale.getDefault())
        getLastLocation()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START)
            } else {
                drawer.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            getLastLocation()
        }
    }

    private fun getLastLocation(): Unit {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                requestNewLocationData()
            } else {
                Toast.makeText(this, "turn on location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        val result = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return result
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManger: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManger.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManger.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest.setInterval(0)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
            val mLastLocation: Location? = p0?.lastLocation

            if (mLastLocation != null) {
                shared.saveInPrefFile(
                    mLastLocation.latitude.toFloat(),
                    mLastLocation.longitude.toFloat()
                )

                println("^^^^^^^^^^^^^mLocationLat=${mLastLocation.latitude}")
                println("^^^^^^^^^^^mLocationLON=${mLastLocation.longitude}")

//                lifecycleScope.launch (Dispatchers.IO){
//                    val address: MutableList<Address> = geoCoder.getFromLocation(
//                        mLastLocation.latitude,
//                        mLastLocation.longitude,
//                        1
//                    ) as MutableList<Address>
//                    println("%%%%%%%%%%%%%%%%%%%%%%%%%%${address[0].locality}")
//                }

            }


        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showDialog()
            } else {
                if (!isLocationEnabled()) {
                    Toast.makeText(this, "plz turn on location", Toast.LENGTH_SHORT).show()
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
            }
        }
    }


    private fun showDialog() {
        Toast.makeText(this, "$$$$$$$$$$$$$$$$$$$$$$$", Toast.LENGTH_SHORT).show()
        println("#################*****************************")
        val dialogBuild: AlertDialog.Builder = AlertDialog.Builder(this)
        dialogBuild.setIcon(R.drawable.baseline_cloud_24)
        dialogBuild.setTitle("Weather App")
        dialogBuild.setMessage("Please Allow Weather App Access your location")
        dialogBuild.setCancelable(false)

        val dialog = dialogBuild.create()
        dialog.show()
    }
}