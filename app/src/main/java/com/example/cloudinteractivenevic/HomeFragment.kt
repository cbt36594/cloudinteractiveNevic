package com.example.cloudinteractivenevic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cloudinteractivenevic.databinding.HomeBinding
import com.example.cloudinteractivenevic.model.Photos

class HomeFragment : Fragment() {
    private lateinit var binding: HomeBinding
    private  val viewModel by viewModels<HomeViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = HomeBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@HomeFragment.viewModel
                lifecycleOwner = viewLifecycleOwner
            }
        viewModel.apply {
            clickGetPhotos = {
                Log.d("nevic", "clickGetPhotos")
                findNavController().navigate(
                    R.id.apiResultFragment
                )
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.getPhotos()
    }
}