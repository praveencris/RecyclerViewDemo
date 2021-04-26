package com.example.recyclerviewdemoclick

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        binding.recyclerView.adapter=numberAdapter
        val list= (1..20).toMutableList()

        numberAdapter.numbersList=list
    }

    override fun onAddClick(position: Int) {
        val list = numberAdapter.numbersList;
        list[position]=list[position] + 1
        numberAdapter.numbersList = list
        binding.recyclerView.scrollToPosition(position)
    }

    override fun onResetClick(position: Int) {
        val list = numberAdapter.numbersList;
        list[position]=0
        numberAdapter.numbersList = list
        binding.recyclerView.scrollToPosition(position)
    }

    override fun onTextClick(position: Int) {
        val list = numberAdapter.numbersList;
        Snackbar.make(binding.root, "Item Clicked ${list[position]}", Snackbar.LENGTH_SHORT).show()
    }
}