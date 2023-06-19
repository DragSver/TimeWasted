package com.example.timewaster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.timewaster.databinding.LongActivityFragmentBinding
import com.example.timewaster.viewmodel.ActivityViewModel

class LongActivityFragment : Fragment() {

    var navController: NavController?=null

    lateinit var binding: LongActivityFragmentBinding

    lateinit var viewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LongActivityFragmentBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(ActivityViewModel::class.java)

        binding.activityText.text = viewModel.activityLiveData.value!!.activity
        binding.accessibilityText.text = viewModel.activityLiveData.value!!.accessibility.toString()
        binding.typeText.text = viewModel.activityLiveData.value!!.type
        if (viewModel.activityLiveData.value!!.link != "") {
            binding.cardLink.visibility = View.VISIBLE
            binding.linkText.text = viewModel.activityLiveData.value!!.link
        } else {
            binding.cardLink.visibility = View.GONE
        }
        binding.participantsText.text = viewModel.activityLiveData.value!!.participants.toString()
        binding.priceText.text = viewModel.activityLiveData.value!!.price.toString()
        binding.keyText.text = viewModel.activityLiveData.value!!.key
        viewModel.setFavoriteButton(binding.favoriteButton, viewModel.activityLiveData.value!!)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        binding.favoriteButton.setOnClickListener {
            viewModel.switchFavorite(viewModel.activityLiveData.value!!, binding.favoriteButton)
        }
    }
}