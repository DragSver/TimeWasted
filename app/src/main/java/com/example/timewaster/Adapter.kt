package com.example.timewaster

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import com.example.timewaster.databinding.ActivityItemBinding
import com.example.timewaster.viewmodel.ActivityViewModel

class Adapter(val viewModel: ActivityViewModel, val navController: NavController) :
    ListAdapter<Activity, Adapter.ItemHolder>(ProductComparator())   {

    class ProductComparator : DiffUtil.ItemCallback<Activity>(){
        override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem.key == newItem.key
        }
        override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem == newItem
        }
    }

    class ItemHolder(val binding: ActivityItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(activity: Activity, viewModel: ActivityViewModel, navController: NavController) = with(binding){
            activityText.text = activity.activity
            viewModel.setFavoriteButton(favoriteButton, activity)

            favoriteButton.setOnClickListener {
                viewModel.switchFavorite(activity, favoriteButton)
            }
            activityOpenLongButton.setOnClickListener {
                viewModel.activityLiveData.value = activity
                navController.navigate(R.id.action_favorite_activities_fragment_to_long_activity_fragment)
            }
        }
        companion object{
            fun create(parent: ViewGroup): ItemHolder{
                return ItemHolder(ActivityItemBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = ActivityItemBinding.inflate(inflater)

        return ItemHolder(binding)
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position), viewModel, navController)
    }
}