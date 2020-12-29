package com.example.jetpackdemo.ui.integral

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackdemo.R
import com.example.jetpackdemo.adapter.FooterLoadStateAdapter
import com.example.jetpackdemo.adapter.HeaderLoadStateAdapter
import com.example.jetpackdemo.data.bean.IntegralResponse
import com.example.jetpackdemo.databinding.FragmentIntegralBinding
import com.example.jetpackdemo.ext.init
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.util.InjectorUtil
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.util.notNull
import com.zzq.common.util.LogUtil
import kotlinx.android.synthetic.main.fragment_integral.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.recyclerview_footer.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

class IntegralFragment : BaseFragment<BaseViewModel, FragmentIntegralBinding>(){
    private var rank: IntegralResponse? = null

    private val integralAdapter: IntegralAdapter by lazy{
        IntegralAdapter()
    }

    private val viewModel: IntegralViewModel by viewModels {
        InjectorUtil.getIntegralViewModelFactory(requireContext())
    }

    override fun layoutId() = R.layout.fragment_integral
    override fun initView(savedInstanceState: Bundle?) {
        LogUtil.logd("initView")
        statefulLayout.showLoading()

        mDatabind.vm = viewModel
        rank = arguments?.getParcelable("rank")
        rank.notNull({
            viewModel.rank.value = rank
        }, {
            integral_cardview.visibility = View.GONE
        })

        recyclerView.layoutManager = LinearLayoutManager(context)
        // Adapter初始化
        initAdapter(recyclerView)

        // SwipeRefreshLayout初始化
        swipeRefresh.init {
            integralAdapter.refresh()
        }

    }

    override fun lazyLoadData() {
        lifecycleScope.launchWhenCreated(){
            viewModel.getIntegralRankPager().collectLatest {
                integralAdapter.submitData(it)
            }
        }

    }

    private fun initAdapter(rl: RecyclerView) {
        rl.adapter = integralAdapter.withLoadStateHeaderAndFooter(
            header = HeaderLoadStateAdapter(),
            footer = FooterLoadStateAdapter(integralAdapter)
        )

//        lifecycleScope.launchWhenCreated {
//            integralAdapter.loadStateFlow.collectLatest { loadStates ->
//                swipeRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
//                if (loadStates.append is LoadState.Loading) {
//                    statefulLayout.showContent()
//                }
//            }
//        }

        lifecycleScope.launchWhenCreated {
            integralAdapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect{ recyclerView.scrollToPosition(0) }
        }

        integralAdapter.addLoadStateListener { combinedLoadStates ->
            LogUtil.logd("loadstate = "+combinedLoadStates.toString())

            when(combinedLoadStates.refresh){
                is LoadState.Loading -> {
                    swipeRefresh.isRefreshing = true
                    statefulLayout.showLoading()
                }
                is LoadState.Error -> statefulLayout.showError {
                    integralAdapter.refresh()
                }


                else -> {
                    swipeRefresh.isRefreshing = false
                    statefulLayout.showContent()
                }
            }

            when(combinedLoadStates.append){
                LoadState.Loading -> statefulLayout.showContent()

            }

        }
    }

}