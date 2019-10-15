package fr97.movieinfo.core.common

import android.app.Application
import android.util.Log
import androidx.work.*
import fr97.movieinfo.core.di.Injector
import fr97.movieinfo.feature.update.UpdateWorker
import java.util.concurrent.TimeUnit

class App : Application() {

    companion object {
        const val UPDATE_WORK_NAME = "UpdateWork"
    }

    override fun onCreate() {
        super.onCreate()
        // Only runs when device is charging and not being used
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .setRequiresDeviceIdle(true)
            .build()
//        val updateWork = PeriodicWorkRequestBuilder<UpdateWorker>(7, TimeUnit.DAYS).setConstraints(constraints).build()
//        WorkManager.getInstance(this)
//            .enqueueUniquePeriodicWork(UPDATE_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, updateWork)
//        Log.d("WORKER", "Starting work")
        WorkManager.getInstance(this).cancelAllWork()

//        val prefs = Injector.preferences(this);
//        prefs.putBoolean("firstStart", true).toBlocking().first()
//        val firstStart = prefs.getBoolean("firstStart", true).toBlocking().first()
//        if (firstStart) {
//            Log.d("WORKER", "Starting work")
//            val updateWork = OneTimeWorkRequestBuilder<UpdateWorker>().setConstraints(constraints).build()
//            WorkManager.getInstance(this).beginUniqueWork(UPDATE_WORK_NAME, ExistingWorkPolicy.KEEP, updateWork)
//                .enqueue()
//            prefs.putBoolean("firstStart", false)
//        } else {
//            val updateWork =
//                PeriodicWorkRequestBuilder<UpdateWorker>(7, TimeUnit.DAYS).setConstraints(constraints).build()
//            WorkManager.getInstance(this)
//                .enqueueUniquePeriodicWork(UPDATE_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, updateWork)
//            Log.d("WORKER", "Delaying work work")
//        }

    }

}