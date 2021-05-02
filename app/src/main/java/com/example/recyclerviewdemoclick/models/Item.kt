package com.example.recyclerviewdemoclick.models

import android.os.Parcelable
import androidx.databinding.BaseObservable
import kotlinx.parcelize.Parcelize


@Parcelize
class Item(var position: Int, var dataItem: Int) : Parcelable {

}
