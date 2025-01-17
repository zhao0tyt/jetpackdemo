package com.example.jetpackdemo.ui.integral

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetpackdemo.R
import com.example.jetpackdemo.data.bean.IntegralResponse
import com.example.jetpackdemo.databinding.FragmentIntegralBinding
import com.example.jetpackdemo.ext.*
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.util.InjectorUtil
import com.kingja.loadsir.core.LoadService
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.nav
import com.zzq.common.ext.navigateAction
import com.zzq.common.ext.util.notNull
import com.zzq.common.util.LogUtil
import kotlinx.android.synthetic.main.fragment_integral.*
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.toolbar.*


class IntegralFragment : BaseFragment<BaseViewModel, FragmentIntegralBinding>(){
    private var rank: IntegralResponse? = null
    private val INTEGRAL_RULE_URL = "https://www.wanandroid.com/blog/show/2653"
    private val INTEGRAL_RULE_KEY = "integral_rule"
    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    private val mAdapter: IntegralAdapter by lazy{
        IntegralAdapter(arrayListOf())
    }

    private val viewModel: IntegralViewModel by viewModels {
        InjectorUtil.getIntegralViewModelFactory(requireContext())
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
        //Toolbar初始化
        toolbar.initClose("积分榜") {
            nav().navigateUp()
        }.run {
            inflateMenu(R.menu.integral_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    //跳转到积分规则页面
                    R.id.integral_rule -> {
                        nav().navigateAction(R.id.action_to_webFragment,
                            Bundle().apply {
                                putString(INTEGRAL_RULE_KEY, INTEGRAL_RULE_URL)
                            })
                    }
                    //跳转到积分历史页面
                    R.id.integral_history -> {
                        nav().navigateAction(R.id.action_integralFragment_to_integralHistoryFragment)
                    }

                }
                true
            }

        }

        mAdapter.loadMoreModule.setOnLoadMoreListener {
            viewModel.getIntegralRank(false)
        }
        // RecyclerView初始化
        recyclerView.init(LinearLayoutManager(context), mAdapter).run {
            this.initFloatBtn(floatbtn)
        }

        //初始化 SwipeRefreshLayout
        swipeRefreshLayout.init {
            //触发刷新监听时请求数据
            viewModel.getIntegralRank(true)
        }

        //状态页配置
        loadsir = loadServiceInit(swipeRefreshLayout) {
            LogUtil.logd("loadServiceInit")
            //点击重试时触发的操作
            loadsir.showLoading()
            viewModel.getIntegralRank(true)
        }
    }

    override fun lazyLoadData() {
        LogUtil.logd("lazyLoadData")
        loadsir.showLoading()
        viewModel.getIntegralRank(true)
    }

    override fun createObserver() {
        viewModel.integralDataState.observe(viewLifecycleOwner, Observer {
            loadListData(it, mAdapter, loadsir, swipeRefreshLayout)
        })
    }
}