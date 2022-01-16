package com.example.asteroidradar.database.Entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE


@Entity(tableName = "estimated_diameter", primaryKeys = arrayOf("metric") ,foreignKeys = arrayOf(ForeignKey(entity = nearEarthAsteroidsEntity::class,
    parentColumns = arrayOf("estimated_diameter_id"), childColumns = arrayOf("estimatedDiameterId"), onDelete = CASCADE)), indices = arrayOf(
    Index("estimatedDiameterId", unique = true)
))
data class estimatedDiameterEntity(
    @ColumnInfo(name = "estimatedDiameterId")
    val estimatedDiameterId: Long,

    @ColumnInfo(name = "metric")
    val metric: String
)
