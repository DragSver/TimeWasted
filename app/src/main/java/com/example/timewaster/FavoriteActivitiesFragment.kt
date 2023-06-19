package com.example.timewaster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.example.timewaster.databinding.FavoriteActivityFragmentBinding
import com.example.timewaster.viewmodel.ActivityViewModel

class FavoriteActivitiesFragment : Fragment() {

    var navController: NavController?=null

    lateinit var binding: FavoriteActivityFragmentBinding
    lateinit var adapter: ListAdapter<Activity, Adapter.ItemHolder>

    lateinit var viewModel: ActivityViewModel

    val favoriteActivitiesLiveData = MutableLiveData<List<Activity>>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
        binding = FavoriteActivityFragmentBinding.inflate(layoutInflater)
        binding.rcView.layoutManager = LinearLayoutManager(context)
        favoriteActivitiesLiveData.value = viewModel.activities

//        binding.swipeContainer.setOnRefreshListener {
//            getPreferences()
//            binding.swipeContainer.isRefreshing = false
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        if (viewModel.sharedPreferences != null) {
            adapter = Adapter(viewModel, navController!!)
        }
        binding.rcView.adapter = adapter

        favoriteActivitiesLiveData.observe(viewLifecycleOwner, Observer { activities ->
            adapter.submitList(activities)
        })
    }
}