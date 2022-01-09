package com.example.asteroidradar.Network

import android.content.Context
import com.example.asteroidradar.DataClasses.DataClasses
import com.example.asteroidradar.R
import com.example.asteroidradar.Utils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.connection.ConnectInterceptor.intercept
import okio.IOException
import org.json.JSONObject
import java.util.concurrent.TimeUnit


class NetworkUtils {

    suspend fun fetchNearEarthAsteroids(context: Context): ArrayList<DataClasses.Asteroid> {
        val nextSevenDaysFormatted = Utils().getNextSevenDaysFormatted(context)
        val json = NasaApiServices.asteroidsListService.getAsteroidsList(
                startDate = nextSevenDaysFormatted.get(0),
                endDate = nextSevenDaysFormatted.get(nextSevenDaysFormatted.lastIndex),
                API_KEY = context.getString(R.string.API_KEY)
            )

        return filterNearEarthAsteroids(json,nextSevenDaysFormatted)
    }

    private fun filterNearEarthAsteroids(json: String, nextSevenDaysFormatted: ArrayList<String>, ):ArrayList<DataClasses.Asteroid>{
        val nearEarthObjects = ArrayList<DataClasses.Asteroid>()

        val jsonObject= JSONObject(json).getJSONObject("near_earth_objects")

        for (formattedDate in nextSevenDaysFormatted) {
            if (jsonObject.has(formattedDate)) {
                val dateAsteroidJsonArray = jsonObject.getJSONArray(formattedDate)

                for (i in 0 until dateAsteroidJsonArray.length()) {
                    val obj = dateAsteroidJsonArray.getJSONObject(i)

                    val estimatedDiameter = obj.getJSONObject("estimated_diameter")
                    val kms = estimatedDiameter.getJSONObject("kilometers")
                    val mts = estimatedDiameter.getJSONObject("meters")
                    val miles = estimatedDiameter.getJSONObject("miles")
                    val feet = estimatedDiameter.getJSONObject("feet")

                    val closeAproachArray = obj.getJSONArray("close_approach_data")
                    val closeApproachList = ArrayList<DataClasses.CloseApproachData>()
                    for (j in 0 until closeAproachArray.length()) {
                        val close = closeAproachArray.getJSONObject(j)

                        val relativeVelocity = close.getJSONObject("relative_velocity")
                        val missDistance = close.getJSONObject("miss_distance")
                        closeApproachList.add(
                            DataClasses.CloseApproachData(
                                close.getString("close_approach_date"),
                                close.getString("close_approach_date_full"),
                                close.getLong("epoch_date_close_approach"),
                                close.getString("orbiting_body"),
                                DataClasses.RelativeVelocity(
                                    relativeVelocity.getString("kilometers_per_second"),
                                    relativeVelocity.getString("kilometers_per_hour"),
                                    relativeVelocity.getString("miles_per_hour")
                                ),
                                DataClasses.MissDistance(
                                    missDistance.getString("astronomical"),
                                    missDistance.getString("lunar"),
                                    missDistance.getString("kilometers"),
                                    missDistance.getString("miles")
                                )
                            )
                        )
                    }

                    var asteroid = DataClasses.Asteroid(
                        obj.get("id").toString(),
                        obj.get("name").toString(),
                        DataClasses.Links(obj.get("links").toString()),
                        DataClasses.EstimatedDiameter(
                            DataClasses.Dimentions(
                                kms.getDouble("estimated_diameter_min"),
                                kms.getDouble("estimated_diameter_max")
                            ),
                            DataClasses.Dimentions(
                                mts.getDouble("estimated_diameter_min"),
                                mts.getDouble("estimated_diameter_max")
                            ),
                            DataClasses.Dimentions(
                                miles.getDouble("estimated_diameter_min"),
                                miles.getDouble("estimated_diameter_max")
                            ),
                            DataClasses.Dimentions(
                                feet.getDouble("estimated_diameter_min"),
                                feet.getDouble("estimated_diameter_max")
                            ),
                        ),
                        obj.getDouble("absolute_magnitude_h"),
                        obj.get("is_potentially_hazardous_asteroid") as Boolean,
                        obj.get("is_sentry_object") as Boolean,
                        obj.get("nasa_jpl_url") as String,
                        obj.get("neo_reference_id") as String,
                        closeApproachList
                    )
                    nearEarthObjects.add(asteroid)
                }
            }
        }

        return nearEarthObjects
    }


     suspend fun fetchPictureOfTheDay(context: Context): DataClasses.PictureOfTheDay{
         val response:String = NasaApiServices.pictureOfTheDay.getPictureOfTheDay(context.getString(R.string.API_KEY))
         val json = JSONObject(response)
         return  DataClasses.PictureOfTheDay(
             json.getString("date"),
             json.getString("explanation"),
             json.getString("hdurl"),
             json.getString("media_type"),
             json.getString("service_version"),
             json.getString("title"),
             json.getString("url")
         )
     }

    fun OkHttpClientWithTimeOut(): OkHttpClient {
        val client = OkHttpClient()
        client.newBuilder().addInterceptor {
            it.proceed(
                it.request().newBuilder().method(it.request().method, it.request().body).build()
            )
        }
            .callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60,TimeUnit.SECONDS)
            .connectTimeout(60,TimeUnit.SECONDS).build()
        return client
    }

}