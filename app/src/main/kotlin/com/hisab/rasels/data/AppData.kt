package com.hisab.rasels.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class AppDataModel(
    var tx: MutableList<Transaction> = mutableListOf(),
    var cats: MutableList<String> = mutableListOf(
        "খাবার", "যাতায়াত", "পড়াশোনা", "বাসা", 
        "মোবাইল", "ধর্মীয়", "ব্যবসা", "অন্যান্য"
    )
)

class DataManager(private val context: Context) {
    private val sharedPref = context.getSharedPreferences("hisab_pro", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val KEY = "app_data"

    fun getData(): AppDataModel {
        val json = sharedPref.getString(KEY, null) ?: return AppDataModel()
        return try {
            gson.fromJson(json, AppDataModel::class.java)
        } catch (e: Exception) {
            AppDataModel()
        }
    }

    fun saveData(data: AppDataModel) {
        sharedPref.edit().putString(KEY, gson.toJson(data)).apply()
    }
}