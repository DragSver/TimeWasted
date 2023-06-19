package com.example.timewaster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.timewaster.databinding.ShortActivityFragmentBinding
import com.example.timewaster.viewmodel.ActivityViewModel
import kotlinx.coroutines.launch


class ActivityFragment : Fragment() {

    var navController: NavController?=null

    private lateinit var connectionManager: ConnectionManager

    lateinit var binding: ShortActivityFragmentBinding

    lateinit var viewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        connectionManager = ConnectionManager(this.requireContext())
        binding = ShortActivityFragmentBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
        navController = Navigation.findNavController(view)

        isConnected()

        binding.buttonGetActivity.setOnClickListener {
            if(isConnected()) {
                lifecycleScope.launch {
                    getData()
                    if (viewModel.activityLiveData.value != null) {
                        viewModel.setFavoriteButton(binding.favoriteButton, viewModel.activityLiveData.value!!)
                    }
                }
                if (binding.layoutActivity.visibility == View.GONE) {
                    binding.layoutActivity.visibility = View.VISIBLE
                }
            }
        }

        binding.favoriteButton.setOnClickListener {
            if (viewModel.activityLiveData.value != null){
                viewModel.switchFavorite(viewModel.activityLiveData.value!!, binding.favoriteButton)
            }
        }

        binding.activityOpenLongButton.setOnClickListener {
            if (navController!!.currentDestination?.id == R.id.activity_fragment){
                navController!!.navigate(R.id.action_activity_fragment_to_long_activity_fragment)
            }
        }

        binding.swipeContainer.setOnRefreshListener {
            isConnected()
            binding.swipeContainer.isRefreshing = false
        }

        viewModel.activityLiveData.observe(viewLifecycleOwner, Observer { activity ->
            binding.activityText.text = activity.activity
        })
    }

    private suspend fun getData() {
        viewModel.getData()
    }

    private fun isConnected() : Boolean {
        if (!connectionManager.isConnected()) {
            binding.unsuccessfulInternetConnectionText.visibility = View.VISIBLE
            binding.buttonGetActivity.visibility = View.GONE
            binding.layoutActivity.visibility = View.GONE
            return false
        } else {
            if (viewModel.activityLiveData.value != null) {
                binding.layoutActivity.visibility = View.VISIBLE
                viewModel.setFavoriteButton(binding.favoriteButton, viewModel.activityLiveData.value!!)
            }
            binding.buttonGetActivity.visibility = View.VISIBLE
            binding.unsuccessfulInternetConnectionText.visibility = View.GONE
            return true
        }
    }
}