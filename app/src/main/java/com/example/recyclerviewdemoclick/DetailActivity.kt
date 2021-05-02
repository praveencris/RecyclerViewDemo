package com.example.recyclerviewdemoclick

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.recyclerviewdemoclick.databinding.ActivityDetailBinding
import com.example.recyclerviewdemoclick.models.Item

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val binding =
            DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)

        val item:Item = intent?.extras?.getParcelable<Item>(EXTRA_ITEM) as Item
        Log.d("DetailActivity", "Result ${item.dataItem}::${item.dataItem}")

        binding.number1Text.text = item.dataItem.toString()

        binding.addButton.setOnClickListener {
            binding.number1Text.text = (binding.number1Text.text.toString().toInt() + 1).toString()
        }
        binding.doneButton.setOnClickListener {
            item.dataItem=binding.number1Text.text.toString().toInt()
            setResult(RESULT_OK, MainActivity.getIntent(item))
            finish()
        }
    }

    companion object {
        private val EXTRA_ITEM = "EXTRA_ITEM"
        fun getIntent(item: Item, context: Context): Intent {
            val intent: Intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_ITEM, item)
            return intent
        }

    }
}