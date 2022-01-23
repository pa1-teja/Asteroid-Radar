package com.example.asteroidradar.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.asteroidradar.database.AsteroidRadarDatabase

class AsteroidDetailViewModelFactory(private val asteroidId: Long, private val asteroidRadarDatabase: AsteroidRadarDatabase):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AsteroidDetailViewModel::class.java)){
            return AsteroidDetailViewModel(asteroidId,asteroidRadarDatabase) as T
        }else{
            throw IllegalArgumentException("Unknown View Model class")
        }
    }
}