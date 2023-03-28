package eg.gov.iti.jets.mad.weather.view.settingsView

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import eg.gov.iti.jets.mad.weather.databinding.FragmentSettingsDialogBinding

class InitialSettingsDialogFragment :DialogFragment() {

    lateinit var binding:FragmentSettingsDialogBinding
    lateinit var sharedPreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    companion object {

        const val TAG = "AlertDialogFragment"

        fun newInstance(): InitialSettingsDialogFragment {
            val args = Bundle()
            val fragment = InitialSettingsDialogFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedPreference =
            requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        editor = sharedPreference.edit()

        binding = FragmentSettingsDialogBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.okButton.setOnClickListener {
//         //   findNavController().navigate(R.id.action_settingsDialogFragment_to_homeFragment)
//        }
        binding.dialogLocationGroup.setOnCheckedChangeListener{
            locationGroup, checkedId ->
        }

    }

}