package com.example.cloudinteractivenevic.apiresult

import android.app.ProgressDialog
import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cloudinteractivenevic.R
import com.example.cloudinteractivenevic.common.navigateForward
import com.example.cloudinteractivenevic.databinding.ApiResultBinding

class ApiResultFragment : Fragment() {

    private lateinit var binding: ApiResultBinding
    private lateinit var mApiResultAdapter: ApiResultAdapter
    private  val viewModel by viewModels<ApiResultViewModel>()
    private var mProgressDialog: ProgressDialog? = null
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
                Log.d("nevic", "clickGetPhotos")

                findNavController().navigate(
                    R.id.apiResultFragment
                )
            }
            clickItemDetail = {
                findNavController().navigateForward(
                    R.id.detailFragment,
                    args = ApiResultFragmentArgs(
                        id = viewModel.id.value ?: "",
                        title = viewModel.title.value ?: "",
                        thumbnailUrl = viewModel.thumbnailUrl.value ?: ""
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
                showProgressDialog(R.string.SH_Load, false)
            } else {
                dismissLoadDialog()
            }
        }
    }
    protected fun showProgressDialog(messageId: Int, cancelable: Boolean) {
        if (mProgressDialog != null && mProgressDialog?.isShowing == true) {
            mProgressDialog?.setMessage(getString(messageId))
        } else {
            mProgressDialog = newProgressDialog(requireActivity(), messageId, cancelable)
            mProgressDialog?.show()
        }
    }
    fun newProgressDialog(
        context: Context,
        @StringRes messageId: Int,
        cancelable: Boolean
    ): ProgressDialog? {
        return newProgressDialog(context, context.getString(messageId), cancelable)
    }

    fun newProgressDialog(
        context: Context?,
        message: String?,
        cancelable: Boolean
    ): ProgressDialog? {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage(message)
        progressDialog.setCancelable(cancelable)
        progressDialog.setCanceledOnTouchOutside(cancelable)
        if (Build.VERSION.SDK_INT <= 21) {
            val drawable = ProgressBar(context).indeterminateDrawable.mutate()
            drawable.setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.color_main),
                PorterDuff.Mode.SRC_IN
            )
            progressDialog.setIndeterminateDrawable(drawable)
        }
        return progressDialog
    }
    fun dismissLoadDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }
}