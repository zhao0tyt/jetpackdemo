package com.example.jetpackdemo.ui.integral

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.jetpackdemo.R
import com.example.jetpackdemo.data.model.IntegralResponse
import com.example.jetpackdemo.databinding.FragmentIntegralBinding
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.util.InjectorUtil
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.util.notNull
import kotlinx.android.synthetic.main.fragment_integral.*

class IntegralFragment : BaseFragment<BaseViewModel, FragmentIntegralBinding>() {
    private var rank: IntegralResponse? = null

    private val viewModel: IntegralViewModel by viewModels {
        InjectorUtil.getIntegralViewModelFactory()
    }

    override fun layoutId() = R.layout.fragment_integral
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = viewModel
        rank = arguments?.getParcelable("rank")
        rank.notNull({
            viewModel.rank.value = rank
        }, {
            integral_cardview.visibility = View.GONE
        })

    }

    override fun lazyLoadData() {

    }

}