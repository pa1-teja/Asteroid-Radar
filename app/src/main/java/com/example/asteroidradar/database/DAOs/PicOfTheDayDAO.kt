package com.example.asteroidradar.database.DAOs

import androidx.room.*
import androidx.room.OnConflictStrategy.ABORT
import com.example.asteroidradar.dataClasses.DataClasses
import com.example.asteroidradar.database.Entities.PicOfDayEntity


@Dao
interface PicOfTheDayDAO {

    @Insert(onConflict = ABORT)
    fun insertPicOfTheDay(pictureOfTheDay: PicOfDayEntity)

//    @Delete
//    fun deletePicOfTheDay()

    @Query("SELECT * FROM image_of_the_day")
    fun getPicOfTheDayInfo(): PicOfDayEntity

    @Query("SELECT hdurl FROM image_of_the_day")
    fun getHDImgUrl():String

    @Query("SELECT explanation FROM image_of_the_day")
    fun getExplanation():String
}