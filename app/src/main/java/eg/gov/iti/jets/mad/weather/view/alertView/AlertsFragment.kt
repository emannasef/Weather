package eg.gov.iti.jets.mad.weather.view.alertView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.mad.weather.R
import eg.gov.iti.jets.mad.weather.database.ConcreteLocalSource
import eg.gov.iti.jets.mad.weather.databinding.FragmentAlartBinding
import eg.gov.iti.jets.mad.weather.databinding.FragmentAlertDialogBinding
import eg.gov.iti.jets.mad.weather.model.FavLocation
import eg.gov.iti.jets.mad.weather.model.MyAlert
import eg.gov.iti.jets.mad.weather.model.Repository
import eg.gov.iti.jets.mad.weather.network.WeatherClient
import eg.gov.iti.jets.mad.weather.utlits.RoomState
import eg.gov.iti.jets.mad.weather.view.favView.FavAdapter
import eg.gov.iti.jets.mad.weather.viewModel.alert.AlertsViewModel
import eg.gov.iti.jets.mad.weather.viewModel.alert.AlertsViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class AlertsFragment : Fragment() ,AlertClickListener{

    lateinit var binding: FragmentAlartBinding

    lateinit var alertsViewModel: AlertsViewModel
    lateinit var alertsViewModelFactory: AlertsViewModelFactory
    private lateinit var alertAdapter:AlertAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAlartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addAlertFAB.setOnClickListener {
//            AlertDialogFragment.newInstance()
//                .show(requireActivity().supportFragmentManager, AlertDialogFragment.TAG)
            findNavController().navigate(R.id.action_alertFragment_to_alertDialogFragment)
        }

        alertsViewModelFactory = AlertsViewModelFactory(
            Repository.getInstance(
                ConcreteLocalSource(requireContext()),
                WeatherClient.getInstance()
            )
        )

        alertsViewModel =
            ViewModelProvider(this, alertsViewModelFactory).get(AlertsViewModel::class.java)


        lifecycleScope.launch {
            alertsViewModel.alertStateFlow.collectLatest { result ->
                when (result) {
                    is RoomState.Loading -> {

                        binding.alertRecyclerView.visibility = View.GONE
                    }
                    is RoomState.SuccessAlert-> {

                        binding.alertRecyclerView.visibility = View.VISIBLE

                        alertAdapter =
                            AlertAdapter(requireContext(), result.alertData, this@AlertsFragment)
                        binding.alertRecyclerView.apply {
                            adapter = alertAdapter
                            layoutManager =
                                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

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
    }

    override fun deleteAlert(myAlert: MyAlert) {
        showDialog(myAlert)
    }

    private fun showDialog(alert: MyAlert) {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete From Alerts")
        builder.setMessage("Do You Want To Delete This Alert ?")

        builder.setPositiveButton("Delete") { _, _ ->
            alertsViewModel.deleteAlert(alert)
        }

        builder.setNegativeButton("Cancel") { _, _ ->

        }

        builder.show()
    }

}