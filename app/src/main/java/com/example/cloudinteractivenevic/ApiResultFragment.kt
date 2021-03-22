package com.example.cloudinteractivenevic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cloudinteractivenevic.databinding.ApiResultBinding
import com.example.cloudinteractivenevic.databinding.HomeBinding

class ApiResultFragment : Fragment() {

    private lateinit var binding: ApiResultBinding
    private  val viewModel by viewModels<ApiResultViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = ApiResultBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@ApiResultFragment.viewModel
                lifecycleOwner = viewLifecycleOwner
            }
        viewModel.apply {
            clickGetPhotos = {
                Log.d("nevic", "clickGetPhotos")
                getPhotos()
                findNavController().navigate(
                    R.id.apiResultFragment
                )
            }
        }
        return binding.root
    }
}