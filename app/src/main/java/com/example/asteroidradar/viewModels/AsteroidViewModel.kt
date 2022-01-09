package com.example.asteroidradar.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asteroidradar.DataClasses.DataClasses
import com.example.asteroidradar.Network.NasaApiServices
import com.example.asteroidradar.Network.NetworkUtils
import com.example.asteroidradar.R
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber
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
//            try{
                _loadStatus.value = AsteroidLoadStatus.LOADING
                _pictureOfTheDay.value = networkUtils.fetchPictureOfTheDay(context)
                _loadStatus.value = AsteroidLoadStatus.DONE
//            }catch (ex: Exception){
//                _loadStatus.value = AsteroidLoadStatus.ERROR
//                _pictureOfTheDay.value = DataClasses.PictureOfTheDay("","","","","","","","")
//            }
        }
    }


}