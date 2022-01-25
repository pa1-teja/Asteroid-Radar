package com.example.asteroidradar

import android.app.Application
import androidx.work.*
import com.example.asteroidradar.workManager.AsteroidsNASAAPIWork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class BaseApplication: Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        fetchInfoFromNASA()
    }

    private fun fetchInfoFromNASA(){
        applicationScope.launch {
            scheduleRecurringTask()
        }
    }

    private fun scheduleRecurringTask(){
//        val constraints = Constraints.Builder().setRequiresCharging(true)
//            .setRequiredNetworkType(NetworkType.UNMETERED).build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED).build()


        val repeatTaskAtIntervals = PeriodicWorkRequestBuilder<AsteroidsNASAAPIWork>(1,  TimeUnit.DAYS).setConstraints(constraints).build()


        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(AsteroidsNASAAPIWork.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP,repeatTaskAtIntervals)
    }

}