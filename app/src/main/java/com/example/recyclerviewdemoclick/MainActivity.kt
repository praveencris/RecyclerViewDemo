package com.example.recyclerviewdemoclick

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.*
import com.example.recyclerviewdemoclick.adapter.NumberAdapter
import com.example.recyclerviewdemoclick.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), NumberAdapter.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var numberAdapter: NumberAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        numberAdapter = NumberAdapter(R.layout.list_item_alphabate, this)
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = numberAdapter
        val list = (1..21).toMutableList()

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
                    .setAction("Undo", View.OnClickListener {
                        numberAdapter.numbersList = currentList
                        numberAdapter.notifyDataSetChanged()
                    }).show()

            }

        }
        val touchHelper = ItemTouchHelper(itemTouchHelperSimpleCallback)
        touchHelper.attachToRecyclerView(binding.recyclerView)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val list = numberAdapter.numbersList;
        binding.recyclerView.layoutManager = when (item.itemId) {
            R.id.linear_view -> {
                numberAdapter = NumberAdapter(R.layout.list_item_alphabate, this)
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            }
            R.id.grid_view -> {
                numberAdapter = NumberAdapter(R.layout.list_item_number_grid, this)
                GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
            }
            R.id.staggered_view -> {
                numberAdapter = NumberAdapter(R.layout.list_item_number_grid, this)
                StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            }
            else -> LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }

        binding.recyclerView.adapter = numberAdapter
        numberAdapter.numbersList = list
        return true
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
        startActivityForResult(DetailActivity.getIntent(position, list[position], this), RQ_DETAIL)
    }

    companion object {
        val RQ_DETAIL = 1
        private val EXTRA_POSITION = "POSITION"
        private val EXTRA_DATA = "DATA"
        fun getIntent(position: Int?, dataItem: Int?): Intent {
            val intent = Intent()
            intent.putExtra(EXTRA_POSITION, position)
            intent.putExtra(EXTRA_DATA, dataItem)
            return intent
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RQ_DETAIL) {
          if(resultCode== RESULT_OK){
              data?.let {
                  val position = data.extras?.getInt(EXTRA_POSITION)
                  val dataItem = data.extras?.getInt(EXTRA_DATA)
                  Log.d("MainActivity", "Result $position::$dataItem")

                  dataItem?.let {
                      position?.let {
                          val list = numberAdapter.numbersList;
                          list[position] = dataItem
                          numberAdapter.numbersList = list
                          binding.recyclerView.scrollToPosition(position)
                      }

                  }

              }
          }

        }
    }
}