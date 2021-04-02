package com.example.cloudinteractivenevic.apiresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cloudinteractivenevic.R
import com.example.cloudinteractivenevic.common.navigateForward
import com.example.cloudinteractivenevic.databinding.ApiResultBinding
import com.example.cloudinteractivenevic.extension.*
import kotlinx.coroutines.*

class ApiResultFragment : Fragment() {

    private lateinit var binding: ApiResultBinding
    private lateinit var mApiResultAdapter: ApiResultAdapter
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
                mApiResultAdapter = ApiResultAdapter(this@ApiResultFragment.viewModel)
                rvPhotosList.adapter = mApiResultAdapter
            }
        viewModel.apply {
            getPhotos()
            clickGetPhotos = {
                findNavController().navigate(
                    R.id.apiResultFragment
                )
            }
            clickItemDetail = {itemId, itemTitle, itemThumbnailUrl ->
                findNavController().navigateForward(
                    R.id.detailFragment,
                    args = ApiResultFragmentArgs(
                        id = itemId,
                        title = itemTitle,
                        thumbnailUrl = itemThumbnailUrl
                    ).toBundle()
                )
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoad ->
            if (isLoad) {
                binding.progressbar.visibility = View.VISIBLE
            } else {
                binding.progressbar.visibility = View.GONE
            }
        }
    }
}