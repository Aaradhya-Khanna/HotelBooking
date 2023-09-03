import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelbooking.R
import com.example.hotelbooking.hotels

class HotelAdapter(private val dataSet: List<hotels>) :
    RecyclerView.Adapter<HotelAdapter.ViewHolder>() {

    // Define an interface to handle item clicks
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // Add a variable to hold the click listener
    private var itemClickListener: OnItemClickListener? = null

    // Function to set the click listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotel = dataSet[position]
        holder.bind(hotel)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val hotelNameTextView: TextView = view.findViewById(R.id.name)

        fun bind(hotel: hotels) {
            hotelNameTextView.text = hotel.name

            // Set an OnClickListener for item views
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)
            }
        }
    }
}
