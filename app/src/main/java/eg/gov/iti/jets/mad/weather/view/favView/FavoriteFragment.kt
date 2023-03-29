package eg.gov.iti.jets.mad.weather.view.favView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.mad.weather.R
import eg.gov.iti.jets.mad.weather.database.ConcreteLocalSource
import eg.gov.iti.jets.mad.weather.databinding.FragmentFavoriteBinding
import eg.gov.iti.jets.mad.weather.model.FavLocation
import eg.gov.iti.jets.mad.weather.model.Repository
import eg.gov.iti.jets.mad.weather.network.WeatherClient
import eg.gov.iti.jets.mad.weather.utlits.RoomState
import eg.gov.iti.jets.mad.weather.utlits.SharedPrefs
import eg.gov.iti.jets.mad.weather.viewModel.fav.FavViewModel
import eg.gov.iti.jets.mad.weather.viewModel.fav.FavViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavoriteFragment : Fragment() ,OnAdapterClickListener{

    lateinit var binding: FragmentFavoriteBinding
    lateinit var favAdapter: FavAdapter
    lateinit var favViewModel: FavViewModel
    lateinit var favViewModelFactory: FavViewModelFactory
    lateinit var shared: SharedPrefs


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shared = SharedPrefs(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favViewModelFactory = FavViewModelFactory(
            Repository.getInstance(
                ConcreteLocalSource(requireContext()), WeatherClient.getInstance()
            )
        )
        favViewModel = ViewModelProvider(this, favViewModelFactory).get(FavViewModel::class.java)

        favViewModel.getFavLocations()

        lifecycleScope.launch {
            favViewModel.favStateFlow.collectLatest { result ->
                when (result) {
                    is RoomState.Loading -> {

                        binding.favRecyclerView.visibility = View.GONE
                    }
                    is RoomState.Success -> {

                        binding.favRecyclerView.visibility = View.VISIBLE

                        favAdapter = FavAdapter(requireContext(), result.data,this@FavoriteFragment)
                        binding.favRecyclerView.apply {
                            adapter = favAdapter
                            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

                        }
                    }
                    else -> {
                        Toast.makeText(
                            requireContext(),
                            "check your connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_favoriteFragment_to_favMapFragment)

        }
    }

    override fun deleteFav(favLocation: FavLocation) {
        favViewModel.deleteFromFav(favLocation)
    }

    override fun viewData(favLocation: FavLocation) {

        val bundle:Bundle= Bundle()

        bundle.putSerializable("favourite",favLocation)
        findNavController().navigate(R.id.homeFragment,bundle)

      //  shared.saveLocInPrefFile(favLocation.latitude.toFloat(),favLocation.longitude.toFloat())
        //findNavController().navigate(R.id.homeFragment)
    }

}