package com.example.asteroidradar.Network

import com.example.asteroidradar.dataClasses.DataClasses
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


     const val BASE_URL = "https://api.nasa.gov/"

    private val moshiJsonToKotlinObjectsConverter = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
     val asteroidsAndImgOftheDayRetrofit = Retrofit.Builder().client(NetworkUtils().okHttpClient())
         .addConverterFactory(ToStringConverterFactory())
         .addConverterFactory(MoshiConverterFactory.create(moshiJsonToKotlinObjectsConverter))
        .baseUrl(BASE_URL).build()

interface NasaAPIs {

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroidsList(@Query("start_date")startDate: String,
                                 @Query("end_date") endDate: String,
                                 @Query("api_key") API_KEY: String): String

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(@Query("api_key") API_KEY: String): DataClasses.PictureOfTheDay
}

object NasaApiServices {
    val asteroidsServiceCall: NasaAPIs by lazy { asteroidsAndImgOftheDayRetrofit.create(NasaAPIs::class.java) }
}