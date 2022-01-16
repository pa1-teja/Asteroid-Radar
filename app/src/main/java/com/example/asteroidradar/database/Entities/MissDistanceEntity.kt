package com.example.asteroidradar.database.Entities

import androidx.room.*

@Entity(tableName = "miss_distance", indices = arrayOf(Index("id", unique = true)) ,foreignKeys = arrayOf(ForeignKey(entity = CloseApproachDataEntity::class,
    parentColumns = arrayOf("miss_distance_id"), childColumns = arrayOf("id"))))
data class MissDistanceEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "astronomical")
    val astronomical: String,

    @ColumnInfo(name = "lunar")
    val lunar: String,

    @ColumnInfo(name = "kilometers")
    val kilometers: String,

    @ColumnInfo(name = "miles")
    val miles: String
)
