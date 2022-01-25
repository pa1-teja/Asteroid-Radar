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
fun bindAsteroidListItemImage(imageView: AppCompatImageView, data: Boolean?) {
    imageView.setImageResource(if (data == true) R.drawable.ic_status_potentially_hazardous else R.drawable.ic_status_normal)
    imageView.contentDescription = if (data == true) imageView.context.getString(R.string.potentially_hazardous_asteroid_image) else  imageView.context.getString(R.string.not_hazardous_asteroid_image)
}

@BindingAdapter("nearApproachDate")
fun bindListItemNearApproachDate(textView: AppCompatTextView, date: String?) {
    textView.text = date
    textView.contentDescription = date
}

@BindingAdapter("asteroidName")
fun bindListItemAsteroidName(textView: AppCompatTextView, asteroidName: String?) {
    textView.text = asteroidName
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
    textView.text = textView.context.getString(R.string.astronomical_unit_format,data)
    textView.contentDescription = textView.context.getString(R.string.astronomical_unit_format,data)
}

@BindingAdapter("estimatedDiameter")
fun bindEstimatedDiameter(textView: AppCompatTextView, data:String?){
    textView.text = textView.context.getString(R.string.km_unit_format,data?.toFloat())
    textView.contentDescription = textView.context.getString(R.string.km_unit_format,data?.toFloat())
}

@BindingAdapter("relativeVelocity")
fun bindEstimatedVelocity(textView: AppCompatTextView, data: String?){
    textView.text = textView.context.resources.getString(R.string.km_s_unit_format,data?.toFloat())
    textView.contentDescription = textView.context.resources.getString(R.string.km_s_unit_format,data?.toFloat())
}

@BindingAdapter("distanceFromEarth")
fun bindDistanceFromEarth(textView: AppCompatTextView, data: String?){
    textView.text = textView.context.getString(R.string.astronomical_unit_format, data?.toFloat())
    textView.contentDescription = textView.context.getString(R.string.astronomical_unit_format,data?.toFloat())
}

@BindingAdapter("asteroidImage")
fun bindHazardousAsteroidImage(imageView: AppCompatImageView, isPotentiallyDangerous: Boolean?){
    val img = if (isPotentiallyDangerous == true) R.drawable.asteroid_hazardous else R.drawable.asteroid_safe
    imageView.contentDescription = if (isPotentiallyDangerous == true) imageView.context.getString(R.string.potentially_hazardous_asteroid_image) else  imageView.context.getString(R.string.not_hazardous_asteroid_image)
    Picasso.get().load(img).into(imageView)
}