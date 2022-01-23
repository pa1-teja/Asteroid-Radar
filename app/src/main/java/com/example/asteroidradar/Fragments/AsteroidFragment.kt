package com.example.asteroidradar.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.asteroidradar.dataAdapters.AsteroidsListAdapter
import com.example.asteroidradar.R
import com.example.asteroidradar.dataClasses.DataClasses
import com.example.asteroidradar.database.AsteroidRadarDatabase
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

    private var application: Context? = null
    private lateinit var astoridFragmentBinding: FragmentAsteroidBinding
    private lateinit var asteroidDatabase: AsteroidRadarDatabase

    private val asteroidViewModel: AsteroidViewModel by lazy {
        val asteroidViewModelFactory = AsteroidViewModelFactory(asteroidDatabase)
        ViewModelProvider(this,asteroidViewModelFactory).get(AsteroidViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        astoridFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_asteroid,container,false)

        application = requireNotNull(this.requireActivity()).applicationContext

        asteroidDatabase = AsteroidRadarDatabase.getDatabaseInstance(requireContext())

        astoridFragmentBinding.lifecycleOwner = this

        astoridFragmentBinding.viewModel = asteroidViewModel

        astoridFragmentBinding.asteroidsList.adapter = AsteroidsListAdapter(AsteroidsListAdapter.OnClickListener{
            asteroidViewModel.displaySelectedAsteroidDetails(it)
        })


        asteroidViewModel.loadStatus.observe(viewLifecycleOwner, Observer {
            when(it){
                DataClasses.AsteroidLoadStatus.LOADING -> astoridFragmentBinding.progressBar.visibility = View.VISIBLE
                DataClasses.AsteroidLoadStatus.ERROR -> astoridFragmentBinding.progressBar.visibility = View.GONE
                DataClasses.AsteroidLoadStatus.DONE -> astoridFragmentBinding.progressBar.visibility = View.GONE
            }
        })

        asteroidViewModel.picOfDayURL.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrBlank()){
                Picasso.get().load(R.drawable.placeholder).fit().into(astoridFragmentBinding.imgOfDDay)
            } else{
                Picasso.get().load(it).placeholder(R.drawable.placeholder).fit().into(astoridFragmentBinding.imgOfDDay)
            }
        })

        asteroidViewModel.picOfDayExplanation.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank()){
                astoridFragmentBinding.imgOfDDay.contentDescription = it
            }
        })

        asteroidViewModel.navigateToSelectedAsteroidDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(AsteroidFragmentDirections.actionAsteroidFragmentToAsteroidDetailFragment(it))
                asteroidViewModel.doneDisplayingAsteroidDetail()
            }
        })

        return astoridFragmentBinding.root
    }
}