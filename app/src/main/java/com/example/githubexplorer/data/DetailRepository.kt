package com.example.githubexplorer.data

import android.content.SharedPreferences
import javax.inject.Inject

interface DetailRepository {

    fun saveOwnerAndName(owner: String, name: String)

    fun getOwner(): String

    fun getName(): String
}

class DetailRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : DetailRepository {

    override fun saveOwnerAndName(owner: String, name: String) {
        sharedPreferences.edit()
            .putString(OWNER_KEY, owner)
            .putString(NAME_KEY, name)
            .apply()
    }

    override fun getOwner(): String {
        return sharedPreferences.getString(OWNER_KEY, "") ?: ""
    }

    override fun getName(): String {
        return sharedPreferences.getString(NAME_KEY, "") ?: ""
    }
}