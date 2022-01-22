package com.example.asteroidradar.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asteroidradar.Network.NetworkUtils
import com.example.asteroidradar.R
import com.example.asteroidradar.dataClasses.DataClasses
import com.example.asteroidradar.database.AsteroidRadarDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber


enum class AsteroidLoadStatus { LOADING, ERROR, DONE }

class AsteroidViewModel(context: Context, asteroidRadarDatabase: AsteroidRadarDatabase) :
    ViewModel() {

    private val _picOfDayURL = MutableLiveData<String>()

    val picOfDayURL: LiveData<String> get() = _picOfDayURL

    private val _picOfDayExplanation = MutableLiveData<String>()

    val picOfDayExplanation: LiveData<String> get() = _picOfDayExplanation

    private var _nearEarthAsteroids = MutableLiveData<List<DataClasses.Asteroid>>()

    val nearEarthAsteroids: LiveData<List<DataClasses.Asteroid>> get() = _nearEarthAsteroids

    private val _loadStatus = MutableLiveData<AsteroidLoadStatus>()

    val loadStatus: LiveData<AsteroidLoadStatus> get() = _loadStatus

    private val _navigateToSelectedAsteroidDetail = MutableLiveData<DataClasses.Asteroid>()

    val navigateToSelectedAsteroidDetail: LiveData<DataClasses.Asteroid> get() = _navigateToSelectedAsteroidDetail

    private val _asteroidsList = MutableLiveData<List<DataClasses.asteroids>>()

    val asteroidsList: LiveData<List<DataClasses.asteroids>> get() = _asteroidsList

    init {
        getAsteroidsList(asteroidRadarDatabase)
        getPicURLFromDB(asteroidRadarDatabase)
        getPicExplanation(asteroidRadarDatabase)
    }

    fun getAsteroidsList(asteroidRadarDatabase: AsteroidRadarDatabase){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadStatus.postValue(AsteroidLoadStatus.LOADING)
                _asteroidsList.postValue(asteroidRadarDatabase.nearEarthAsteroidsDAO.getAsteroidsListData())
                _loadStatus.postValue(AsteroidLoadStatus.DONE)
            }catch (ex: Exception){
                Timber.e(ex.message)
                _loadStatus.postValue(AsteroidLoadStatus.ERROR)
                _asteroidsList.postValue(asteroidRadarDatabase.nearEarthAsteroidsDAO.getAsteroidsListData())
            }
        }
    }

    fun displaySelectedAsteroidDetails(asteroid: DataClasses.Asteroid) {
        _navigateToSelectedAsteroidDetail.value = asteroid
    }

    fun doneDisplayingAsteroidDetail() {
        _navigateToSelectedAsteroidDetail.value = null
    }


    private fun getPicURLFromDB(asteroidRadarDatabase: AsteroidRadarDatabase) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadStatus.postValue(AsteroidLoadStatus.LOADING)
                _picOfDayURL.postValue(asteroidRadarDatabase.picOfTheDayDAO.getHDImgUrl())
                _loadStatus.postValue(AsteroidLoadStatus.DONE)
            }catch (ex: Exception){
                Timber.d("Failed to retrieve Image of the day URL from the database for this reason : ${ex.message}")
                _loadStatus.postValue(AsteroidLoadStatus.ERROR)
                _picOfDayURL.postValue(R.drawable.ic_broken_image.toString())
            }
        }
    }

    private fun getPicExplanation(asteroidRadarDatabase: AsteroidRadarDatabase){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadStatus.postValue(AsteroidLoadStatus.LOADING)
                _picOfDayExplanation.postValue(asteroidRadarDatabase.picOfTheDayDAO.getExplanation())
                _loadStatus.postValue(AsteroidLoadStatus.DONE)
            }catch (ex: Exception){
                Timber.d("Failed to retrieve Image of the day URL from the database for this reason : ${ex.message}")
                _loadStatus.postValue(AsteroidLoadStatus.ERROR)
                _picOfDayExplanation.postValue("Content Description for this picture is not available")
            }
        }
    }
}