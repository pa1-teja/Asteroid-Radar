package com.example.asteroidradar.database.Entities

import androidx.annotation.NonNull
import androidx.room.*


@Entity(tableName = "image_of_the_day")
data class PicOfDayEntity(

    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: String,


    @ColumnInfo(name = "explanation")
    val explanation: String,

    @ColumnInfo(name = "hdurl")
    @NonNull
    val hdurl: String,


    @ColumnInfo(name = "media_type")
    val media_type: String,


    @ColumnInfo(name = "service_version")
    val service_version: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "url")
    val url: String
)
