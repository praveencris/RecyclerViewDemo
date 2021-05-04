package com.example.recyclerviewdemoclick.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.example.recyclerviewdemoclick.MainActivity
import com.example.recyclerviewdemoclick.R
import com.example.recyclerviewdemoclick.adapter.NumberAdapter
import com.example.recyclerviewdemoclick.databinding.FragmentMainBinding
import com.example.recyclerviewdemoclick.models.Item
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val EXTRA_ITEM = "extra_item"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment(), NumberAdapter.OnClickListener {
    private lateinit var binding: FragmentMainBinding
    private lateinit var numberAdapter: NumberAdapter

    // TODO: Rename and change types of parameters
    private var item: Item? = null
    private var list = (1..21).toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getParcelable<Item>(EXTRA_ITEM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        numberAdapter = NumberAdapter(R.layout.list_item_alphabate, this)
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = numberAdapter


        numberAdapter.numbersList = list


          val itemTouchHelperSimpleCallback: ItemTouchHelper.SimpleCallback = object :
              ItemTouchHelper.SimpleCallback(
                  0,
                  ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                          or ItemTouchHelper.UP or ItemTouchHelper.DOWN
              ) {

              override fun onMove(
                  recyclerView: RecyclerView,
                  viewHolder: RecyclerView.ViewHolder,
                  target: RecyclerView.ViewHolder
              ): Boolean {
                  TODO("Not yet implemented")
              }

              override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                  val currentList = mutableListOf<Int>()
                  currentList.addAll(numberAdapter.numbersList)
                  numberAdapter.numbersList.removeAt(viewHolder.adapterPosition)
                  numberAdapter.notifyDataSetChanged()

                  Snackbar.make(binding.root, "Deleted by mistake!", Snackbar.LENGTH_INDEFINITE)
                      .setAction(
                          "Undo"
                      ) {
                          numberAdapter.numbersList = currentList
                          numberAdapter.notifyDataSetChanged()
                      }.show()

              }

          }
          val touchHelper = ItemTouchHelper(itemTouchHelperSimpleCallback)
          touchHelper.attachToRecyclerView(binding.recyclerView)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.inflateMenu(R.menu.menu)
        binding.toolbar.setOnMenuItemClickListener { item ->
            val list = numberAdapter.numbersList;
            binding.recyclerView.layoutManager = when (item?.itemId) {
                R.id.linear_view -> {
                    numberAdapter = NumberAdapter(R.layout.list_item_alphabate, this)
                    LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                }
                R.id.grid_view -> {
                    numberAdapter = NumberAdapter(R.layout.list_item_number_grid, this)
                    GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
                }
                R.id.staggered_view -> {
                    numberAdapter = NumberAdapter(R.layout.list_item_number_grid, this)
                    StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
                }
                else -> LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            }
            binding.recyclerView.adapter = numberAdapter
            numberAdapter.numbersList = list
            true
        }

    }


    override fun onAddClick(position: Int) {
        val list = numberAdapter.numbersList;
        list[position] = list[position] + 1
        numberAdapter.numbersList = list
        binding.recyclerView.scrollToPosition(position)
    }

    override fun onResetClick(position: Int) {
        val list = numberAdapter.numbersList;
        list[position] = 0
        numberAdapter.numbersList = list
        binding.recyclerView.scrollToPosition(position)
    }

    override fun onTextClick(position: Int) {
        val list = numberAdapter.numbersList;
        Snackbar.make(binding.root, "Item Clicked ${list[position]}", Snackbar.LENGTH_SHORT).show()
        val item: Item = Item(position, list[position])

        val detailFragment = DetailFragment.newInstance(item)
        val tag = DetailFragment::class.java.simpleName

        detailFragment.setTargetFragment(this, ITEM_REQUEST_CODE)
        (activity as? MainActivity)?.replace(detailFragment, tag)
    }

    override fun onResume() {
        super.onResume()

        item?.let { item ->
            val newList = numberAdapter.numbersList;
            newList[item.position] = item.dataItem
            numberAdapter.numbersList = newList
            binding.recyclerView.scrollToPosition(item.position)
        }

    }

    companion object {
        const val ITEM_REQUEST_CODE = 1

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(item: Item) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_ITEM, item)
                }
            }

        @JvmStatic
        fun newInstance() = MainFragment()
    }


    fun setItem(item: Item) {
        this.item = item
    }

}



