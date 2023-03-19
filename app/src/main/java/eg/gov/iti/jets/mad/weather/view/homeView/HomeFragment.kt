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
import eg.gov.iti.jets.mad.weather.R
import eg.gov.iti.jets.mad.weather.database.ConcreteLocalSource
import eg.gov.iti.jets.mad.weather.databinding.FragmentHomeBinding
import eg.gov.iti.jets.mad.weather.model.Hourly
import eg.gov.iti.jets.mad.weather.model.Repository
import eg.gov.iti.jets.mad.weather.network.WeatherClient
import eg.gov.iti.jets.mad.weather.utlits.ApiState
import eg.gov.iti.jets.mad.weather.viewModel.home.HomeViewModel
import eg.gov.iti.jets.mad.weather.viewModel.home.HomeViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var hourAdapter: HourAdapter

    lateinit var homeViewModel: HomeViewModel
    lateinit var homeViewModelFactory: HomeViewModelFactory

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

        homeViewModelFactory = HomeViewModelFactory(
            Repository.getInstance(
                ConcreteLocalSource(context),
                WeatherClient.getInstance()
            )
        )
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)

//        homeViewModel.hoursList.observe(requireActivity()) {
//            if (it != null) {
//              //  println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^$it")
//                hourAdapter.setList(it)
//            }
//        }

        lifecycleScope.launch {
            homeViewModel.stateFlow.collectLatest { result ->
                when (result) {
                    is ApiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.hoursRecyclerView.visibility = View.GONE
                    }
                    is ApiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.hoursRecyclerView.visibility = View.VISIBLE
                        println("!!!!!!!!!!!!!!!!!${result.data}")
                        hourAdapter.setList(result.data.hourly)
                    }
                    else -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, "check your connection", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

      //  binding.govTextView.text = "Mansoura"
        hourAdapter = HourAdapter(requireContext(), ArrayList<Hourly>())
        var layoutManger = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        binding.hoursRecyclerView.apply {
            adapter = hourAdapter
            layoutManager = layoutManger
        }


    }
}