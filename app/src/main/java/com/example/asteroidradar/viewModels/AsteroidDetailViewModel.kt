package com.example.asteroidradar.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asteroidradar.dataClasses.DataClasses
import com.example.asteroidradar.database.AsteroidRadarDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class AsteroidDetailViewModel(private val asteroidId:Long, asteroidRadarDatabase: AsteroidRadarDatabase): ViewModel() {

    private val _detailsOfSelectedAsteroid = MutableLiveData<DataClasses.AsteroidDetails>()

    val detailsOfSelectedAsteroid: LiveData<DataClasses.AsteroidDetails> get() = _detailsOfSelectedAsteroid

    private val _loadStatus = MutableLiveData<DataClasses.AsteroidLoadStatus>()

    val loadStatus: LiveData<DataClasses.AsteroidLoadStatus> get() = _loadStatus

    init {
        getAsteroidDetailsFromDB(asteroidRadarDatabase)
    }

    fun getAsteroidDetailsFromDB(asteroidRadarDatabase: AsteroidRadarDatabase) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadStatus.postValue(DataClasses.AsteroidLoadStatus.LOADING)
                val asteroidDetail = asteroidRadarDatabase.nearEarthAsteroidsDAO.getAllAsteroidInfo(asteroidId)
                _detailsOfSelectedAsteroid.postValue(asteroidDetail)
                _loadStatus.postValue(DataClasses.AsteroidLoadStatus.DONE)
            } catch (ex: Exception) {
                Timber.e(ex.message)
                _loadStatus.postValue(DataClasses.AsteroidLoadStatus.ERROR)
            }
        }
    }

}