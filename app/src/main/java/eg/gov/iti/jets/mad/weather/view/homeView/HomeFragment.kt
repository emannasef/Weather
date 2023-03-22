package eg.gov.iti.jets.mad.weather.view.homeView

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
import eg.gov.iti.jets.mad.weather.model.Repository
import eg.gov.iti.jets.mad.weather.network.WeatherClient
import eg.gov.iti.jets.mad.weather.utlits.ApiState
import eg.gov.iti.jets.mad.weather.utlits.Converter
import eg.gov.iti.jets.mad.weather.utlits.SharedPrefs
import eg.gov.iti.jets.mad.weather.viewModel.home.HomeViewModel
import eg.gov.iti.jets.mad.weather.viewModel.home.HomeViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var hourAdapter: HourAdapter
    private lateinit var dayAdapter: DayAdapter

    lateinit var homeViewModel: HomeViewModel
    lateinit var homeViewModelFactory: HomeViewModelFactory

    //lateinit var geoCoder: Geocoder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = SharedPrefs(requireActivity())
        val loc = sharedPrefs.getFromPrefFile()

//        geoCoder= Geocoder(requireActivity(),Locale.getDefault())
//        val addresses: MutableList<Address> = geoCoder.getFromLocation(
//            loc.latidute,
//            loc.longitude,
//            1
//        ) as MutableList<Address>

      //  println("YaaaaRAAAAB ${addresses[0].getAddressLine(0)}")

        homeViewModelFactory = HomeViewModelFactory(
            Repository.getInstance(
                ConcreteLocalSource(context),
                WeatherClient.getInstance()
            )
        )
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)

        homeViewModel.getWeatherOverNetwork(
            lat = loc.latidute,
            lon = loc.longitude,
            language = "ar"
        )


        lifecycleScope.launch {
            homeViewModel.stateFlow.collectLatest { result ->
                when (result) {
                    is ApiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.hoursRecyclerView.visibility = View.GONE
                        binding.daysRecyclerView.visibility = View.GONE
                    }
                    is ApiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.hoursRecyclerView.visibility = View.VISIBLE
                        binding.daysRecyclerView.visibility = View.VISIBLE

                        hourAdapter = HourAdapter(requireContext(), result.data.hourly)
                        dayAdapter =
                            DayAdapter(requireContext(), result.data.daily, result.data.timezone)


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


                        println("________________________${result.data.current}")
//                        binding.govTextView.text = address.get(0).locality.toString()
//                        binding.adressTextView.text = "rtfgyuhj"


                        binding.currentTempTextView.text = Converter.convertFromKelvinToCelsius(result.data.current.temp).toString()
                        binding.currentTempDescTextView.text = result.data.current.weather[0].description
                        binding.currentImageView.setImageResource(Converter.getIcon(result.data.current.weather[0].icon))
                        binding.humidityTextView.text = result.data.current.humidity.toString()
                        binding.pressureTextView.text =result.data.current.pressure.toString()
                        binding.windTextView.text =result.data.current.pressure.toString()
                        binding.visibilityTextView.text = result.data.current.visibility.toString()
                        binding.ultraVioletTextView.text = result.data.current.uvi.toString()
                        binding.cloudTextView.text = result.data.current.clouds.toString()
                    }
                    else -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, "check your connection", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


    }
}