package eg.gov.iti.jets.mad.weather.view.settingsView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.GoogleMap
import eg.gov.iti.jets.mad.weather.R
import eg.gov.iti.jets.mad.weather.databinding.FragmentSettingsBinding
import eg.gov.iti.jets.mad.weather.utlits.SharedPrefs


class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding
    lateinit var shared: SharedPrefs
    private lateinit var myGoogleMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shared = SharedPrefs(requireContext())

        binding.mapRadioButton.setOnClickListener {

            findNavController().navigate(R.id.action_settingsFragment_to_mapFragment)

        }
//        binding.locationGroup.setOnCheckedChangeListener{_,_ ->
//            if (binding.mapRadioButton.isChecked){
//                findNavController().navigate(R.id.action_settingsFragment_to_mapFragment)
//
//            }
//
//        }

        binding.langGroup.setOnCheckedChangeListener { _, _ ->
            if (binding.arabicRadioButton.isChecked) {
                shared.setLang("ar")
            } else {
                shared.setLang("en")
            }

        }

    }

}