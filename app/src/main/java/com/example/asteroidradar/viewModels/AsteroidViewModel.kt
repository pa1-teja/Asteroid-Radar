package com.example.asteroidradar.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asteroidradar.dataClasses.DataClasses
import com.example.asteroidradar.Network.NetworkUtils
import kotlinx.coroutines.launch
import java.lang.Exception


enum class AsteroidLoadStatus{LOADING,ERROR,DONE}

class AsteroidViewModel(context: Context): ViewModel() {


    private val _nearEarthAsteroids = MutableLiveData<List<DataClasses.Asteroid>>()

    val nearEarthAsteroids: LiveData<List<DataClasses.Asteroid>> get() = _nearEarthAsteroids

    private val _loadStatus = MutableLiveData<AsteroidLoadStatus>()

    val loadStatus: LiveData<AsteroidLoadStatus> get() = _loadStatus

    private val _navigateToSelectedAsteroidDetail = MutableLiveData<DataClasses.Asteroid>()

    val navigateToSelectedAsteroidDetail: LiveData<DataClasses.Asteroid> get() = _navigateToSelectedAsteroidDetail

    private val networkUtils = NetworkUtils()


    private val _pictureOfTheDay = MutableLiveData<DataClasses.PictureOfTheDay>()

    val pictureOfTheDay: LiveData<DataClasses.PictureOfTheDay> get() = _pictureOfTheDay

    init {
        getNearEarthAsteroids(context)
        getPictureOfTheDay(context)
    }

    fun displaySelectedAsteroidDetails(asteroid: DataClasses.Asteroid){
        _navigateToSelectedAsteroidDetail.value = asteroid
    }

     fun doneDisplayingAsteroidDetail(){
        _navigateToSelectedAsteroidDetail.value = null
    }

    private fun getNearEarthAsteroids(context: Context) {
            viewModelScope.launch {
                try {
                    _loadStatus.value = AsteroidLoadStatus.LOADING
                    _nearEarthAsteroids.value = networkUtils.fetchNearEarthAsteroids(context)
                    _loadStatus.value = AsteroidLoadStatus.DONE
                } catch (ex: Exception){
                    _loadStatus.value = AsteroidLoadStatus.ERROR
                    _nearEarthAsteroids.value = ArrayList()
                }
            }
    }

    private fun getPictureOfTheDay(context: Context){
        viewModelScope.launch {
            try{
                _loadStatus.value = AsteroidLoadStatus.LOADING
                _pictureOfTheDay.value = networkUtils.fetchPictureOfTheDay(context)
                _loadStatus.value = AsteroidLoadStatus.DONE
            }catch (ex: Exception){
                _loadStatus.value = AsteroidLoadStatus.ERROR
                _pictureOfTheDay.value = DataClasses.PictureOfTheDay("","","",
                    "","","","")
            }
        }
    }


}