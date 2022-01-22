package com.example.asteroidradar.dataAdapters

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.R
import com.example.asteroidradar.dataClasses.DataClasses
import com.squareup.picasso.Picasso


@BindingAdapter("asteroidListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DataClasses.asteroids>?) {
    val adapter = recyclerView.adapter as AsteroidsListAdapter
    adapter.submitList(data)
}

@BindingAdapter("isAsteroidDangerous")
fun bindAsteroidListItemImage(imageView: AppCompatImageView, data: Boolean) {
    imageView.setImageResource(if (data) R.drawable.ic_status_potentially_hazardous else R.drawable.ic_status_normal)
    imageView.contentDescription = if (data) "The asteroid is Potentially Hazardous" else "The asteroid is not Potentially Hazardous"
}

@BindingAdapter("nearApproachDate")
fun bindListItemNearApproachDate(textView: AppCompatTextView, data: DataClasses.CloseApproachData) {
    textView.setText(data.closeApproachDate)
    textView.contentDescription = data.closeApproachDate
}

@BindingAdapter("asteroidName")
fun bindListItemAsteroidName(textView: AppCompatTextView, data: DataClasses.Asteroid) {
    textView.setText(data.name)
    textView.contentDescription = data.name
}


/*
 * Below adapters are related to AsteroidDetailFragment
 */

@BindingAdapter("closeApproachDate")
fun bindCloseApproachDate(textView: AppCompatTextView, data: DataClasses.CloseApproachData) {
    textView.text = data.closeApproachDate
    textView.contentDescription = data.closeApproachDate
}

@BindingAdapter("magnitude")
fun bindCloseApproachAsteroidMagnitude(textView: AppCompatTextView, data: DataClasses.Asteroid) {
    textView.text = data.absoluteMagnitudeH.toString()
    textView.contentDescription = data.absoluteMagnitudeH.toString()
}

@BindingAdapter("estimatedDiameter")
fun bindEstimatedDiameter(textView: AppCompatTextView, data:DataClasses.Asteroid){
    textView.text = data.estimatedDiameter.kilometers.estimatedDiameterMaximum.toString() + "kms"
    textView.contentDescription = data.estimatedDiameter.kilometers.estimatedDiameterMaximum.toString() + "kms"
}

@BindingAdapter("relativeVelocity")
fun bindEstimatedVelocity(textView: AppCompatTextView, data: DataClasses.CloseApproachData){
    textView.text = data.relativeVelocity.kilometersPerSecond
    textView.contentDescription = data.relativeVelocity.kilometersPerSecond
}

@BindingAdapter("distanceFromEarth")
fun bindDistanceFromEarth(textView: AppCompatTextView, data: DataClasses.CloseApproachData){
    textView.text = data.missDistance.astronomical
    textView.contentDescription = data.missDistance.astronomical
}

@BindingAdapter("asteroidImage")
fun bindHazardousAsteroidImage(imageView: AppCompatImageView, isPotentiallyDangerous: Boolean){
    val img = if (isPotentiallyDangerous) R.drawable.asteroid_hazardous else R.drawable.asteroid_safe
    imageView.contentDescription = if (isPotentiallyDangerous) "The asteroid is Potentially Hazardous" else "The asteroid is not Potentially Hazardous"
    Picasso.get().load(img).into(imageView)
}