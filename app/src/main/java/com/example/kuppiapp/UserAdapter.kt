package com.example.kuppiapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kuppiapp.databinding.AdapterUserBinding
import com.google.android.material.shadow.ShadowRenderer

class UserAdapter(val context: Context, private var users: List<User>) :
    RecyclerView.Adapter<MainViewHolder>(), DeleteButtonClickListener {
    //    var users = listOf<User>()
    fun setUserList(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterUserBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val user = users[position]
        holder.binding.user = user
        holder.binding.button3.setOnClickListener({
            this
                .onDeleteClick(user)
        })
    }

    override fun onDeleteClick(user: User) {
        SharedPref(context).deleteUser(user, object : UserListListener {
            override fun getUsers(users: List<User>) {
                setUserList(users)
            }

        })
    }

}

interface DeleteButtonClickListener {
    fun onDeleteClick(user: User)
}

class MainViewHolder(val binding: AdapterUserBinding) : RecyclerView.ViewHolder(binding.root) {
}
