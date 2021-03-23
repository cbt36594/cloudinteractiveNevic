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
import com.example.cloudinteractivenevic.databinding.ApiResultBinding

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
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.getPhotos()
    }
}