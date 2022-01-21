package com.example.asteroidradar.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Asteroids_MasterTable", indices = [Index("date", unique = true)])
data class MasterTable(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "master_id")
    val masterId: Int = 0,

    @ColumnInfo(name = "date")
    val date:String
)
