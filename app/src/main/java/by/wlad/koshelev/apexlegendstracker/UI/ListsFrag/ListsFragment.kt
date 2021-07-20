package by.wlad.koshelev.apexlegendstracker.UI.ListsFrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import by.wlad.koshelev.apexlegendstracker.Arch.VM
import by.wlad.koshelev.apexlegendstracker.R
import kotlinx.android.synthetic.main.fragment_lists.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class ListsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // настройки Ресайкла
        gamersLists_Recycler_ListsFrag.layoutManager = GridLayoutManager(activity, 1)


        /**
         * кнопка УДАЛИТЬ ВСЕ
         */
        clearBD_btn_ListsFrag.setOnClickListener {
            MainScope().launch {
                VM.vm.dellAllGamers()
            }
        }

        /**
         * слушатель на список всех игроков
         */
        VM.vm.gmsLists.observe(viewLifecycleOwner, Observer {
            gamersLists_Recycler_ListsFrag.adapter = GamersAdapter(activity as AppCompatActivity, it)
        })

    }


}