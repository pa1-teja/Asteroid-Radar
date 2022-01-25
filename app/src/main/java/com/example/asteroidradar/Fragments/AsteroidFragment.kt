package com.example.asteroidradar.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.asteroidradar.dataAdapters.AsteroidsListAdapter
import com.example.asteroidradar.R
import com.example.asteroidradar.Utils
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

    private lateinit var astoridFragmentBinding: FragmentAsteroidBinding
    private lateinit var asteroidDatabase: AsteroidRadarDatabase

    private val asteroidViewModel: AsteroidViewModel by lazy {
        val asteroidViewModelFactory = AsteroidViewModelFactory(requireContext(),asteroidDatabase)
        ViewModelProvider(this,asteroidViewModelFactory).get(AsteroidViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        astoridFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_asteroid,container,false)

        asteroidDatabase = AsteroidRadarDatabase.getDatabaseInstance(requireContext())

        astoridFragmentBinding.lifecycleOwner = this

        astoridFragmentBinding.viewModel = asteroidViewModel

        astoridFragmentBinding.loadingIndicator.isIndeterminate = true

        astoridFragmentBinding.asteroidsList.adapter = AsteroidsListAdapter(AsteroidsListAdapter.OnClickListener{
            asteroidViewModel.displaySelectedAsteroidDetails(it)
        })


        asteroidViewModel.picOfDayURL.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrBlank()){
                Picasso.get().load(R.drawable.placeholder).fit().into(astoridFragmentBinding.imgOfDDay)
            }else if(it.equals("R.drawable.placeholder_picture_of_day")){
                Picasso.get().load(R.drawable.place_holder_img_of_the_day).fit().into(astoridFragmentBinding.imgOfDDay)
            }
            else{
                Picasso.get().load(it).placeholder(R.drawable.placeholder).fit().into(astoridFragmentBinding.imgOfDDay)
            }
        })

        asteroidViewModel.picOfDayExplanation.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank()){
                astoridFragmentBinding.imgOfDDay.contentDescription = getString(R.string.nasa_picture_of_day_content_description_format, it)
            } else{
                astoridFragmentBinding.imgOfDDay.contentDescription = getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
            }
        })

        asteroidViewModel.navigateToSelectedAsteroidDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(AsteroidFragmentDirections.actionAsteroidFragmentToAsteroidDetailFragment(it))
                asteroidViewModel.doneDisplayingAsteroidDetail()
            }
        })

        asteroidViewModel.loadStatus.observe(viewLifecycleOwner, Observer {
            it.let {
                when(it){
                    DataClasses.AsteroidLoadStatus.DONE ->{ astoridFragmentBinding.loadingIndicator.visibility = View.GONE }
                    DataClasses.AsteroidLoadStatus.ERROR ->{ astoridFragmentBinding.loadingIndicator.visibility = View.GONE
                       Utils().showAlertMessage(getString(R.string.failed_to_load_error_message),requireContext())
                    }
                    DataClasses.AsteroidLoadStatus.LOADING ->{astoridFragmentBinding.loadingIndicator.visibility = View.VISIBLE}
                }
            }
        })

        setHasOptionsMenu(true)
        return astoridFragmentBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.asteroid_radar_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.savedAsteroids ->{asteroidViewModel.getAsteroidsListFromDB(asteroidDatabase,DataClasses.AsteroidListRecordsFilter.SAVED) }
            R.id.weekAsteroids -> {asteroidViewModel.getAsteroidsListFromDB(asteroidDatabase,DataClasses.AsteroidListRecordsFilter.WEEK) }
            R.id.todayAsteroids -> {asteroidViewModel.getAsteroidsListFromDB(asteroidDatabase, DataClasses.AsteroidListRecordsFilter.TODAY) }
        }
        return super.onOptionsItemSelected(item)
    }


}