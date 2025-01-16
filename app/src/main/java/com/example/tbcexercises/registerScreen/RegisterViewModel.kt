package com.example.tbcexercises.registerScreen

import androidx.lifecycle.ViewModel
import com.example.tbcexercises.models.Card
import com.example.tbcexercises.util.Result
import com.example.tbcexercises.models.Views
import kotlinx.serialization.json.Json

class RegisterViewModel : ViewModel() {

    private val data =
        """[[{"field_id":1,"hint":"UserName","field_type":"input","keyboard":"text","required":false,"is_active":false,"icon":"https://jemala.png"},{"field_id":2,"hint":"Email","field_type":"input","required":true,"keyboard":"text","is_active":true,"icon":"https://jemala.png"},{"field_id":3,"hint":"phone","field_type":"input","required":true,"keyboard":"number","is_active":true,"icon":"https://jemala.png"}],[{"field_id":4,"hint":"FullName","field_type":"input","keyboard":"text","required":true,"is_active":true,"icon":"https://jemala.png" },{"field_id":14,"hint":"Jemali","field_type":"input","keyboard":"text","required":false,"is_active":true,"icon":"https://jemala.png" },{"field_id":89,"hint":"Birthday","field_type":"chooser","required":false,"is_active":true,"icon":"https://jemala.png" },{"field_id":898,"hint":"Gender","field_type":"chooser","required":false,"is_active":false,"icon":"https://jemala.png" }]]"""
    private val json = Json { ignoreUnknownKeys = true }

    private val startList: List<List<Views>> = json.decodeFromString(data)

    val decodedData: List<Card> = startList.map { Card(it) }

    fun checkForRequiredFields(): Result {
        val flattedDecodedData = decodedData.flatMap { card -> card.views }
        for (view in flattedDecodedData) {
            if (view.required && view.userInput.isNullOrEmpty()) {
                return Result.OnError(view.hint)
            }
        }
        val profile = flattedDecodedData.associate { view ->
            view.fieldId to view.userInput
        }
        return Result.OnSuccess(profile.toString())
    }

    fun updateView(id: Int, text: String) {
        for (card in decodedData) {
            val view = card.views.firstOrNull { it.fieldId == id }
            if (view !== null) {
                view.userInput = text
            }
        }
    }

}