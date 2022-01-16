package com.example.asteroidradar.database.DAOs

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.asteroidradar.dataClasses.DataClasses
import timber.log.Timber


@Dao
interface NearEarthAsteroidsDAO {



    @Query("INSERT INTO near_earth_objects(id, date, name, linksId, estimated_diameter_id ,absolute_magnitude_h, is_potentially_hazardous_asteroid, is_sentry_object, nasa_jpl_url, neo_reference_id,close_approach_data_id) VALUES(:id,:date,:name,:id,:id,:aboluteMagnitude,:isPotentiallyHazardous,:isSentryObj,:nasaJplUrl,:neoReferenceId,:id) ")
    suspend fun insertNearEarthAsteroids(id:String, date: String, name: String ,aboluteMagnitude: Double, isPotentiallyHazardous: Boolean, isSentryObj: Boolean, nasaJplUrl: String, neoReferenceId: String)

    @Query("INSERT INTO asteroid_links(linksId,self) VALUES(:id,:link)")
    suspend fun insertSelfLink(id: String,link: String)

    @Query("INSERT INTO estimated_diameter(estimatedDiameterId,metric) VALUES(:id,:metric)")
    suspend fun insertEstimatedDiameterInfo(id: String,metric: String)

    @Query("INSERT INTO diameter_values(metric ,estimated_diameter_min, estimated_diameter_max) VALUES(:metric,:estimatedDiameterMin, :estimatedDiameterMax)")
    suspend fun insertDiameterValues(metric:String ,estimatedDiameterMin:Double, estimatedDiameterMax:Double)

    @Query("INSERT INTO close_approach_data (close_approach_id,close_approach_date, close_approach_date_full, epoch_date_close_approach,relative_velocity_id,miss_distance_id ,orbiting_body) VALUES(:id,:closeApproachDate, :closeApproachDateFull, :epoch,:id,:id,:orbitingBody)")
    suspend fun insertCloseApproachInfo(id: String,closeApproachDate:String, closeApproachDateFull:String, epoch:Long, orbitingBody:String)

    @Query("INSERT INTO relative_velocity (id,kilometers_per_second, kilometers_per_hour, miles_per_hour) VALUES(:id,:kmps,:kmph,:mph)")
    suspend fun insertRelativeVelocityInfo(id: String,kmps:String, kmph: String, mph:String)

    @Query("INSERT INTO miss_distance (id,astronomical, lunar, kilometers, miles) VALUES(:id,:astronomical,:lunar,:kilometers,:miles)")
    suspend fun insertMissDistance(id: String,astronomical:String, lunar:String, kilometers:String, miles:String)


    @Transaction
    suspend fun insertAsteroidInfo(asteroids:ArrayList<DataClasses.Asteroid>){
        Timber.d("Insert Transaction begun")
        for (asteroid in asteroids) {
           insertNearEarthAsteroids(
                asteroid.id,
                asteroid.date,
                asteroid.name,
                asteroid.absoluteMagnitudeH,
                asteroid.isPotentiallyHazardousAsteroid,
                asteroid.isSentryObject,
                asteroid.nasaJplUrl,
                asteroid.neoReferenceId
            )
            insertSelfLink(asteroid.id,asteroid.links.self)
            asteroid.estimatedDiameter.keys.forEach {
                insertEstimatedDiameterInfo(asteroid.id,it)
                when (it) {
                    "kilometers" -> insertDiameterValues(
                        it,
                        asteroid.estimatedDiameter.kilometers.estimatedDiameterMinimum,
                        asteroid.estimatedDiameter.kilometers.estimatedDiameterMinimum
                    )
                    "meters" -> insertDiameterValues(
                        it,
                        asteroid.estimatedDiameter.meters.estimatedDiameterMinimum,
                        asteroid.estimatedDiameter.meters.estimatedDiameterMinimum
                    )
                    "miles" -> insertDiameterValues(
                        it,
                        asteroid.estimatedDiameter.miles.estimatedDiameterMinimum,
                        asteroid.estimatedDiameter.miles.estimatedDiameterMinimum
                    )
                    "feet" -> insertDiameterValues(
                        it,
                        asteroid.estimatedDiameter.feet.estimatedDiameterMinimum,
                        asteroid.estimatedDiameter.feet.estimatedDiameterMinimum
                    )
                }
            }
            val close = asteroid.closeApproachData.get(asteroid.closeApproachData.size - 1)
            insertCloseApproachInfo(asteroid.id,
                close.closeApproachDate,
                close.closeApproachDateFull,
                close.epochDateCloseApproach,
                close.orbitingBody
            )
            insertRelativeVelocityInfo(
                asteroid.id,
                close.relativeVelocity.kilometersPerSecond,
                close.relativeVelocity.kilometersPerHour,
                close.relativeVelocity.milesPerHour
            )
            insertMissDistance(
                asteroid.id,
                close.missDistance.astronomical,
                close.missDistance.lunar,
                close.missDistance.kilometers,
                close.missDistance.miles
            )
        }
        Timber.d("Insert Transaction Complete")
    }


//    @Delete
//    suspend fun deleteAllAsteroidsInfo()
//
//    @Delete
//    suspend fun deleteSpecificDayAsteroidInfo(date: String)
//
//    @Query("SELECT * FROM near_earth_objects WHERE date = :date")
//    suspend fun getSpecificDayAsteroidInfo(date: String): DataClasses.Asteroid
//
//    @Query("SELECT * FROM near_earth_objects")
//    suspend fun getAllAsteroidsInfo():DataClasses.Asteroid
//
//    @Query("SELECT * FROM near_earth_objects AS neo INNER JOIN asteroid_links AS al ON neo.linksId = al.linksId INNER JOIN estimated_diameter AS ed ON neo.estimated_diameter_id = ed.estimatedDiameterId INNER JOIN diameter_values AS dv ON ed.metric = dv.metric INNER JOIN close_approach_data AS cad ON neo.close_approach_data_id = cad.close_approach_id INNER JOIN relative_velocity AS rv ON cad.relative_velocity_id = rv.id INNER JOIN miss_distance AS md ON cad.miss_distance_id = md.id  WHERE neo.date = :date")
//    suspend fun getAsteroidInfoForSpecificDate(date: String): DataClasses.CloseApproachData
//
//    @Query("SELECT * FROM near_earth_objects AS neo INNER JOIN asteroid_links AS al ON neo.linksId = al.linksId INNER JOIN estimated_diameter AS ed ON neo.estimated_diameter_id = ed.estimatedDiameterId INNER JOIN diameter_values AS dv ON ed.metric = dv.metric INNER JOIN close_approach_data AS cad ON neo.close_approach_data_id = cad.close_approach_id INNER JOIN relative_velocity AS rv ON cad.relative_velocity_id = rv.id INNER JOIN miss_distance AS md ON cad.miss_distance_id = md.id")
//    suspend fun getAllAsteroidInfo()
}