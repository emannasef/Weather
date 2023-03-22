package eg.gov.iti.jets.mad.weather.view.homeView

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.mad.weather.databinding.DayRowBinding
import eg.gov.iti.jets.mad.weather.databinding.HourRowBinding
import eg.gov.iti.jets.mad.weather.model.Daily
import eg.gov.iti.jets.mad.weather.model.Hourly
import eg.gov.iti.jets.mad.weather.utlits.Converter

class DayAdapter(private var context: Context, private var days: List<Daily>, var timeZone: String) :
    RecyclerView.Adapter<DayAdapter.ViewHolder>() {
    lateinit var binding: DayRowBinding

    fun setList(daysList: List<Daily>) {
        days = daysList
        notifyDataSetChanged()
        //println("######################$days")
    }

    class ViewHolder(var binding: DayRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DayRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return days.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     val currentDay = days[position]
        binding.dayTextView.text=Converter.getDay(currentDay.dt,timeZone)
        binding.dayDesTextView.text=currentDay.weather[0].description
        binding.dayImageView.setImageResource(Converter.getIcon(currentDay.weather[0].icon))
        binding.dayTempTextView.text=Converter.convertFromKelvinToCelsius(currentDay.temp.day).toString()
    }

}