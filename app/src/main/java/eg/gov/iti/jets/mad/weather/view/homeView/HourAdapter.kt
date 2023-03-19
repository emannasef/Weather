package eg.gov.iti.jets.mad.weather.view.homeView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.mad.weather.databinding.HourRowBinding
import eg.gov.iti.jets.mad.weather.model.Hourly
import eg.gov.iti.jets.mad.weather.utlits.TempConverter

class HourAdapter (private var context: Context,private var hours: List<Hourly>) :
    RecyclerView.Adapter<HourAdapter.ViewHolder>()  {
    lateinit var binding:HourRowBinding

    fun setList(hoursList:List<Hourly>){
        hours=hoursList
        notifyDataSetChanged()
       // println("######################$hours")
    }

class ViewHolder(var binding: HourRowBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = HourRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
     return hours.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentHour = hours[position]
        binding.hourTextView.text=TempConverter.getTime("hh a",currentHour.dt.toLong())
        binding.tempHourTextView.text=TempConverter.convertFromKelvinToCelsius(currentHour.temp).toString()

    }

}