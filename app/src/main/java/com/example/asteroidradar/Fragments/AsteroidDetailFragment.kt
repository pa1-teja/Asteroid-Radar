package com.example.asteroidradar.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.asteroidradar.R
import com.example.asteroidradar.database.AsteroidRadarDatabase
import com.example.asteroidradar.databinding.FragmentAsteroidDetailBinding
import com.example.asteroidradar.viewModels.AsteroidDetailViewModel
import com.example.asteroidradar.viewModels.AsteroidDetailViewModelFactory
import com.example.asteroidradar.viewModels.AsteroidViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [AsteroidDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AsteroidDetailFragment : BaseFragment() {

    private var asteroidObjBundle: Long = -1
    private lateinit var detailFragBinding : FragmentAsteroidDetailBinding
    private lateinit var asteroidDatabase: AsteroidRadarDatabase

    private val asteroidDetailViewModel: AsteroidDetailViewModel by lazy {
        val asteroidDetailViewModelFactory = AsteroidDetailViewModelFactory(asteroidObjBundle, asteroidDatabase)
        ViewModelProvider(this,asteroidDetailViewModelFactory).get(AsteroidDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        detailFragBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_asteroid_detail,container,false)

         asteroidObjBundle = AsteroidDetailFragmentArgs.fromBundle(requireArguments()).asteroidObjectId
        asteroidDatabase = AsteroidRadarDatabase.getDatabaseInstance(requireContext())
        detailFragBinding.lifecycleOwner = this
        detailFragBinding.asteroidObj = asteroidDetailViewModel
        return detailFragBinding.root
    }
}