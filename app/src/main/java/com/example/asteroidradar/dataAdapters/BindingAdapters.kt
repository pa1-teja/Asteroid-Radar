package com.example.asteroidradar.dataAdapters

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.R
import com.example.asteroidradar.dataClasses.DataClasses
import com.squareup.picasso.Picasso



    @BindingAdapter("asteroidListData")
    fun bindRecyclerView(recyclerView: RecyclerView, data: List<DataClasses.Asteroid>?) {
        val adapter = recyclerView.adapter as AsteroidsListAdapter
        adapter.submitList(data)
    }

    @BindingAdapter("isAsteroidDangerous")
    fun bindAsteroidListItemImage(imageView: AppCompatImageView, data: Boolean) {
        imageView.setImageResource(if (data) R.drawable.ic_status_potentially_hazardous else R.drawable.ic_status_normal)
    }

    @BindingAdapter("nearApproachDate")
    fun bindListItemNearApproachDate(textView: AppCompatTextView, data: DataClasses.CloseApproachData) {
        textView.setText(data.closeApproachDate)
    }

    @BindingAdapter("asteroidName")
    fun bindListItemAsteroidName(textView: AppCompatTextView, data: DataClasses.Asteroid) {
        textView.setText(data.name)
    }

