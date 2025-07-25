package com.acdevs.knowyourillness.data.repository

import android.content.Context
import com.acdevs.knowyourillness.data.model.Symptom
import org.json.JSONObject

class SymptomRepository(private val context: Context) {
    fun loadSymptoms(): List<Symptom> {
        val json = context.assets.open("symptom_index.json").bufferedReader().use { it.readText() }
        val jsonObject = JSONObject(json)
        val list = mutableListOf<Symptom>()

        jsonObject.keys().forEach {
            val id = jsonObject.getInt(it)
            list.add(Symptom(name = it.replace("_", " "), id = id))
        }

        return list.sortedBy { it.id }
    }
}
