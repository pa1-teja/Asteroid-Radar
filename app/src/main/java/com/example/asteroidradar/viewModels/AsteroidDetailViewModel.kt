package com.example.asteroidradar.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.asteroidradar.dataClasses.DataClasses

class AsteroidDetailViewModel(private val asteroid:DataClasses.Asteroid, private val context: Context): ViewModel() {

    private val _detailsOfSelectedAsteroid = MutableLiveData<DataClasses.Asteroid>()

    val detailsOfSelectedAsteroid: LiveData<DataClasses.Asteroid> get() = _detailsOfSelectedAsteroid

    private val _closeApproachAsteroidDetail = MutableLiveData<DataClasses.CloseApproachData>()

    val closeApproachAsteroidDetail: LiveData<DataClasses.CloseApproachData> get() = _closeApproachAsteroidDetail

    private val _dangerousAsteroidsFlag = MutableLiveData<Boolean>()

    val dangerousAsteroidsFlag: LiveData<Boolean> get() = _dangerousAsteroidsFlag

    init {
        _detailsOfSelectedAsteroid.value = asteroid
        _closeApproachAsteroidDetail.value = asteroid.closeApproachData.get(asteroid.closeApproachData.size-1)
        _dangerousAsteroidsFlag.value = asteroid.isPotentiallyHazardousAsteroid
    }


}