package com.example.asteroidradar.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "asteroids", foreignKeys = [ForeignKey(entity = MasterTable::class,
    parentColumns = arrayOf("master_id"), childColumns = arrayOf("master_table_id"), onDelete = CASCADE)]
)
data class AsteroidsDataTable(

    @ColumnInfo(name = "master_table_id")
    val masterId: Int,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id:String,

    @ColumnInfo(name = "neo_reference_id")
    val neoReferenceId:String,

    @ColumnInfo(name = "name")
    val name:String,

    @ColumnInfo(name = "nasa_jpl_url")
    val nasaJplUrl:String,

    @ColumnInfo(name = "absolute_magnitude_h")
    val absoluteMagnitude: Double,

    @ColumnInfo(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardous: Boolean,

    @ColumnInfo(name = "is_sentry_object")
    val isSentryObject: Boolean,

    @ColumnInfo(name = "self_link")
    val selfLink: String,

    @ColumnInfo(name = "estimated_diameter_Kms_min")
    val estimatedDiameterKiloMetersMin: Double,

    @ColumnInfo(name = "estimated_diameter_Kms_max")
    val estimatedDiameterKiloMetersMax: Double,

    @ColumnInfo(name = "estimated_diameter_Mts_min")
    val estimatedDiameterMetersMin: Double,

    @ColumnInfo(name = "estimated_diameter_Mts_max")
    val estimatedDiameterMetersMax: Double,

    @ColumnInfo(name = "estimated_diameter_Miles_min")
    val estimatedDiameterMilesMin: Double,

    @ColumnInfo(name = "estimated_diameter_Miles_max")
    val estimatedDiameterMilesMax: Double,

    @ColumnInfo(name = "estimated_diameter_Feet_min")
    val estimatedDiameterFeetMin: Double,

    @ColumnInfo(name = "estimated_diameter_Feet_max")
    val estimatedDiameterFeetMax: Double,
)
