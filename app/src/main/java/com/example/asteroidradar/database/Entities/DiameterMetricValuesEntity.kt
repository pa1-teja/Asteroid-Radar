package com.example.asteroidradar.database.Entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE


@Entity(tableName = "diameter_values", indices = arrayOf(Index("metric", unique = true)) ,
    foreignKeys = arrayOf(ForeignKey(entity = estimatedDiameterEntity::class, parentColumns = arrayOf("metric"),
                                     childColumns = arrayOf("metric"), onDelete = CASCADE)))
data class DiameterMetricValuesEntity(

    @PrimaryKey
    @ColumnInfo(name = "metric")
    val metric: String,

    @ColumnInfo(name = "estimated_diameter_min")
    val estimated_diameter_min: Double,

    @ColumnInfo(name = "estimated_diameter_max")
    val estimated_diameter_max: Double
)
