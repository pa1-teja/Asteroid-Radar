package com.example.asteroidradar.database.Entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.asteroidradar.dataClasses.DataClasses

@Entity(tableName = "close_approach_data", indices = arrayOf(Index("close_approach_id", unique = true),
    Index("relative_velocity_id", unique = true), Index("miss_distance_id", unique = true)
) ,primaryKeys = arrayOf("relative_velocity_id", "miss_distance_id") ,foreignKeys = arrayOf(ForeignKey(entity = nearEarthAsteroidsEntity::class,
    parentColumns = arrayOf("close_approach_data_id"), childColumns = arrayOf("close_approach_id"), onDelete = CASCADE)))
data class CloseApproachDataEntity(

    @ColumnInfo(name = "close_approach_id")
    val closeApproachDataID:String,

    @ColumnInfo(name = "close_approach_date")
    val close_approach_date : String,

    @ColumnInfo(name = "close_approach_date_full")
    val close_approach_date_full: String,

    @ColumnInfo(name = "epoch_date_close_approach")
    val epoch_date_close_approach: Double,

    @ColumnInfo(name = "relative_velocity_id")
    val relative_velocityID: String,

    @ColumnInfo(name = "miss_distance_id")
    val miss_distance: String,

    @ColumnInfo(name = "orbiting_body")
    val orbiting_body: String
)
