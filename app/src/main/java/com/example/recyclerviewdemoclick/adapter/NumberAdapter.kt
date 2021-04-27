package com.example.recyclerviewdemoclick.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewdemoclick.R

class NumberAdapter(val layoutId: Int, val clickListener: ClickListener) :
    ListAdapter<Int,NumberAdapter.ViewHolder>(DiffUtilNumberCallBack()) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemText = itemView.findViewById<TextView>(R.id.numberText)
        val imvAdd = itemView.findViewById<ImageView>(R.id.addImv)
        val imvReset = itemView.findViewById<ImageView>(R.id.resetImv)

        fun bind(item: Int, clickListener: ClickListener) {
            itemText.text = item.toString()
            itemText.setOnClickListener {
                clickListener.textClicked(adapterPosition)
            }
            imvAdd.setOnClickListener {
                clickListener.addClicked(adapterPosition)
            }
            imvReset.setOnClickListener {
                clickListener.resetClicked(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item, clickListener)
    }


    interface ClickListener {
        fun addClicked(position: Int)
        fun resetClicked(position: Int)
        fun textClicked(position: Int)
    }

    class DiffUtilNumberCallBack: DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
          return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem==newItem
        }

    }

}

