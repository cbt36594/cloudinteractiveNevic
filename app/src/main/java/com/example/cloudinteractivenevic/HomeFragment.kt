package com.example.cloudinteractivenevic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cloudinteractivenevic.databinding.HomeBinding
import com.example.cloudinteractivenevic.extension.BitmapCache

class HomeFragment : Fragment() {

    private lateinit var binding: HomeBinding
    private  val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val bitmapCache = BitmapCache()
        bitmapCache.clearBitmap()
        binding = HomeBinding.inflate(inflater, container, false)
            .apply {
                viewModel = this@HomeFragment.viewModel
                lifecycleOwner = viewLifecycleOwner
            }
        viewModel.apply {
            clickGetPhotos = {
                findNavController().navigate(
                    R.id.apiResultFragment
                )
            }
        }
        return binding.root
    }
}