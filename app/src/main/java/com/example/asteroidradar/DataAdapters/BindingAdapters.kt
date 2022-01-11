package com.example.asteroidradar.DataAdapters

import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.R
import com.example.asteroidradar.dataClasses.DataClasses
import com.squareup.picasso.Picasso

class BindingAdapters {

    @BindingAdapter("asteroidListData")
    fun bindRecyclerView(recyclerView: RecyclerView, data: List<DataClasses.Asteroid>?) {
        val adapter = recyclerView.adapter as AsteroidsListAdapter
        adapter.submitList(data)
    }

    @BindingAdapter("isAsteroidDangerous")
    fun bindAsteroidListItemImage(imageView: AppCompatImageView, data: DataClasses.Asteroid?) {
        val img =
            if (data?.isPotentiallyHazardousAsteroid == true) R.drawable.ic_status_potentially_hazardous else R.drawable.ic_status_normal
        Picasso.get().load(img).into(imageView)
    }

    @BindingAdapter("nearApproachDate")
    fun bindListItemNearApproachDate(textView: AutoCompleteTextView, data: DataClasses.CloseApproachData) {
        textView.setText(data.closeApproachDate)
    }

    @BindingAdapter("asteroidName")
    fun bindListItemAsteroidName(textView: AutoCompleteTextView, data: DataClasses.Asteroid) {
        textView.setText(data.name)
    }
}
