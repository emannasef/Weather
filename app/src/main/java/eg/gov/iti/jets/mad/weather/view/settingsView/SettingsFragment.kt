package eg.gov.iti.jets.mad.weather.view.settingsView

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.zeugmasolutions.localehelper.Locales
import eg.gov.iti.jets.mad.weather.R
import eg.gov.iti.jets.mad.weather.databinding.FragmentSettingsBinding
import eg.gov.iti.jets.mad.weather.utlits.Constants
import eg.gov.iti.jets.mad.weather.utlits.LanguageManager
import eg.gov.iti.jets.mad.weather.utlits.SharedPrefs
import java.util.*


class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding
    lateinit var shared: SharedPrefs
    lateinit var languageManager: LanguageManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shared = SharedPrefs(requireContext())
        languageManager= LanguageManager(requireContext())

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


        binding.tempGroup.setOnCheckedChangeListener { _, id ->
            val radioBtn: RadioButton = view.findViewById(id)
            val clickedTemp = radioBtn.text.toString()
            shared.setTemp(clickedTemp)
        }


        binding.locationGroup.setOnCheckedChangeListener { _, id ->
            if (binding.mapRadioButton.isChecked) {
                findNavController().navigate(R.id.action_settingsFragment_to_mapFragment)
                 //shared.setLocation("map")
            } else {
                shared.setLocation("gps")
            }
        }


        binding.langGroup.setOnCheckedChangeListener { _, _ ->

            if (binding.arabicRadioButton.isChecked) {
             shared.setLang("ar").toString()
                //setLan("ar")
                languageManager.updateResources("ar")
            } else {
            shared.setLang("en").toString()
                //setLan("en")
                languageManager.updateResources("en")
            }
        }

        binding.windSpeedGroup.setOnCheckedChangeListener{_,id,->
            val radioBtn: RadioButton = view.findViewById(id)
            val clickedWind = radioBtn.text.toString()
            shared.setWindSpeed(clickedWind)
        }

        binding.alartGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton:RadioButton=view.findViewById(checkedId)
            val clicked = radioButton.text.toString()
            shared.setAlert(clicked)
        }



        if (shared.getTemp() == Constants.FAHRENHEIT) {
            binding.fahrenhitRadioButton.isChecked = true
        }
        else if (shared.getTemp() == Constants.CELSIUS) {
            binding.celisiusRadioButton.isChecked = true
        }
        else{
            binding.kelvinradioButton.isChecked = true
        }


        if (shared.getLang() == Constants.AR) {
            binding.arabicRadioButton.isChecked = true
        } else {
            binding.englishRadioButton.isChecked = true
        }

        if (shared.getLocation() == Constants.MAP) {
            binding.mapRadioButton.isChecked = true
        } else {
            binding.gpsRadioButton.isChecked = true
        }


        if (shared.getWindSpeed()==Constants.MILE_HOUR){
            binding.mileHourRadioButton.isChecked=true
        }else{
            binding.metterSecRadioButton.isChecked=true
        }

        if (shared.getAlert()==Constants.ALARM){
            binding.alarmRadioButton.isChecked=true
        }else{
            binding.notificationRadioButton.isChecked=true
        }


    }



//    private fun setLan(language: String) {
//        val metric = resources.displayMetrics
//        val configuration = resources.configuration
//        configuration.locale = Locale(language)
//        Locale.setDefault(Locale(language))
//
//        configuration.setLayoutDirection(Locale(language))
//        // update configuration
//        resources.updateConfiguration(configuration, metric)
//        // notify configuration
//        onConfigurationChanged(configuration)
//
//    }


}