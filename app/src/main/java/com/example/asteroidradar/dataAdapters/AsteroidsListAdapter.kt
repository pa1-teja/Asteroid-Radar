package com.example.asteroidradar.dataAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar.dataClasses.DataClasses
import com.example.asteroidradar.databinding.AsteroidsListItemBinding

class AsteroidsListAdapter(val onClickListener: OnClickListener):
    ListAdapter<DataClasses.Asteroids, AsteroidsListAdapter.ViewHolder>(AsteroidsDiffUtil()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),onClickListener)
    }

    class ViewHolder private constructor(val binding: AsteroidsListItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: DataClasses.Asteroids, onClickListener: OnClickListener){
            binding.asteroidInfo = item
            binding.clickListener = onClickListener
            binding.executePendingBindings()
        }
        companion object{
            internal fun from(parent: ViewGroup):ViewHolder{
                val binding = AsteroidsListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return ViewHolder(binding)
            }
        }
    }

    class AsteroidsDiffUtil: DiffUtil.ItemCallback<DataClasses.Asteroids>(){
        override fun areItemsTheSame(oldItem: DataClasses.Asteroids, newItem: DataClasses.Asteroids): Boolean {
            return oldItem.asteroidTableId == newItem.asteroidTableId
        }

        override fun areContentsTheSame(oldItem: DataClasses.Asteroids, newItem: DataClasses.Asteroids): Boolean {
            return oldItem == newItem
        }
    }

    class OnClickListener(val onClickListener: (asteroidTableId: Long) -> Unit){
        fun onClick(asteroidDetail: DataClasses.Asteroids) = onClickListener(asteroidDetail.asteroidTableId)
    }
}