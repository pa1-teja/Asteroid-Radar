package com.example.asteroidradar.database.daos


import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import com.example.asteroidradar.dataClasses.DataClasses
import com.example.asteroidradar.database.entities.AsteroidsDataTable
import com.example.asteroidradar.database.entities.CloseApproachDataTable
import com.example.asteroidradar.database.entities.MasterTable
import kotlinx.coroutines.flow.Flow
import timber.log.Timber


@Dao
interface NearEarthAsteroidsDAO {

    @Insert
    fun insertNearEarthAsteroids(asteroidsDataTable: AsteroidsDataTable)

    @Insert(onConflict = IGNORE)
    fun insertCloseApproachInfo(closeApproachDataTable: CloseApproachDataTable)

    @Insert(onConflict = IGNORE)
    fun insertDatesInMasterTable(masterTable: MasterTable)

    @Transaction
    fun insertAsteroidInfo(asteroids: ArrayList<DataClasses.Asteroid>){
        Timber.d("Insert Transaction begun")

        for (asteroid in asteroids) {

            insertDatesInMasterTable(MasterTable(date = asteroid.date))

            val masterTableId = getMasterTableAutoGeneratedDateId(asteroid.date)

            val asteroidDataTable = AsteroidsDataTable(
                id = asteroid.id,
                neoReferenceId = asteroid.neoReferenceId,
                name = asteroid.name,
                nasaJplUrl = asteroid.nasaJplUrl,
                absoluteMagnitude = asteroid.absoluteMagnitudeH,
                isPotentiallyHazardous = asteroid.isPotentiallyHazardousAsteroid,
                isSentryObject = asteroid.isSentryObject,
                selfLink = asteroid.links.self,
                estimatedDiameterFeetMax = asteroid.estimatedDiameter.feet.estimatedDiameterMaximum,
                estimatedDiameterFeetMin = asteroid.estimatedDiameter.feet.estimatedDiameterMinimum,
                estimatedDiameterKiloMetersMax = asteroid.estimatedDiameter.kilometers.estimatedDiameterMaximum,
                estimatedDiameterKiloMetersMin = asteroid.estimatedDiameter.kilometers.estimatedDiameterMinimum,
                estimatedDiameterMetersMax = asteroid.estimatedDiameter.meters.estimatedDiameterMaximum,
                estimatedDiameterMetersMin = asteroid.estimatedDiameter.meters.estimatedDiameterMinimum,
                estimatedDiameterMilesMax = asteroid.estimatedDiameter.miles.estimatedDiameterMaximum,
                estimatedDiameterMilesMin = asteroid.estimatedDiameter.miles.estimatedDiameterMinimum,
                masterId = masterTableId)

         insertNearEarthAsteroids(asteroidDataTable)
            val closeApproachDataTable = CloseApproachDataTable(
                asteroidTableId = asteroid.id,
                closeApproachDate = asteroid.closeApproachData[asteroid.closeApproachData.size-1].closeApproachDate,
                closeApproachDateFull = asteroid.closeApproachData[asteroid.closeApproachData.size-1].closeApproachDateFull,
                epochDateCloseApproach = asteroid.closeApproachData[asteroid.closeApproachData.size-1].epochDateCloseApproach,
                relativeVelocityKmph = asteroid.closeApproachData[asteroid.closeApproachData.size-1].relativeVelocity.kilometersPerHour,
                relativeVelocityMph = asteroid.closeApproachData[asteroid.closeApproachData.size-1].relativeVelocity.milesPerHour,
                relativeVelocityKmps = asteroid.closeApproachData[asteroid.closeApproachData.size-1].relativeVelocity.kilometersPerSecond,
                missDistanceAstronomical = asteroid.closeApproachData[asteroid.closeApproachData.size-1].missDistance.astronomical,
                missDistanceKilometers = asteroid.closeApproachData[asteroid.closeApproachData.size-1].missDistance.kilometers,
                missDistanceLunar = asteroid.closeApproachData[asteroid.closeApproachData.size-1].missDistance.lunar,
                missDistanceMiles = asteroid.closeApproachData[asteroid.closeApproachData.size-1].missDistance.miles,
                closeApproachId = masterTableId, id = null)

            insertCloseApproachInfo(closeApproachDataTable)
        }
        Timber.d("Insert Transaction Complete")
    }

    @Query("SELECT master_id FROM Asteroids_MasterTable WHERE date = :date")
     fun getMasterTableAutoGeneratedDateId(date: String): Int

     @Query("SELECT COUNT(*) FROM Asteroids_MasterTable")
     fun areRecordsAdded(): Int

    @Query("DELETE FROM Asteroids_MasterTable")
    fun deleteAllAsteroidsInfo()

    @Query("DELETE FROM Asteroids_MasterTable WHERE date = :date")
    fun deleteSpecificDayAsteroidInfo(date: String)

     @Query("SELECT ast.master_table_id AS masterTableId,ast.id AS asteroidTableId,ast.name AS asteroidName,mt.date AS date, ast.is_potentially_hazardous_asteroid AS isDangerous FROM ASTEROIDS AS ast INNER JOIN Asteroids_MasterTable AS mt ON mt.master_id = ast.master_table_id")
     fun getAsteroidsListData(): Flow<List<DataClasses.Asteroids>>

     @Query("SELECT cad.close_approach_date AS closeApproachDate, ast.absolute_magnitude_h AS absoluteMagnitude, ast.estimated_diameter_Kms_max AS estimatedDiameterKmsMax, cad.relative_velocity_kilometers_per_second AS relativeVelocityKmps, cad.miss_distance_astronomical AS missDistanceAstronomical, ast.is_potentially_hazardous_asteroid AS isDangerous FROM Asteroids AS ast INNER JOIN close_approach_data AS cad ON ast.id = cad.asteroid_table_id WHERE ast.id = :asteroidTableId")
     fun getAllAsteroidInfo(asteroidTableId: Long): DataClasses.AsteroidDetails

    @Query("SELECT ast.master_table_id AS masterTableId,ast.id AS asteroidTableId,ast.name AS asteroidName,mt.date AS date, ast.is_potentially_hazardous_asteroid AS isDangerous FROM ASTEROIDS AS ast INNER JOIN Asteroids_MasterTable AS mt ON mt.master_id = ast.master_table_id WHERE mt.date =:date")
    fun getTodayAsteroidsListData(date: String): Flow<List<DataClasses.Asteroids>>

    @Query("SELECT ast.master_table_id AS masterTableId,ast.id AS asteroidTableId,ast.name AS asteroidName,mt.date AS date, ast.is_potentially_hazardous_asteroid AS isDangerous FROM ASTEROIDS AS ast INNER JOIN Asteroids_MasterTable AS mt ON mt.master_id = ast.master_table_id WHERE mt.date BETWEEN :startDate AND :endDate")
    fun getWeekAsteroidsListData(startDate: String,endDate: String): Flow<List<DataClasses.Asteroids>>
}