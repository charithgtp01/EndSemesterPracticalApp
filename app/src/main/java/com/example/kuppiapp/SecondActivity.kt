package com.example.kuppiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.kuppiapp.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    lateinit var dataBindingSecondBinding: ActivitySecondBinding

    private val recycleViewModel: RecycleViewModel by viewModels()

    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBindingSecondBinding = DataBindingUtil.setContentView(this, R.layout.activity_second)
        dataBindingSecondBinding.lifecycleOwner = this
        dataBindingSecondBinding.vm = recycleViewModel
        //Need to pass user list to the adapter
        val users = SharedPref(this).getUsers() as List<User>


        userAdapter = UserAdapter(this, users)

        recycleViewModel.users.observe(this) {
            recycleViewModel.setUsers(it)
            userAdapter.setUserList(it)
        }

        dataBindingSecondBinding.recycleView.adapter = userAdapter
    }
}