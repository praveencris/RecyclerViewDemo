package com.example.recyclerviewdemoclick.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.recyclerviewdemoclick.R
import com.example.recyclerviewdemoclick.databinding.FragmentDetailBinding
import com.example.recyclerviewdemoclick.models.Item

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val EXTRA_ITEM = "extra_item"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var item: Item


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            item = it.getParcelable(EXTRA_ITEM)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)


        Log.d("DetailActivity", "Result ${item.dataItem}::${item.dataItem}")

        binding.number1Text.text = item.dataItem.toString()

        binding.addButton.setOnClickListener {
            binding.number1Text.text = (binding.number1Text.text.toString().toInt() + 1).toString()
        }
        binding.doneButton.setOnClickListener {
            item.dataItem = binding.number1Text.text.toString().toInt()
            (targetFragment as? MainFragment)?.setItem(item)
            activity?.supportFragmentManager?.popBackStackImmediate()

        }
        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(item: Item) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_ITEM, item)
                }
            }
    }
}