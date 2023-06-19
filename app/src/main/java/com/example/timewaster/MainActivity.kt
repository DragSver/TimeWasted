package com.example.timewaster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.timewaster.databinding.MainBinding
import com.example.timewaster.viewmodel.ActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainBinding
    lateinit var navController: NavController
    lateinit var viewModel: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ActivityViewModel::class.java]
        viewModel.init(this)

        navController =
            (supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment).navController

        val menu = binding.topAppBar.menu
        val favoriteItem = menu.findItem(R.id.open_favorite_button)
        favoriteItem?.setOnMenuItemClickListener {
            if (navController.currentDestination?.id == R.id.activity_fragment){
                navController.navigate(R.id.action_activity_fragment_to_favorite_activities_fragment)
            }
            if (navController.currentDestination?.id == R.id.long_activity_fragment){
                navController.navigate(R.id.action_long_activity_fragment_to_favorite_activities_fragment)
            }
            favoriteItem.setIcon(R.drawable.baseline_favorite_24)
            true
        }
        binding.topAppBar.setOnClickListener {
            if (navController.currentDestination?.id == R.id.favorite_activities_fragment) {
                navController.navigate(R.id.action_favorite_activities_fragment_to_activity_fragment)
            }
            if (navController.currentDestination?.id == R.id.long_activity_fragment) {
                navController.navigate(R.id.action_long_activity_fragment_to_activity_fragment)
            }
            favoriteItem?.setIcon(R.drawable.baseline_favorite_border_24)
        }

        setContentView(binding.root)
    }
}