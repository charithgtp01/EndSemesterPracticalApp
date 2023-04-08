package com.example.kuppiapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecycleViewModel : ViewModel() {
    fun setUsers(users: List<User>) {
        _users.value = users
    }

    private var _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users


}