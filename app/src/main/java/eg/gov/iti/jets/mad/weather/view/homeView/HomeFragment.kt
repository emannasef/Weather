package eg.gov.iti.jets.mad.weather.view.homeView


import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.mad.weather.database.ConcreteLocalSource
import eg.gov.iti.jets.mad.weather.databinding.FragmentHomeBinding
import eg.gov.iti.jets.mad.weather.model.FavLocation
import eg.gov.iti.jets.mad.weather.model.Repository
import eg.gov.iti.jets.mad.weather.network.WeatherClient
import eg.gov.iti.jets.mad.weather.utlits.ApiState
import eg.gov.iti.jets.mad.weather.utlits.Constants
import eg.gov.iti.jets.mad.weather.utlits.Converter
import eg.gov.iti.jets.mad.weather.utlits.SharedPrefs
import eg.gov.iti.jets.mad.weather.viewModel.home.HomeViewModel
import eg.gov.iti.jets.mad.weather.viewModel.home.HomeViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var hourAdapter: HourAdapter
    private lateinit var dayAdapter: DayAdapter

    lateinit var homeViewModel: HomeViewModel
    lateinit var homeViewModelFactory: HomeViewModelFactory

    lateinit var geoCoder: Geocoder
    lateinit var address: MutableList<Address>
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        SettingsDialogFragment.newInstance()
//            .show(requireActivity().supportFragmentManager, SettingsDialogFragment.TAG)
        sharedPrefs = SharedPrefs(requireContext())
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = SharedPrefs(requireActivity())
        val loc = sharedPrefs.getLocFromPrefFile()
        geoCoder = Geocoder(requireActivity(), Locale.getDefault())

        val simpleDate = SimpleDateFormat("dd-M-yyyy hh:mm")
        val currentDate = simpleDate.format(Date())
        binding.dateTimeTextView.text = currentDate.toString()

        homeViewModelFactory = HomeViewModelFactory(
            Repository.getInstance(
                ConcreteLocalSource(requireContext()),
                WeatherClient.getInstance()
            )
        )
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)

        if (arguments != null) {
            val fav: FavLocation = arguments?.getSerializable("favourite") as FavLocation
            homeViewModel.getWeatherOverNetwork(
                lat = fav.latitude,
                lon = fav.longitude,
                language = sharedPrefs.getLang()
            )

        } else {
            homeViewModel.getWeatherOverNetwork(
                lat = loc.latidute,
                lon = loc.longitude,
                language = sharedPrefs.getLang()
            )
        }

        lifecycleScope.launch {
            homeViewModel.stateFlow.collectLatest {
                when (it) {
                    is ApiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.hoursRecyclerView.visibility = View.GONE
                        binding.daysRecyclerView.visibility = View.GONE
                    }
                    is ApiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.hoursRecyclerView.visibility = View.VISIBLE
                        binding.daysRecyclerView.visibility = View.VISIBLE

                        address = geoCoder.getFromLocation(
                            it.data.lat,
                            it.data.lon,
                            1
                        ) as MutableList<Address>

                        binding.govTextView.text =
                            address[0].getAddressLine(0)
                                .split(",").get(1)

                        hourAdapter = HourAdapter(requireContext(), it.data.hourly)
                        dayAdapter =
                            DayAdapter(requireContext(), it.data.daily, it.data.timezone)

                        binding.hoursRecyclerView.apply {
                            adapter = hourAdapter
                            layoutManager = LinearLayoutManager(
                                requireContext(),
                                RecyclerView.HORIZONTAL,
                                false
                            )
                        }

                        binding.daysRecyclerView.apply {
                            adapter = dayAdapter
                            layoutManager =
                                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                        }
                        binding.gradeTextView.text = changeGrade()
                        binding.currentTempTextView.text = getTemp(it.data.current.temp).toString()
                        binding.currentTempDescTextView.text = it.data.current.weather[0].description
                        binding.currentImageView.setImageResource(Converter.getIcon(it.data.current.weather[0].icon))
                        binding.humidityTextView.text = it.data.current.humidity.toString()
                        binding.pressureTextView.text = it.data.current.pressure.toString()
                        binding.windTextView.text = getWindSpeed(it.data.current.wind_speed).toString()
                        binding.visibilityTextView.text = it.data.current.visibility.toString()
                        binding.ultraVioletTextView.text = it.data.current.uvi.toString()
                        binding.cloudTextView.text = it.data.current.clouds.toString()
                    }
                    else -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, "check your connection", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }


    private fun getTemp(temp: Double): Int {
        var tempUnit = sharedPrefs.getTemp()
        return when (tempUnit) {
            Constants.CELSIUS -> Converter.convertFromKelvinToCelsius(temp)
            Constants.FAHRENHEIT -> Converter.convertFromKelvinToFahrenheit(temp)
            else -> temp.toInt()
        }

    }

    private fun changeGrade(): String {
        return when (sharedPrefs.getTemp()) {
            Constants.CELSIUS -> "C"
            Constants.FAHRENHEIT -> "F"
            else -> "K"
        }
    }

    private fun getWindSpeed(wind: Double): Double {
        val windUnit = sharedPrefs.getWindSpeed()
        return if (windUnit == Constants.MILE_HOUR) {
            Converter.convertMeterspersecToMilesperhour(wind)
        } else {
            wind
        }
    }
}