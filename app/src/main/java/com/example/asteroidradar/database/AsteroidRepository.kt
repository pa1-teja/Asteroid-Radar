package com.example.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.asteroidradar.dataClasses.DataClasses

class AsteroidRepository(private val asteroidRadarDatabase: AsteroidRadarDatabase) {

    val asteroidsList: LiveData<List<DataClasses.Asteroids>> =
        Transformations.map(asteroidRadarDatabase.nearEarthAsteroidsDAO.getAsteroidsListData()) {
            it
        }
}