package eg.gov.iti.jets.mad.weather.view.homeView

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.mad.weather.databinding.HourRowBinding
import eg.gov.iti.jets.mad.weather.model.MyResponse
import eg.gov.iti.jets.mad.weather.utlits.*

class HourAdapter(private var context: Context, private var hours: List<MyResponse.Hourly>,private var sharedPrefs: SharedPrefs) :
    RecyclerView.Adapter<HourAdapter.ViewHolder>() {
    lateinit var binding: HourRowBinding

//    fun setList(hoursList:List<Hourly>){
//        hours=hoursList
//        notifyDataSetChanged()
//       // println("######################$hours")
//    }

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


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentHour = hours.get(position)

      //  println(Converter.getTime("hh a", hours[position].dt.toLong()).toString())
        if (currentHour != null) {
            binding.myHourTextView.text = Converter.getTime("hh:mm a", currentHour.dt.toLong())
            binding.tempHourTextView.text = getTemp(currentHour.temp,sharedPrefs).toString()
            binding.hourImageView.setImageResource(Converter.getIcon(currentHour.weather[0].icon))
            binding.gradeTextView4.text =changeGrade(sharedPrefs)
        }


    }


}