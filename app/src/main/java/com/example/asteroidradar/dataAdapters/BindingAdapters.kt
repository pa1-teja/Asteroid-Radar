package com.example.asteroidradar.dataAdapters

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.R
import com.example.asteroidradar.dataClasses.DataClasses
import com.squareup.picasso.Picasso


@BindingAdapter("asteroidListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DataClasses.Asteroids>?) {
    val adapter = recyclerView.adapter as AsteroidsListAdapter
    adapter.submitList(data)
}

@BindingAdapter("isAsteroidDangerous")
fun bindAsteroidListItemImage(imageView: AppCompatImageView, data: Boolean) {
    imageView.setImageResource(if (data) R.drawable.ic_status_potentially_hazardous else R.drawable.ic_status_normal)
    imageView.contentDescription = if (data) "The asteroid is Potentially Hazardous" else "The asteroid is not Potentially Hazardous"
}

@BindingAdapter("nearApproachDate")
fun bindListItemNearApproachDate(textView: AppCompatTextView, date: String) {
    textView.setText(date)
    textView.contentDescription = date
}

@BindingAdapter("asteroidName")
fun bindListItemAsteroidName(textView: AppCompatTextView, asteroidName: String) {
    textView.setText(asteroidName)
    textView.contentDescription = asteroidName
}


/*
 * Below adapters are related to AsteroidDetailFragment
 */

@BindingAdapter("closeApproachDate")
fun bindCloseApproachDate(textView: AppCompatTextView, date: String?) {
    textView.text = date
    textView.contentDescription = date
}

@BindingAdapter("magnitude")
fun bindCloseApproachAsteroidMagnitude(textView: AppCompatTextView, data: Double?) {
    textView.text = data.toString()
    textView.contentDescription = data.toString()
}

@BindingAdapter("estimatedDiameter")
fun bindEstimatedDiameter(textView: AppCompatTextView, data:String?){
    textView.text = "$data Kms"
    textView.contentDescription = "$data Kms"
}

@BindingAdapter("relativeVelocity")
fun bindEstimatedVelocity(textView: AppCompatTextView, data: String?){
    textView.text = data
    textView.contentDescription = data
}

@BindingAdapter("distanceFromEarth")
fun bindDistanceFromEarth(textView: AppCompatTextView, data: String?){
    textView.text = data
    textView.contentDescription = data
}

@BindingAdapter("asteroidImage")
fun bindHazardousAsteroidImage(imageView: AppCompatImageView, isPotentiallyDangerous: Boolean?){
    val img = if (isPotentiallyDangerous == true) R.drawable.asteroid_hazardous else R.drawable.asteroid_safe
    imageView.contentDescription = if (isPotentiallyDangerous == true) "The asteroid is Potentially Hazardous" else "The asteroid is not Potentially Hazardous"
    Picasso.get().load(img).into(imageView)
}