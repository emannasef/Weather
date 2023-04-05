package eg.gov.iti.jets.mad.weather.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import eg.gov.iti.jets.mad.weather.R
import eg.gov.iti.jets.mad.weather.utlits.Constants
import eg.gov.iti.jets.mad.weather.utlits.SharedPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val shared = SharedPrefs(requireContext())
//        val open = shared.getBoolean("open", false)
//        var sharedPrefs: SharedPreferences = requireActivity().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
//        val open = sharedPrefs.getBoolean("open",false)
        lifecycleScope.launch(Dispatchers.Main) {
            delay(3000)
//
//            if (open) {
//                this.cancel()

//            } else {
//                this.cancel()

//            }

          //  findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

}