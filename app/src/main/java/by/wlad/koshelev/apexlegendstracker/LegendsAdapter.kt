package by.wlad.koshelev.apexlegendstracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import by.wlad.koshelev.apexlegendstracker.GamerStats.Segment

class LegendsAdapter(
    var app: AppCompatActivity,
    var arr: MutableList<Segment>
) : RecyclerView.Adapter<LegendsAdapter.MyHolder>() {


    class MyHolder(itemV: View) : RecyclerView.ViewHolder(itemV) {
        val iconLigends: ImageView = itemV.findViewById(R.id.iconLegends_itemLegends)
        val name: TextView = itemV.findViewById(R.id.nameLegends_tx_itemLegends)
        val kills: TextView = itemV.findViewById(R.id.kills_tx_itemLegends)
        var wins: TextView = itemV.findViewById(R.id.wins_tx_itemLegends)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val infalter = LayoutInflater.from(parent.context)
        val itemView = infalter.inflate(R.layout.legends_item, parent, false)
        val holder = MyHolder(itemView)
        return holder
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val a: Segment = arr[position]

        // инока легенды
        try {
            SetImgFromInet.set(a.metadata.imageUrl, holder.iconLigends)
        } catch (e: Exception) {
        }

        // имя легенды
        try {
            holder.name.setText("${a.metadata.name}")
        } catch (e: Exception) {
            holder.name.setText("n/a")
        }

        // убийства
        try {
            holder.kills.setText("${a.stats.kills.displayValue}")
        } catch (e: Exception) {
            holder.kills.setText("n/a")
        }


        // победы
        try {
            holder.wins.setText("${a.stats.winsWithFullSquad.displayValue}")
        } catch (e: Exception) {
            holder.wins.setText("n/a")
        }

    }
}