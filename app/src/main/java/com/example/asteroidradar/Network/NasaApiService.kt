package com.example.asteroidradar.Network

import com.example.asteroidradar.DataClasses.DataClasses
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


     const val BASE_URL = "https://api.nasa.gov/neo/rest/v1/feed/"
     const val PICTURE_OF_THE_DAY_BASE_URL = "https://api.nasa.gov/planetary/apod/"

    enum class filter()

    private val moshiJsonToKotlinObjectsConverter = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
     val asteroidListRetrofit = Retrofit.Builder()
         .addConverterFactory(ToStringConverterFactory())
        .baseUrl(BASE_URL).build()

    val imgOfTheDayRetrofit = Retrofit.Builder()
//        .addConverterFactory(ToStringConverterFactory())
        .addConverterFactory(MoshiConverterFactory.create(moshiJsonToKotlinObjectsConverter))
        .client(NetworkUtils().OkHttpClientWithTimeOut())
        .baseUrl(PICTURE_OF_THE_DAY_BASE_URL).build()

interface NasaAPIs {

    @GET(BASE_URL)
    suspend fun getAsteroidsList(@Query("start_date")startDate: String,
                                 @Query("end_date") endDate: String,
                                 @Query("api_key") API_KEY: String): String

    @GET(PICTURE_OF_THE_DAY_BASE_URL)
    suspend fun getPictureOfTheDay(@Query("api_key") API_KEY: String): String
}

object NasaApiServices {


    val asteroidsListService: NasaAPIs by lazy { asteroidListRetrofit.create(NasaAPIs::class.java) }
    val pictureOfTheDay: NasaAPIs by lazy { imgOfTheDayRetrofit.create(NasaAPIs::class.java) }
}