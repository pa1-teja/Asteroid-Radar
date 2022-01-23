package com.example.asteroidradar.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "close_approach_data",
    foreignKeys = [ForeignKey(entity = AsteroidsDataTable::class, parentColumns = arrayOf("id"),
        childColumns = arrayOf("asteroid_table_id"), onDelete = CASCADE)])
data class CloseApproachDataTable(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,

    @ColumnInfo(name = "asteroid_table_id")
    val asteroidTableId: String,

    @ColumnInfo(name = "close_approach_id")
    val closeApproachId: Int,

    @ColumnInfo(name = "close_approach_date")
    val closeApproachDate: String,

    @ColumnInfo(name = "close_approach_date_full")
    val closeApproachDateFull: String,

    @ColumnInfo(name = "epoch_date_close_approach")
    val epochDateCloseApproach: Long,

    @ColumnInfo(name = "relative_velocity_kilometers_per_second")
    val relativeVelocityKmps:String,

    @ColumnInfo(name = "relative_velocity_kilometers_per_hour")
    val relativeVelocityKmph:String,

    @ColumnInfo(name = "relative_velocity_miles_per_hour")
    val relativeVelocityMph: String,

    @ColumnInfo(name = "miss_distance_astronomical")
    val missDistanceAstronomical: String,

    @ColumnInfo(name = "miss_distance_lunar")
    val missDistanceLunar: String,

    @ColumnInfo(name = "miss_distance_kilometers")
    val missDistanceKilometers: String,

    @ColumnInfo(name = "miss_distance_miles")
    val missDistanceMiles: String
)
