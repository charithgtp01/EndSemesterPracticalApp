package com.example.kuppiapp

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {
    fun setItemCount(userCount: Int) {
        _itemCount.value = userCount
    }

    private val _itemCount = MutableLiveData(0)
    val itemCount: LiveData<Int> get() = _itemCount

    @get:Bindable
    var name: String = ""

    @get:Bindable
    var email: String = ""


}