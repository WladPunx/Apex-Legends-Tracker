package by.wlad.koshelev.apexlegendstracker.UI.ListsFrag

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
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
                AlertDialog.Builder(activity)
                    .setCancelable(false)
                    .setTitle(getString(R.string.suuure))
                    .setMessage(getString(R.string.deleteAllGamers))

                    .setPositiveButton(getString(R.string.yes), DialogInterface.OnClickListener { dialog, which ->
                        MainScope().launch {
                            VM.vm.dellAllGamers()
                        }
                    })

                    .setNegativeButton(getString(R.string.no), DialogInterface.OnClickListener { dialog, which ->
                        dialog.cancel()
                    })
                    .create()
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}