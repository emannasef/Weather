package eg.gov.iti.jets.mad.weather.view.homeView

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.mad.weather.databinding.DayRowBinding
import eg.gov.iti.jets.mad.weather.databinding.HourRowBinding
import eg.gov.iti.jets.mad.weather.model.MyResponse
import eg.gov.iti.jets.mad.weather.utlits.Constants

import eg.gov.iti.jets.mad.weather.utlits.Converter
import eg.gov.iti.jets.mad.weather.utlits.SharedPrefs

class DayAdapter(private var context: Context, private var days: List<MyResponse.Daily>, var timeZone: String) :
    RecyclerView.Adapter<DayAdapter.ViewHolder>() {
    lateinit var binding: DayRowBinding

//    fun setList(daysList: List<MyResponse.Daily>) {
//        days = daysList
//        notifyDataSetChanged()
//        //println("######################$days")
//    }

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
        binding.dayTempTextView.text=getTemp(currentDay.temp.day).toString()
        binding.gradeTextView3.text=changeGrade()
    }

    fun getTemp(temp: Double): Int {
        val sharedPrefs = SharedPrefs(context)
        var tempUnit = sharedPrefs.getTemp()
        if (tempUnit == Constants.CELSIUS) {
            return Converter.convertFromKelvinToCelsius(temp)
        } else if (tempUnit == Constants.FAHRENHEIT) {
            return Converter.convertFromKelvinToFahrenheit(temp)
        } else {
            return temp.toInt()
        }
    }

    fun changeGrade():String{
        val sharedPrefs = SharedPrefs(context)
        var tempUnit = sharedPrefs.getTemp()

        if (tempUnit == Constants.CELSIUS) {
            return "C"
        } else if (tempUnit == Constants.FAHRENHEIT) {
            return "F"
        } else {
            return "K"
        }
    }

}