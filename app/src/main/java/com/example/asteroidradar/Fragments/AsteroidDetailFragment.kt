package com.example.asteroidradar.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.asteroidradar.R
import com.example.asteroidradar.databinding.FragmentAsteroidDetailBinding


/**
 * A simple [Fragment] subclass.
 * Use the [AsteroidDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AsteroidDetailFragment : BaseFragment() {

    private lateinit var detailFragBinding : FragmentAsteroidDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        detailFragBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_asteroid_detail,container,false)


        return detailFragBinding.root
    }
}