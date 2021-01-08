package com.example.jetpackdemo.ui.integral

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.FragmentListBinding
import com.example.jetpackdemo.ext.*
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.util.InjectorUtil
import com.kingja.loadsir.core.LoadService
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.nav
import com.zzq.common.util.LogUtil
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.toolbar.*

class IntegralHistoryFragment : BaseFragment<BaseViewModel, FragmentListBinding>(){

    private val viewModel: IntegralHistoryViewModel by viewModels {
        InjectorUtil.getIntegralHistoryViewModelFactory(requireContext())
    }
    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>
    private lateinit var smartRefreshLayout: SmartRefreshLayout

    private val mAdapter: IntegralHistoryAdapter by lazy{
        IntegralHistoryAdapter(arrayListOf())
    }

    override fun layoutId(): Int {
        return R.layout.fragment_list
    }

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.initClose("积分记录") {
            nav().navigateUp()
        }
        // RecyclerView初始化
        recyclerView.init(LinearLayoutManager(context), mAdapter)

        // SmartRefreshLayout初始化
        smartRefreshLayout = srl.init(ClassicsHeader(context), ClassicsFooter(context),{
            viewModel.getIntegralHistoryData(true)
        },{
            viewModel.getIntegralHistoryData(false)
        })

        //状态页配置
        loadsir = loadServiceInit(smartRefreshLayout) {
            LogUtil.logd("loadServiceInit")
            //点击重试时触发的操作
            loadsir.showLoading()
            viewModel.getIntegralHistoryData(true)
        }
    }
    override fun lazyLoadData() {
        LogUtil.logd("lazyLoadData")
        loadsir.showLoading()
        viewModel.getIntegralHistoryData(true)
    }

    override fun createObserver() {
        viewModel.integralHistoryDataState.observe(viewLifecycleOwner, Observer {
            loadListData(it, mAdapter, loadsir, smartRefreshLayout)
        })
    }
}