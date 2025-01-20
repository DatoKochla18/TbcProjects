package com.example.tbcexercises.data.remote

import com.example.tbcexercises.data.model.Message
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Types

@JsonClass(generateAdapter = true)
object MoshiInstance {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val type = Types.newParameterizedType(List::class.java, Message::class.java)
    val adapter = moshi.adapter<List<Message>>(type)


}
