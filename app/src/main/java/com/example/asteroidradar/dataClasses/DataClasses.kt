package com.example.asteroidradar.dataClasses

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

class DataClasses {

    @Parcelize
    data class PictureOfTheDay(
        @Json(name = "date")
        val date: String,

        @Json(name = "explanation")
        val explanation: String,

        @Json(name = "hdurl")
        val hdurl: String,

        @Json(name = "media_type")
        val media_type: String,

        @Json(name = "service_version")
        val service_version: String,

        @Json(name = "title")
        val title: String,

        @Json(name = "url")
        val url: String
    ): Parcelable


    @Parcelize
    data class Asteroid(

        @Json(name = "id")
        val id: String,

        val date: String,

        @Json(name = "name")
        val name: String,

        @Json(name = "links")
        val links: Links,

        @Json(name = "estimated_diameter")
        val estimatedDiameter: EstimatedDiameter,

        @Json(name = "absolute_magnitude_h")
        val absoluteMagnitudeH: Double,

        @Json(name = "is_potentially_hazardous_asteroid")
        val isPotentiallyHazardousAsteroid : Boolean,

        @Json(name = "is_sentry_object")
        val isSentryObject: Boolean,

        @Json(name = "nasa_jpl_url")
        val nasaJplUrl: String,

        @Json(name = "neo_reference_id")
        val neoReferenceId : String,

        @Json(name = "close_approach_data")
        val closeApproachData: List<CloseApproachData>

    ):Parcelable

    @Parcelize
    data class Links(val self:String): Parcelable

    @Parcelize
    data class EstimatedDiameter(


        val keys: @RawValue MutableIterator<String>,

        @Json(name = "kilometers")
        val kilometers: Dimentions,

        @Json(name = "meters")
        val meters: Dimentions,

        @Json(name = "miles")
        val miles: Dimentions,

        @Json(name = "feet")
        val feet: Dimentions

    ): Parcelable

    @Parcelize
    data class Dimentions(
        @Json(name = "estimated_diameter_min")
        val estimatedDiameterMinimum: Double,

        @Json(name = "estimated_diameter_max")
        val estimatedDiameterMaximum: Double
    ): Parcelable


    @Parcelize
    data class CloseApproachData(
        @Json(name = "close_approach_date")
        val closeApproachDate: String,

        @Json(name = "close_approach_date_full")
        val closeApproachDateFull: String,

        @Json(name = "epoch_date_close_approach")
        val epochDateCloseApproach: Long,

        @Json(name = "orbiting_body")
        val orbitingBody: String,

        @Json(name = "relative_velocity")
        val relativeVelocity: RelativeVelocity,

        @Json(name = "miss_distance")
        val missDistance: MissDistance,
    ): Parcelable

    @Parcelize
    data class RelativeVelocity(
        @Json(name = "kilometers_per_second")
        val kilometersPerSecond: String,

        @Json(name = "kilometers_per_hour")
        val kilometersPerHour: String,

        @Json(name = "miles_per_hour")
        val milesPerHour: String
    ): Parcelable


    @Parcelize
    data class MissDistance(
        @Json(name = "astronomical")
        val astronomical: String,

        @Json(name = "lunar")
        val lunar: String,

        @Json(name = "kilometers")
        val kilometers: String,

        @Json(name = "miles")
        val miles: String
    ): Parcelable

    @Parcelize
    data class asteroids(
        @ColumnInfo(name = "master_table_id")
        val masterTableId: Int,

        @ColumnInfo(name = "id")
        val asteroidTableId: Long,

        @ColumnInfo(name = "name")
        val asteroidName: String,

         @ColumnInfo(name = "date")
         val date: String,

         @ColumnInfo(name = "is_potentially_hazardous_asteroid")
         val isDangerous: Boolean
    ): Parcelable

    @Parcelize
    data class asteroidDetails(
        val closeApproachDate: String,
        val absoluteMagnitude: Double,
        val estimatedDiameterKmsMax: String,
        val relativeVelocityKmps: String,
        val missDistanceAstronomical: String
    ): Parcelable
}