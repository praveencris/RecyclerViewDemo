package com.example.recyclerviewdemoclick

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.recyclerviewdemoclick.databinding.ActivityMainBinding
import com.example.recyclerviewdemoclick.fragments.DetailFragment
import com.example.recyclerviewdemoclick.fragments.MainFragment
import com.example.recyclerviewdemoclick.models.Item


class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        replace(MainFragment(),"MainFragment")
    }


    /**
     * Adds a fragment to the container and back stack with the given tag.
     */
    fun replace(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }






}