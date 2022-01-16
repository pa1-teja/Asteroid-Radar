package com.example.asteroidradar.database.Entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "relative_velocity", indices = arrayOf(Index("id", unique = true)) ,
    foreignKeys = arrayOf(ForeignKey(entity = CloseApproachDataEntity::class, parentColumns = arrayOf("relative_velocity_id"),
        childColumns = arrayOf("id"), onDelete = CASCADE)))
data class RelativeVelocityEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "kilometers_per_second")
    val kilometers_per_second: String,

    @ColumnInfo(name = "kilometers_per_hour")
    val kilometers_per_hour: String,

    @ColumnInfo(name = "miles_per_hour")
    val miles_per_hour: String
)
