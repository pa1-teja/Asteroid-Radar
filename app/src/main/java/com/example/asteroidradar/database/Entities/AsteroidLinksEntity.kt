package com.example.asteroidradar.database.Entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE


@Entity(tableName = "asteroid_links", indices = arrayOf(Index("linksId", unique = true)) ,foreignKeys = arrayOf(ForeignKey(entity = nearEarthAsteroidsEntity::class,
                                                            parentColumns = arrayOf("linksId"),
                                                             childColumns = arrayOf("linksId"), onDelete = CASCADE)))
data class AsteroidLinksEntity(
    @PrimaryKey
    @ColumnInfo(name = "linksId")
    val linksId: Long,

    @ColumnInfo(name = "self")
    val self: String
)
