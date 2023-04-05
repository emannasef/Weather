package eg.gov.iti.jets.mad.weather.view.alertView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.mad.weather.databinding.AlartRowBinding
import eg.gov.iti.jets.mad.weather.databinding.FavRowBinding
import eg.gov.iti.jets.mad.weather.model.FavLocation
import eg.gov.iti.jets.mad.weather.model.MyAlert
import eg.gov.iti.jets.mad.weather.view.favView.FavAdapter
import eg.gov.iti.jets.mad.weather.view.favView.OnAdapterClickListener

class AlertAdapter(
    private var context: Context,
    private var alerts: List<MyAlert>,
    private val clickListener: AlertClickListener
) : RecyclerView.Adapter<AlertAdapter.ViewHolder>() {
    lateinit var binding: AlartRowBinding

    class ViewHolder(var binding: AlartRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = AlartRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = alerts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = alerts[position]
       binding.deleteAlertImageView.setOnClickListener {
           clickListener.deleteAlert(current)
       }
        binding.fromTextView.text=current.startDate
        binding.toTextView.text=current.endDate
      //  binding.alertTime.text=current.pickedTime
    }


}
