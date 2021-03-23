package com.example.cloudinteractivenevic.apiresult

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cloudinteractivenevic.R
import com.example.cloudinteractivenevic.databinding.DetailPageBinding

class DetailFragment : Fragment()  {

    private lateinit var binding: DetailPageBinding
    private  val viewModel by viewModels<ApiResultViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DetailPageBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@DetailFragment.viewModel
                lifecycleOwner = viewLifecycleOwner
                executePendingBindings()
            }
        viewModel.apply {
            getPhotos()
            clickGetPhotos = {
                Log.d("nevic", "clickGetPhotos")

                findNavController().navigate(
                    R.id.apiResultFragment
                )
            }
            clickItemDetail = {
                findNavController().navigate(
                    R.id.detailFragment
                )
            }
        }
        return binding.root
    }
}