package com.example.kuppiapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.kuppiapp.Constants.Companion.TAG
import com.example.kuppiapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityMainBinding
    val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.lifecycleOwner = this
        dataBinding.vm = viewModel

        dataBinding.button.setOnClickListener {
            submit()
        }

        dataBinding.button2.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }


    }

    private fun submit() {

        val name = viewModel.name
        val email = viewModel.email

        when {
            name.isBlank() -> {
                Toast.makeText(this, "Name is mandatory", Toast.LENGTH_SHORT).show()
            }

            email.isBlank() -> {
                Toast.makeText(applicationContext, "Email is mandatory", Toast.LENGTH_SHORT).show()
            }
            else -> {

                val user = User(name, email)

                SharedPref(this).saveUserToPref(user, object : UserPreferenceListener {

                    override fun onSuccess(userCount: Int) {
                        Log.d(TAG, userCount.toString())
                        viewModel.setItemCount(userCount)
                    }

                    override fun onError() {
                        Log.d(TAG, "Error")

                    }
                })


            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.setItemCount(SharedPref(this).getUserCount())
    }

}