package by.wlad.koshelev.apexlegendstracker.UI.ListsFrag

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import by.wlad.koshelev.apexlegendstracker.Arch.VM
import by.wlad.koshelev.apexlegendstracker.R
import kotlinx.android.synthetic.main.fragment_lists.*


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

        (activity as AppCompatActivity).setSupportActionBar(toolbar_ListsFrag)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        setHasOptionsMenu(true)


        // настройки Ресайкла
        gamersLists_Recycler_ListsFrag.layoutManager = GridLayoutManager(activity, 1)


        /**
         * слушатель на список всех игроков
         */
        VM.vm.gmsLists.observe(viewLifecycleOwner, Observer {
            gamersLists_Recycler_ListsFrag.adapter = GamersAdapter(activity as AppCompatActivity, it)
        })

        toolBar_img_ListsFrag.setOnClickListener {
            getView()?.findViewById<View>(R.id.setting)?.performClick()
        }

    }

    /**
     *
     *
     *
     *
     *
     *
     *
     *
     * ТУЛБАР
     */

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.lists_frag_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            /**
             * очистка всей БД
             */
            R.id.clearBD -> {
                VM.vm.dellAllGamers(activity as AppCompatActivity)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}