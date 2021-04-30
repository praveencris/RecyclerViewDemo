package com.example.recyclerviewdemoclick

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.recyclerviewdemoclick.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val binding =
            DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)

        val position = intent?.extras?.getInt(EXTRA_POSITION)
        var dataItem = intent?.extras?.getInt(EXTRA_DATA)
        Log.d("DetailActivity", "Result $position::$dataItem")

        binding.number1Text.text = dataItem.toString()

        binding.addButton.setOnClickListener {
            binding.number1Text.text = (binding.number1Text.text.toString().toInt() + 1).toString()
        }
        binding.doneButton.setOnClickListener {
            dataItem = binding.number1Text.text.toString().toInt()
            setResult(RESULT_OK, MainActivity.getIntent(position,dataItem))
            finish()
        }
    }

    companion object {
        private val EXTRA_POSITION = "POSITION"
        private val EXTRA_DATA = "DATA"
        fun getIntent(position: Int, dataItem: Int, context: Context): Intent {
            val intent: Intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_POSITION, position)
            intent.putExtra(EXTRA_DATA, dataItem)
            return intent
        }

    }
}