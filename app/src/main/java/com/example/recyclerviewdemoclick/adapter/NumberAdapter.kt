package com.example.recyclerviewdemoclick.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewdemoclick.R

class NumberAdapter(private val layoutId: Int, private val clickListener: OnClickListener) :
    RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {
    var numbersList: MutableList<Int> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        return NumberViewHolder.create(parent, layoutId)
    }

    override fun getItemCount(): Int {
        return numbersList.size
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        val item = numbersList[position]
        holder.bind(item, clickListener)
    }


    class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemText = itemView.findViewById<TextView>(R.id.numberText)
        private val addImv = itemView.findViewById<ImageView>(R.id.addImv)
        private val resetImv = itemView.findViewById<ImageView>(R.id.resetImv)


        fun bind(item: Int, clickListener: OnClickListener) {
            itemText.text = item.toString()
            itemText.setOnClickListener {
                clickListener.onTextClick(adapterPosition)
            }
            addImv.setOnClickListener {
                clickListener.onAddClick(adapterPosition)
            }
            resetImv.setOnClickListener(View.OnClickListener {
                clickListener.onResetClick(adapterPosition)
            })
        }

        companion object {
            fun create(parent: ViewGroup, layoutId: Int): NumberViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
                return NumberViewHolder(view)
            }
        }
    }

    interface OnClickListener {
        fun onAddClick(position: Int)
        fun onResetClick(position: Int)
        fun onTextClick(position: Int)
    }
}