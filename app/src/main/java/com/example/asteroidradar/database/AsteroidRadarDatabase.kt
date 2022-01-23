package com.example.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.asteroidradar.database.daos.NearEarthAsteroidsDAO
import com.example.asteroidradar.database.daos.PicOfTheDayDAO
import com.example.asteroidradar.database.entities.AsteroidsDataTable
import com.example.asteroidradar.database.entities.CloseApproachDataTable
import com.example.asteroidradar.database.entities.MasterTable
import com.example.asteroidradar.database.entities.PicOfDayEntity


@Database(entities = [PicOfDayEntity::class, MasterTable::class, AsteroidsDataTable::class, CloseApproachDataTable::class],
    version = 1, exportSchema = false)
abstract class AsteroidRadarDatabase: RoomDatabase() {
    abstract val picOfTheDayDAO: PicOfTheDayDAO
    abstract val nearEarthAsteroidsDAO: NearEarthAsteroidsDAO

    companion object{

        private var INSTANCE: AsteroidRadarDatabase? = null

        fun getDatabaseInstance(context: Context): AsteroidRadarDatabase {
            synchronized(this){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context,AsteroidRadarDatabase::class.java,"asteroid_radar_app_database")
                        .fallbackToDestructiveMigration().build()

                }
                return INSTANCE!!
            }
        }
    }
}