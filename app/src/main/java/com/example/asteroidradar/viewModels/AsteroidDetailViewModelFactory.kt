package com.example.asteroidradar.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.asteroidradar.dataClasses.DataClasses

class AsteroidDetailViewModelFactory(private val asteroid: DataClasses.Asteroid, private val context: Context):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AsteroidDetailViewModel::class.java)){
            return AsteroidDetailViewModel(asteroid,context) as T
        }else{
            throw IllegalArgumentException("Unknown View Model class")
        }
    }
}