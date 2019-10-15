package fr97.movieinfo.feature.update
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Color.RED
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.AudioAttributes.USAGE_NOTIFICATION_RINGTONE
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.media.RingtoneManager.getDefaultUri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_ALL
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import fr97.movieinfo.R
import fr97.movieinfo.core.di.Injector
import fr97.movieinfo.core.extension.vectorToBitmap
import fr97.movieinfo.data.api.Client
import fr97.movieinfo.data.api.MovieApi
import fr97.movieinfo.data.api.MovieResponse
import fr97.movieinfo.data.entity.MovieEntity
import fr97.movieinfo.feature.main.MainActivity
import kotlinx.coroutines.delay
import retrofit2.awaitResponse

class UpdateWorker(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        Log.d("WORKER", "doWork is called")
        (1..10).asIterable().map {// Query first 100 pages
            loadData("popular", it)
            loadData("top_rated", it)
            loadData("upcoming", it)
            delay(30)
        }
        sendNotification(1)
        return Result.success()
    }

    private suspend fun loadData(filter: String, page: Int) {
        val movieRepository = Injector.movieRepository(applicationContext)
        val movieApi = Client.retrofitClient.create(MovieApi::class.java)
        val response = movieApi.getMovies(filter, page).awaitResponse()
        if (response.isSuccessful) {
            val movies = response.body()?.let { res ->
                res.movies.map { MovieEntity(it.id, it.title, it.thumbnailPath ?: "", it.releaseDate, filter) }
            } ?: emptyList()
            movieRepository.saveAll(movies)
        }
    }

    private fun sendNotification(id: Int) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NOTIFICATION_ID, id)

        val notificationManager =
            applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val bitmap = applicationContext.vectorToBitmap(R.drawable.ic_schedule_black)
        val titleNotification = applicationContext.getString(R.string.notification_title)
        val subtitleNotification = applicationContext.getString(R.string.notification_subtitle)
        val pendingIntent = getActivity(applicationContext, 0, intent, 0)
        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
            .setLargeIcon(bitmap).setSmallIcon(R.drawable.ic_schedule_white)
            .setContentTitle(titleNotification).setContentText(subtitleNotification)
            .setDefaults(DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true)

        notification.priority = PRIORITY_MAX

        if (SDK_INT >= O) {
            notification.setChannelId(NOTIFICATION_CHANNEL)

            val ringtoneManager = getDefaultUri(TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder().setUsage(USAGE_NOTIFICATION_RINGTONE)
                .setContentType(CONTENT_TYPE_SONIFICATION).build()

            val channel =
                NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_NAME, IMPORTANCE_HIGH)

            channel.enableLights(true)
            channel.lightColor = RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            channel.setSound(ringtoneManager, audioAttributes)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, notification.build())
    }


    companion object {
        const val NOTIFICATION_ID = "MovieInfo_notification_01"
        const val NOTIFICATION_NAME = "MovieInfo"
        const val NOTIFICATION_CHANNEL = "MovieInfo__channel_01"
        const val NOTIFICATION_WORK = "MovieInfo_notification_work"
    }


}