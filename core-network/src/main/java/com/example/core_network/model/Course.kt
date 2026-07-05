package com.example.core_network.model

import com.google.gson.annotations.SerializedName

// Обертка для всего JSON ответа
data class CoursesResponse(
    @SerializedName("courses")
    val courses: List<Course>
)

// Модель одного курса
data class Course(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("text")
    val text: String,

    @SerializedName("price")
    val price: String, // Цена часто приходит со знаком валюты, поэтому String

    @SerializedName("rate")
    val rate: Double,

    @SerializedName("startDate")
    val startDate: String,

    @SerializedName("hasLike")
    val hasLike: Boolean,

    @SerializedName("publishDate")
    val publishDate: String
)