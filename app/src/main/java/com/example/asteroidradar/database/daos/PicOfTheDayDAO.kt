package com.example.asteroidradar.database.daos

import androidx.room.*
import com.example.asteroidradar.database.Entities.PicOfDayEntity


@Dao
interface PicOfTheDayDAO {

    @Query("INSERT INTO image_of_the_day(date,explanation, hdurl, media_type, service_version, title, url) VALUES(:date,:explanation,:hdurl,:mediaType,:serviceVersion,:title,:url) ")
    fun insertPicOfTheDay(date:String,explanation:String, hdurl:String,mediaType:String, serviceVersion:String, title:String, url:String)

    @Query("DELETE FROM image_of_the_day")
    fun deletePicOfTheDay()

    @Query("SELECT * FROM image_of_the_day")
    fun getPicOfTheDayInfo(): PicOfDayEntity

    @Query("SELECT hdurl FROM image_of_the_day")
    fun getHDImgUrl():String

    @Query("SELECT explanation FROM image_of_the_day")
    fun getExplanation():String
}