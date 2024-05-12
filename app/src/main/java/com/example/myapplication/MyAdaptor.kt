import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import retrofit2.Callback

class MyAdaptor(
    private val context: Context,
    private val userList: List<MyDataItem>,
    private val onItemClick: (MyDataItem) -> Unit
) : RecyclerView.Adapter<MyAdaptor.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userId: TextView = itemView.findViewById(R.id.userId)
        var image: ImageView=itemView.findViewById(R.id.image_movie)

        init {
            userId = itemView.findViewById(R.id.userId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.row_data, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = userList[position]
        val boroughName = when (item.borough) {  // item.borough is assumed to be one of Q, R, X, M, B
            "Q" -> "Queens"
            "R" -> "Bronx"
            "X" -> "Staten Island"
            "M" -> "Manhattan"
            "B" -> "Brooklyn"
            else -> "Unknown"
        }

        holder.userId.text = boroughName  // Set the full borough name

        when (item.borough) {  // Use abbreviations for switch cases
            "Q" -> holder.image.setImageResource(R.drawable.queens)
            "R" -> holder.image.setImageResource(R.drawable.bronx)
            "X" -> holder.image.setImageResource(R.drawable.staten)
            "M" -> holder.image.setImageResource(R.drawable.manhatten)
            "B" -> holder.image.setImageResource(R.drawable.brooklin)
        }

        // Set the click listener for the itemView
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }
    override fun getItemCount() = userList.size
}
