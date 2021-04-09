package com.example.cloudinteractivenevic.apiresult

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cloudinteractivenevic.databinding.DetailPageBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment()  {

    private lateinit var binding: DetailPageBinding
    private  val viewModel by viewModel<DetailViewModel>() {
        /*** 使用koin帶參數進viewModel ***/
        parametersOf(args.id, args.title, args.thumbnailUrl)
    }
    private val args by navArgs<ApiResultFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DetailPageBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@DetailFragment.viewModel
                lifecycleOwner = viewLifecycleOwner
            }
        viewModel.apply {
              /*** LiveData另一種用法 ***/
//            setId(args.id)
//            setTitle(args.title)
//            setThumbnailUrl(args.thumbnailUrl)
            clickItemDetailBack = {
                findNavController().popBackStack()
            }
        }
        return binding.root
    }
}