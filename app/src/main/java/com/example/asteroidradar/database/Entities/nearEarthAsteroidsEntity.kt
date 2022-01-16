package com.example.asteroidradar.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "near_earth_objects",primaryKeys = ["id","linksId","estimated_diameter_id","close_approach_data_id"], indices = arrayOf(
    Index("linksId", unique = true), Index("estimated_diameter_id", unique = true), Index("close_approach_data_id", unique = true)
))
data class nearEarthAsteroidsEntity(

    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "linksId")
    val linksId: String,

    @ColumnInfo(name = "estimated_diameter_id")
    val estimatedDiameterId: String,

    @ColumnInfo(name = "absolute_magnitude_h")
    val absoluteMagnitudeH: Double,

    @ColumnInfo(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid : Boolean,


    @ColumnInfo(name = "is_sentry_object")
    val isSentryObject: Boolean,


    @ColumnInfo(name = "nasa_jpl_url")
    val nasaJplUrl: String,


    @ColumnInfo(name = "neo_reference_id")
    val neoReferenceId : String,


    @ColumnInfo(name = "close_approach_data_id")
    val closeApproachDataID: String
)
