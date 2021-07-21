package by.wlad.koshelev.apexlegendstracker.UI.ListsFrag

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import by.wlad.koshelev.apexlegendstracker.Arch.VM
import by.wlad.koshelev.apexlegendstracker.GamerStats._GamerStats
import by.wlad.koshelev.apexlegendstracker.R
import by.wlad.koshelev.apexlegendstracker.UI.CoolerDate
import by.wlad.koshelev.apexlegendstracker.UI.SetImgFromInet
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class GamersAdapter(
    var app: AppCompatActivity,
    var arr: MutableList<_GamerStats>
) : RecyclerView.Adapter<GamersAdapter.MyHolderGamers>() {

    var mainScope = MainScope()


    class MyHolderGamers(itemV: View) : RecyclerView.ViewHolder(itemV) {
        val name: TextView = itemV.findViewById(R.id.name_txt_ItemGamers)
        val avatar: ImageView = itemV.findViewById(R.id.iconAvatar_img_ItemGamers)
        val rank: ImageView = itemV.findViewById(R.id.iconRank_img_ItemGamers)
        val date: TextView = itemV.findViewById(R.id.date_txt_ItemGamers)
        val delete: ImageView = itemV.findViewById(R.id.delete_img_ItemGamers)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderGamers {
        val infalter = LayoutInflater.from(parent.context)
        val itemView = infalter.inflate(R.layout.item_gamers, parent, false)
        val holder = MyHolderGamers(itemView)

        /**
         * удаление
         */
        holder.delete.setOnClickListener {

            AlertDialog.Builder(app)
                .setCancelable(false)
                .setTitle(app.getString(R.string.suuure))
                .setMessage("${app.getString(R.string.deleteOneGamer)} ${arr[holder.adapterPosition].data.platformInfo.platformUserId} ?")

                .setPositiveButton(app.getString(R.string.yes), DialogInterface.OnClickListener { dialog, which ->
                    MainScope().launch {
                        VM.vm.deleteOneGamer(arr[holder.adapterPosition])
                    }
                })

                .setNegativeButton(app.getString(R.string.no), DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
                .create()
                .show()


        }

        /**
         * нажатие на элемент
         */
        holder.itemView.setOnClickListener {
            val nickName: String = arr[holder.adapterPosition].data.platformInfo.platformUserId
            val platform: String = arr[holder.adapterPosition].data.platformInfo.platformSlug
            mainScope.cancel()
            mainScope = MainScope()
            mainScope.launch {
                VM.vm.getGms(platform, nickName)
            }
        }

        return holder
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: MyHolderGamers, position: Int) {
        val a: _GamerStats = arr[position]


        // имя и цвет
        holder.name.setText("${a.data.platformInfo.platformUserId}")
        when (a.data.platformInfo.platformSlug) {
            "origin" -> holder.name.setTextColor(ContextCompat.getColor(app, R.color.origin))
            "psn" -> holder.name.setTextColor(ContextCompat.getColor(app, R.color.playstaion))
            "xbox" -> holder.name.setTextColor(ContextCompat.getColor(app, R.color.xbox))

        }

        // аватар
        SetImgFromInet.set(a.data.platformInfo.avatarUrl, holder.avatar)

        // ранг
        try {
            SetImgFromInet.set(a.data.segments[0].stats.rankScore.metadata.iconUrl, holder.rank)
        } catch (e: Exception) {
        }


        // дата последнего входа
        holder.date.setText("${CoolerDate.getCoolerDate(a, app)}")
    }


}