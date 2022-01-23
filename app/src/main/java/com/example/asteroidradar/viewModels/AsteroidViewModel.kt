package com.example.asteroidradar.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asteroidradar.R
import com.example.asteroidradar.dataClasses.DataClasses
import com.example.asteroidradar.database.AsteroidRadarDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber




class AsteroidViewModel(asteroidRadarDatabase: AsteroidRadarDatabase) :
    ViewModel() {

    private val _picOfDayURL = MutableLiveData<String>()
    val picOfDayURL: LiveData<String> get() = _picOfDayURL

    private val _picOfDayExplanation = MutableLiveData<String>()
    val picOfDayExplanation: LiveData<String> get() = _picOfDayExplanation

    private val _loadStatus = MutableLiveData<DataClasses.AsteroidLoadStatus>()
    val loadStatus: LiveData<DataClasses.AsteroidLoadStatus> get() = _loadStatus

    private val _navigateToSelectedAsteroidDetail = MutableLiveData<Long>()
    val navigateToSelectedAsteroidDetail: LiveData<Long> get() = _navigateToSelectedAsteroidDetail

    private val _asteroidsList: MutableLiveData<List<DataClasses.Asteroids>>? = null
//    val asteroidsList: LiveData<List<DataClasses.Asteroids>>? get() = _asteroidsList

    val asteroidsList: LiveData<List<DataClasses.Asteroids>>? get() = _asteroidsList

    init {
        getAsteroidsListFromDB(asteroidRadarDatabase)
        getPicURLFromDB(asteroidRadarDatabase)
        getPicExplanationFromDB(asteroidRadarDatabase)
    }

    private fun getAsteroidsListFromDB(asteroidRadarDatabase: AsteroidRadarDatabase){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadStatus.postValue(DataClasses.AsteroidLoadStatus.LOADING)
                _asteroidsList?.postValue(asteroidRadarDatabase.nearEarthAsteroidsDAO.getAsteroidsListData().value)
                _loadStatus.postValue(DataClasses.AsteroidLoadStatus.DONE)
            }catch (ex: Exception){
                Timber.e(ex.message)
                _loadStatus.postValue(DataClasses.AsteroidLoadStatus.ERROR)
                if (_asteroidsList != null) {
                    _asteroidsList.postValue(asteroidRadarDatabase.nearEarthAsteroidsDAO.getAsteroidsListData().value)
                }
            }
        }
    }

    fun displaySelectedAsteroidDetails(asteroidId: Long) {
        _navigateToSelectedAsteroidDetail.value = asteroidId
    }

    fun doneDisplayingAsteroidDetail() {
        _navigateToSelectedAsteroidDetail.value = null
    }


    private fun getPicURLFromDB(asteroidRadarDatabase: AsteroidRadarDatabase) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadStatus.postValue(DataClasses.AsteroidLoadStatus.LOADING)
                _picOfDayURL.postValue(asteroidRadarDatabase.picOfTheDayDAO.getHDImgUrl())
                _loadStatus.postValue(DataClasses.AsteroidLoadStatus.DONE)
            }catch (ex: Exception){
                Timber.d("Failed to retrieve Image of the day URL from the database for this reason : ${ex.message}")
                _loadStatus.postValue(DataClasses.AsteroidLoadStatus.ERROR)
                _picOfDayURL.postValue(R.drawable.ic_broken_image.toString())
            }
        }
    }

    private fun getPicExplanationFromDB(asteroidRadarDatabase: AsteroidRadarDatabase){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadStatus.postValue(DataClasses.AsteroidLoadStatus.LOADING)
                _picOfDayExplanation.postValue(asteroidRadarDatabase.picOfTheDayDAO.getExplanation())
                _loadStatus.postValue(DataClasses.AsteroidLoadStatus.DONE)
            }catch (ex: Exception){
                Timber.d("Failed to retrieve Image of the day URL from the database for this reason : ${ex.message}")
                _loadStatus.postValue(DataClasses.AsteroidLoadStatus.ERROR)
                _picOfDayExplanation.postValue("Content Description for this picture is not available")
            }
        }
    }
}