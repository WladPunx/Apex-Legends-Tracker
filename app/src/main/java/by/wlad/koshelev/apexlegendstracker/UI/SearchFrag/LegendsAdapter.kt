package by.wlad.koshelev.apexlegendstracker.UI.SearchFrag

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.wlad.koshelev.apexlegendstracker.GamerStats.etc.Segment
import by.wlad.koshelev.apexlegendstracker.R
import by.wlad.koshelev.apexlegendstracker.UI.ImageConvertor

class LegendsAdapter(
    var app: AppCompatActivity,
    var arr: MutableList<Segment>
) : RecyclerView.Adapter<LegendsAdapter.MyHolder>() {


    class MyHolder(itemV: View) : RecyclerView.ViewHolder(itemV) {
        val iconLigends: ImageView = itemV.findViewById(R.id.iconLegends_itemLegends)
        val name: TextView = itemV.findViewById(R.id.nameLegends_tx_itemLegends)
        val kills: TextView = itemV.findViewById(R.id.kills_tx_itemLegends)
        val wins: TextView = itemV.findViewById(R.id.wins_tx_itemLegends)
        val skull: ImageView = itemV.findViewById(R.id.iconSkull_itemLegends)
        val icWins: ImageView = itemV.findViewById(R.id.icWinner_itemLegends)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val infalter = LayoutInflater.from(parent.context)
        val itemView = infalter.inflate(R.layout.item_legends, parent, false)
        val holder = MyHolder(itemView)
        return holder
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val a: Segment = arr[position]

        // инока легенды
        ImageConvertor.setImgFromUrl(a.metadata.imageUrl, holder.iconLigends)


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
            holder.kills.setTextColor(ContextCompat.getColor(app, R.color.legends_item_null))
            holder.skull.alpha = 0.2f
        }


        // победы
        try {
            holder.wins.setText("${a.stats.winsWithFullSquad.displayValue}")
        } catch (e: Exception) {
            holder.wins.setText("n/a")
            holder.wins.setTextColor(ContextCompat.getColor(app, R.color.legends_item_null))
            holder.icWins.alpha = 0.2f
        }

    }
}