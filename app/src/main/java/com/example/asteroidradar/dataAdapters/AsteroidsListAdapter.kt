package com.example.asteroidradar.dataAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.dataClasses.DataClasses
import com.example.asteroidradar.databinding.AsteroidsListItemBinding

class AsteroidsListAdapter(val onClickListener: OnClickListener):
    ListAdapter<DataClasses.Asteroid, AsteroidsListAdapter.AsteroidViewHolder>(AsteroidDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(AsteroidsListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid, onClickListener)
    }



    class AsteroidViewHolder(var asteroidsListItemBinding: AsteroidsListItemBinding) : RecyclerView.ViewHolder(asteroidsListItemBinding.root){
            fun bind(asteroid: DataClasses.Asteroid, clickListener: OnClickListener){
                asteroidsListItemBinding.asteroidInfo = asteroid
                asteroidsListItemBinding.closeApproachData = asteroid.closeApproachData.get(0)
                clickListener.onClick(asteroid)
                asteroidsListItemBinding.executePendingBindings()
            }
    }

    companion object AsteroidDiffCallback: DiffUtil.ItemCallback<DataClasses.Asteroid>() {

        override fun areItemsTheSame(oldItem: DataClasses.Asteroid, newItem: DataClasses.Asteroid): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DataClasses.Asteroid, newItem: DataClasses.Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (asteroid:DataClasses.Asteroid) -> Unit){
        fun onClick(asteroid: DataClasses.Asteroid) = clickListener(asteroid)
    }
}