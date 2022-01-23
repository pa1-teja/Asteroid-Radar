package com.example.asteroidradar.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroidradar.Network.NetworkUtils
import com.example.asteroidradar.Utils
import com.example.asteroidradar.database.AsteroidRadarDatabase
import retrofit2.HttpException
import timber.log.Timber

class AsteroidsNASAAPIWork(appContext: Context, workerParameters: WorkerParameters): CoroutineWorker(appContext,workerParameters) {

    companion object{
        const val WORK_NAME = "FetchInfoFromNASA"

    }

    override suspend fun doWork(): Result {
        val database = AsteroidRadarDatabase.getDatabaseInstance(applicationContext)
        val networkUtils = NetworkUtils()
        val utils = Utils()
        val nextSevenDays = utils.getNextSevenDaysFormatted(applicationContext)

        return try{
            if (database.nearEarthAsteroidsDAO.areRecordsAdded() > 1){
                val yesterday = utils.getYesterdayDate(applicationContext)
                database.nearEarthAsteroidsDAO.deleteSpecificDayAsteroidInfo(yesterday)
                networkUtils.fetchNearEarthAsteroids(applicationContext, arrayListOf(nextSevenDays.get(nextSevenDays.lastIndex)),database)
            }else{
                networkUtils.fetchNearEarthAsteroids(applicationContext,nextSevenDays,database)
            }
            networkUtils.fetchPictureOfTheDayAndStoreLocally(applicationContext,database)
            Result.success()
        }catch (ex: HttpException){
            Timber.e(ex.message())
            Result.retry()
        }
    }

}