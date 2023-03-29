package eg.gov.iti.jets.mad.weather.view.favView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eg.gov.iti.jets.mad.weather.databinding.FavRowBinding
import eg.gov.iti.jets.mad.weather.databinding.HourRowBinding
import eg.gov.iti.jets.mad.weather.model.FavLocation
import eg.gov.iti.jets.mad.weather.model.MyResponse
import eg.gov.iti.jets.mad.weather.view.homeView.HourAdapter

class FavAdapter(private var context: Context, private var favs: List<FavLocation>,val clickListener:OnAdapterClickListener) :
    RecyclerView.Adapter<FavAdapter.ViewHolder>() {
    lateinit var binding:FavRowBinding

    class ViewHolder(var binding: FavRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = FavRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount():Int{
        return favs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current =favs[position]
        binding.favPlaceTextView.text=current.address
        binding.deleteImageView.setOnClickListener {
            clickListener.deleteFav(current)
        }

        binding.favRow.setOnClickListener {
            clickListener.viewData(favLocation = current)
        }
    }

}