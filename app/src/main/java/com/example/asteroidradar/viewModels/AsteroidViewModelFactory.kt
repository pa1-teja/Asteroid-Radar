package com.example.asteroidradar.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AsteroidViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AsteroidViewModel::class.java)){
            return AsteroidViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}