package com.example.jetpackdemo.ui.integral

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetpackdemo.R
import com.example.jetpackdemo.data.model.IntegralResponse
import com.example.jetpackdemo.databinding.FragmentIntegralBinding
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.util.InjectorUtil
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.util.notNull
import kotlinx.android.synthetic.main.fragment_integral.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class IntegralFragment : BaseFragment<BaseViewModel, FragmentIntegralBinding>() {
    private var rank: IntegralResponse? = null
    //适配器
    private lateinit var integralAdapter: IntegralAdapter

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
        recyclerView.layoutManager = LinearLayoutManager(context)
        integralAdapter = IntegralAdapter()
        recyclerView.adapter = integralAdapter
    }

    override fun lazyLoadData() {
        lifecycleScope.launch(){
            viewModel.getIntegralRankPager().collectLatest {
                integralAdapter.submitData(it)
            }
        }

    }

}