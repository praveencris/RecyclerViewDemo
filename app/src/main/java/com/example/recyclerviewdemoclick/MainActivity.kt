package com.example.recyclerviewdemoclick

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.*
import com.example.recyclerviewdemoclick.adapter.NumberAdapter
import com.example.recyclerviewdemoclick.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity(), NumberAdapter.ClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var numberAdapter: NumberAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide the status bar.
       // window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        supportActionBar?.hide()



        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        numberAdapter = NumberAdapter(R.layout.list_item_alphabate, this)
        binding.recyclerView.adapter = numberAdapter
        binding.recyclerView.layoutManager = layoutManager
        binding.toolbar.inflateMenu(R.menu.menu)
        binding.toolbar.setOnMenuItemClickListener { item ->
            val list = numberAdapter.currentList
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
                else -> {
                    numberAdapter = NumberAdapter(R.layout.list_item_alphabate, this)
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                }
            }
            binding.recyclerView.adapter = numberAdapter
            numberAdapter.submitList(list)
            true
        }

        val numbersList = (1..20).toMutableList()
        numberAdapter.submitList(numbersList)


        val itemTouchSimpleHelper = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val list: MutableList<Int> = numberAdapter.currentList.toMutableList()
                list.removeAt(viewHolder.adapterPosition)
                numberAdapter.submitList(list)
            }

        }

        val itemTouchHelper = ItemTouchHelper(itemTouchSimpleHelper)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)


        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
               tab?.let {
                   when(it.position){
                       0->firstClicked(it.view)
                       1->SecondClicked(it.view)
                       2->ThirdClicked(it.view)
                       3->FourthClicked(it.view)
                   }
               }
            }

        })

    }

  /*  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }*/

/*    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val list = numberAdapter.currentList
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
            else -> {
                numberAdapter = NumberAdapter(R.layout.list_item_alphabate, this)
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            }
        }

        binding.recyclerView.adapter = numberAdapter
        numberAdapter.submitList(list)
        return true
    }*/

    /*  override fun addClicked(position: Int) {
          val list=numberAdapter.currentList.toMutableList()
          list[position]=list[position]+1
          numberAdapter.submitList(list)
      }

      override fun resetClicked(position: Int) {
          val list=numberAdapter.currentList.toMutableList()
          list[position] = 0
          numberAdapter.submitList(list)
      }
  */
    override fun textClicked(position: Int) {
        Snackbar.make(
            binding.root,
            "Number clicked: ${numberAdapter.currentList[position]}",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    fun firstClicked(view: View) {
        Toast.makeText(this,"First clicked!",Toast.LENGTH_SHORT).show()
    }
    fun SecondClicked(view: View) {
        Toast.makeText(this,"Second clicked!",Toast.LENGTH_SHORT).show()
    }
    fun ThirdClicked(view: View) {
        Toast.makeText(this,"Third clicked!",Toast.LENGTH_SHORT).show()
    }
    fun FourthClicked(view: View) {
        Toast.makeText(this,"Fourth clicked!",Toast.LENGTH_SHORT).show()
    }
}