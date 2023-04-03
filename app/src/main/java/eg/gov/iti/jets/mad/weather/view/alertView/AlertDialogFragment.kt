package eg.gov.iti.jets.mad.weather.view.alertView

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.work.*
import eg.gov.iti.jets.mad.weather.database.ConcreteLocalSource
import eg.gov.iti.jets.mad.weather.databinding.FragmentAlertDialogBinding
import eg.gov.iti.jets.mad.weather.model.MyAlert
import eg.gov.iti.jets.mad.weather.model.Repository
import eg.gov.iti.jets.mad.weather.network.WeatherClient
import eg.gov.iti.jets.mad.weather.viewModel.alert.AlertsViewModel
import eg.gov.iti.jets.mad.weather.viewModel.alert.AlertsViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import eg.gov.iti.jets.mad.weather.R


class AlertDialogFragment : DialogFragment() {

    lateinit var binding: FragmentAlertDialogBinding

    @SuppressLint("WeekBasedYear")
    var formate = SimpleDateFormat("dd MMM, YYYY", Locale.US)
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)

    private val now: Calendar = Calendar.getInstance()
    private val selectedDate: Calendar = Calendar.getInstance()

    private var fromDate: String = ""
    private var toDate: String = ""
    private var timePicked: String = ""
    private var workerTime: Long = 0
    private var workerEndDate: Long = 0

    lateinit var alertsViewModel: AlertsViewModel
    lateinit var alertsViewModelFactory: AlertsViewModelFactory

//    companion object {
//        const val TAG = "AlertDialogFragment"
//        fun newInstance(): AlertDialogFragment {
//            val args = Bundle()
//            val fragment = AlertDialogFragment()
//            fragment.arguments = args
//            return fragment
//        }
//
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAlertDialogBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        alertsViewModelFactory = AlertsViewModelFactory(
            Repository.getInstance(
                ConcreteLocalSource(requireContext()),
                WeatherClient.getInstance()
            )
        )

        alertsViewModel =
            ViewModelProvider(this, alertsViewModelFactory).get(AlertsViewModel::class.java)

        binding.timeButton.setOnClickListener { timePicker() }
        binding.fromButton.setOnClickListener {fromDate()}
        binding.toButton.setOnClickListener { toDate()}
        binding.saveButton.setOnClickListener {

            alertsViewModel.insertAlert(
                MyAlert(
                    startDate = fromDate,
                    endDate = toDate,
                    pickedTime = timePicked
                )
            )
            myPeriodicWork()
            findNavController().navigate(R.id.action_alertDialogFragment_to_alertFragment)
        }
    }


    private fun fromDate() {
        val datePicker = DatePickerDialog(
            requireContext(), { _, year, month, dayOfMonth ->
                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                fromDate = formate.format(selectedDate.time)
                binding.fromButton.text = fromDate
                workerEndDate = selectedDate.timeInMillis
                Toast.makeText(requireContext(), "date : $fromDate", Toast.LENGTH_SHORT).show()
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun toDate() {
        val datePicker = DatePickerDialog(
            requireContext(), { _, year, month, dayOfMonth ->
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                toDate = formate.format(selectedDate.time)
                binding.toButton.text = toDate
                Toast.makeText(requireContext(), "date : $toDate", Toast.LENGTH_SHORT).show()
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }


    private fun timePicker() {
        val timePicker = TimePickerDialog(
            requireContext(), { _, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                timePicked = "${selectedTime.time}"
                println(timePicked)
                binding.timeButton.text = timeFormat.format(selectedTime.time)
                workerTime =
                    (TimeUnit.MINUTES.toSeconds(minute.toLong()) + TimeUnit.HOURS.toSeconds(
                        hourOfDay.toLong()
                    ))
                workerTime = workerTime.minus(3600L * 2)

                println("############################${workerTime}")
            },
            now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false
        )
        timePicker.show()
    }


    private fun myPeriodicWork() {

      //   val date = Calendar.getInstance().timeInMillis.div(1000)
       //  val delay = ((date - workerTime) / 60 / 60 / 60 / 60) - 115

        //  val delay = date - workerTime

        println("############################${workerTime}")
      //  println("###########Delay$delay")

        val data = Data.Builder()
        data.putLong("workerEndDate", workerEndDate)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val myRequest = PeriodicWorkRequest.Builder(
            MyWorker::class.java,
            1,
            TimeUnit.DAYS
        ).setConstraints(constraints).setInitialDelay(workerTime, TimeUnit.MILLISECONDS)
            .addTag("eman").setInputData(data.build())
            .build()

        WorkManager.getInstance(requireContext()).enqueue(myRequest)

        //        WorkManager.getInstance(requireContext())
//            .enqueueUniquePeriodicWork(
//            "eman",
//            ExistingPeriodicWorkPolicy.KEEP,
//            myRequest
//        )


    }


//    private fun createNotificationChannel() {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "channel_name"
//            val descriptionText = "channel_description"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//                description = descriptionText
//            }
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
}