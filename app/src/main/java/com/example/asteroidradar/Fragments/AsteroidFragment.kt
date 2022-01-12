package com.example.asteroidradar.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.asteroidradar.dataAdapters.AsteroidsListAdapter
import com.example.asteroidradar.R
import com.example.asteroidradar.viewModels.AsteroidViewModel
import com.example.asteroidradar.viewModels.AsteroidViewModelFactory
import com.example.asteroidradar.databinding.FragmentAsteroidBinding
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass.
 * Use the [AsteroidFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AsteroidFragment : BaseFragment() {

    private lateinit var astoridFragmentBinding: FragmentAsteroidBinding

    private val asteroidViewModel: AsteroidViewModel by lazy {
        val asteroidViewModelFactory = AsteroidViewModelFactory(requireContext())
        ViewModelProvider(this,asteroidViewModelFactory).get(AsteroidViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        astoridFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_asteroid,container,false)

        astoridFragmentBinding.lifecycleOwner = this

        astoridFragmentBinding.viewModel = asteroidViewModel

        astoridFragmentBinding.asteroidsList.adapter = AsteroidsListAdapter(AsteroidsListAdapter.OnClickListener{
            asteroidViewModel.displaySelectedAsteroidDetails(it)
        })

        asteroidViewModel.pictureOfTheDay.observe(viewLifecycleOwner, Observer {
            if (it != null && it.hdurl.isNotBlank()){
                Picasso.get().load(it.hdurl).error(R.drawable.placeholder).fit().into(astoridFragmentBinding.imgOfDDay)
            }else{
                Picasso.get().load(R.drawable.placeholder).fit().into(astoridFragmentBinding.imgOfDDay)
            }
        })

        asteroidViewModel.navigateToSelectedAsteroidDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                Navigation.createNavigateOnClickListener(AsteroidFragmentDirections.actionAsteroidFragmentToAsteroidDetailFragment(it))
                asteroidViewModel.doneDisplayingAsteroidDetail()
            }
        })

        return astoridFragmentBinding.root
    }
}