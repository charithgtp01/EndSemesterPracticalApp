package com.example.kuppiapp

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

class SharedPref(context: Context) {
    private val pref: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = pref.edit()

    fun saveUserToPref(user: User, userPreferenceListener: UserPreferenceListener) {
        //Get existing users
        var existingUsers = getUsers()

        //If the array is null should initialize the array
        if (existingUsers == null)
            existingUsers = listOf()
        //need to add new user to the user list
        existingUsers = existingUsers + user

        val jsonArray = getJSONArrayFromUserList(existingUsers as List<User>)

        //Save in the pref
        val responseID = savePref(Constants.USER_LIST, jsonArray.toString())

        if (responseID == 0)
            userPreferenceListener.onError()
        else
            userPreferenceListener.onSuccess(existingUsers.size)
    }

    //Get JSON array from User list
    private fun getJSONArrayFromUserList(existingUsers: List<User>): JSONArray {
        var jsonArray = JSONArray()
        for (obj in existingUsers) {

            val jsonObject = JSONObject();
            jsonObject.put("name", obj!!.name)
            jsonObject.put("email", obj.email)
            jsonArray.put(jsonObject)
        }

        return jsonArray
    }

    fun getUsers(): List<User?>? {
        //Json String from Pref
        val jsonString: String? = getPref(Constants.USER_LIST)
        //return list
        return getUserListFromString(jsonString)
    }

    //Method to get User List from String
    private fun getUserListFromString(jsonString: String?): List<User> {
        val jsonArray: JSONArray
        var tempArray = listOf<User>()

        if (jsonString != null) {
            jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val user = User(jsonObject.getString("name"), jsonObject.getString("email"))
                tempArray = tempArray + user
            }

        }

        return tempArray
    }

    fun getUserCount(): Int {
        val listUsers = getUsers()
        if (listUsers != null) {
            return listUsers.size
        }

        return 0
    }


    private fun savePref(key: String, value: String): Int {
        try {
            editor.putString(key, value)
            editor.commit()
        } catch (e: java.lang.Exception) {
            return 0;
        }

        return 1
    }

    private fun getPref(key: String): String? {
        return pref.getString(key, null)
    }

    fun deleteUser(user: User, userListListener: UserListListener) {
        //Existing user
        var users = getUsers()

        //Remove selected user
        users = users?.minus(user)
        //Save remaining User list string
        savePref(Constants.USER_LIST, getJSONArrayFromUserList(users as List<User>).toString())

        //Pass remaing user list by interface
        userListListener.getUsers(users)
    }
}

interface UserPreferenceListener {
    fun onSuccess(userCount: Int)

    fun onError()
}

interface UserListListener {
    fun getUsers(users: List<User>)
}