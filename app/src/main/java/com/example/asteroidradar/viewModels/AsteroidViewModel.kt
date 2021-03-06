package com.example.asteroidradar.viewModels

import android.content.Context
import androidx.lifecycle.*
import com.example.asteroidradar.R
import com.example.asteroidradar.Utils
import com.example.asteroidradar.dataClasses.DataClasses
import com.example.asteroidradar.database.AsteroidRadarDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber




class AsteroidViewModel(val context: Context,val asteroidRadarDatabase: AsteroidRadarDatabase) :
    ViewModel() {

    private val _picOfDayURL = MutableLiveData<String>()
    val picOfDayURL: LiveData<String> get() = _picOfDayURL

    private val _picOfDayExplanation = MutableLiveData<String>()
    val picOfDayExplanation: LiveData<String> get() = _picOfDayExplanation

    private val _loadStatus = MutableLiveData<DataClasses.AsteroidLoadStatus>()
    val loadStatus: LiveData<DataClasses.AsteroidLoadStatus> get() = _loadStatus

    private val _navigateToSelectedAsteroidDetail = MutableLiveData<Long>()
    val navigateToSelectedAsteroidDetail: LiveData<Long> get() = _navigateToSelectedAsteroidDetail

    private val _asteroidsList = MutableLiveData<List<DataClasses.Asteroids>>()

    val asteroidsList: LiveData<List<DataClasses.Asteroids>> get() = _asteroidsList



    init {
        getAsteroidsListFromDB(asteroidRadarDatabase,DataClasses.AsteroidListRecordsFilter.SAVED)
        getPicURLFromDB(asteroidRadarDatabase)
        getPicExplanationFromDB(asteroidRadarDatabase)
    }

    fun getAsteroidsListFromDB(asteroidRadarDatabase: AsteroidRadarDatabase, asteroidFilter: DataClasses.AsteroidListRecordsFilter){
        viewModelScope.launch(Dispatchers.IO) {
            try {
             when(asteroidFilter){
                 DataClasses.AsteroidListRecordsFilter.TODAY ->{
                     _loadStatus.postValue(DataClasses.AsteroidLoadStatus.LOADING)
                     asteroidRadarDatabase.nearEarthAsteroidsDAO.getTodayAsteroidsListData(Utils().getTodayDate(context)) .collectLatest {
                         _asteroidsList.postValue(it)
                         _loadStatus.postValue(DataClasses.AsteroidLoadStatus.DONE)
                     }
                 }
                 DataClasses.AsteroidListRecordsFilter.WEEK ->{
                     _loadStatus.postValue(DataClasses.AsteroidLoadStatus.LOADING)
                     val weekDates = Utils().getNextSevenDaysFormatted(context)
                     asteroidRadarDatabase.nearEarthAsteroidsDAO.getWeekAsteroidsListData(weekDates.get(0),weekDates.get(weekDates.lastIndex)).collectLatest {
                         _asteroidsList.postValue(it)
                         _loadStatus.postValue(DataClasses.AsteroidLoadStatus.DONE)
                     }
                 }
                 DataClasses.AsteroidListRecordsFilter.SAVED ->{
                     _loadStatus.postValue(DataClasses.AsteroidLoadStatus.LOADING)
                     asteroidRadarDatabase.nearEarthAsteroidsDAO.getAsteroidsListData().collectLatest {
                         _asteroidsList.postValue(it)
                         _loadStatus.postValue(DataClasses.AsteroidLoadStatus.DONE)
                     }

                 }
             }
            }catch (ex: Exception){
                Timber.e(ex.message)
                _loadStatus.postValue(DataClasses.AsteroidLoadStatus.ERROR)
                asteroidRadarDatabase.nearEarthAsteroidsDAO.getAsteroidsListData().collectLatest {
                    _asteroidsList.postValue(it)
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
                asteroidRadarDatabase.picOfTheDayDAO.getHDImgUrl().collectLatest {
                    _picOfDayURL.postValue(it)
                    _loadStatus.postValue(DataClasses.AsteroidLoadStatus.DONE)
                }
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
                asteroidRadarDatabase.picOfTheDayDAO.getExplanation().collectLatest {
                    _picOfDayExplanation.postValue(it)
                    _loadStatus.postValue(DataClasses.AsteroidLoadStatus.DONE)
                }
            }catch (ex: Exception){
                Timber.d("Failed to retrieve Image of the day URL from the database for this reason : ${ex.message}")
                _loadStatus.postValue(DataClasses.AsteroidLoadStatus.ERROR)
                _picOfDayExplanation.postValue("Content Description for this picture is not available")
            }
        }
    }
}