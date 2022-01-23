package com.example.asteroidradar.database.daos

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.asteroidradar.database.entities.PicOfDayEntity


@Dao
interface PicOfTheDayDAO {

    @Insert(entity = PicOfDayEntity::class, onConflict = REPLACE)
    fun insertPicOfTheDay(picOfDayEntity: PicOfDayEntity)

    @Query("DELETE FROM image_of_the_day")
    fun deletePicOfTheDay()

    @Query("SELECT * FROM image_of_the_day")
    fun getPicOfTheDayInfo(): PicOfDayEntity

    @Query("SELECT hdurl FROM image_of_the_day")
    fun getHDImgUrl():String

    @Query("SELECT explanation FROM image_of_the_day")
    fun getExplanation():String
}