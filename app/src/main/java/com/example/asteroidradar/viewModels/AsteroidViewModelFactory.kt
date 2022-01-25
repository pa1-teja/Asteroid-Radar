package com.example.asteroidradar.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.asteroidradar.database.AsteroidRadarDatabase

class AsteroidViewModelFactory(private val context: Context,private val database: AsteroidRadarDatabase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AsteroidViewModel::class.java)){
            return AsteroidViewModel(context,database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}