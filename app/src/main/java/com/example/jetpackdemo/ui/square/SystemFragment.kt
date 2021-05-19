package com.example.jetpackdemo.ui.square

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.example.jetpackdemo.R
import com.example.jetpackdemo.adapter.SystemAdapter
import com.example.jetpackdemo.data.bean.SystemResponse
import com.example.jetpackdemo.databinding.IncludeListBinding
import com.example.jetpackdemo.ext.*
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.util.InjectorUtil
import com.example.jetpackdemo.widget.SpaceItemDecoration
import com.kingja.loadsir.core.LoadService
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.nav
import com.zzq.common.ext.navigateAction
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*

class SystemFragment:BaseFragment<BaseViewModel, IncludeListBinding>(){
    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    private val systemAdapter: SystemAdapter by lazy { SystemAdapter(arrayListOf()) }

    private val viewModel: SquareViewModel by viewModels {
        InjectorUtil.getSquareViewModelFactory(requireContext())
    }

    override fun layoutId(): Int {
        return R.layout.include_list
    }

    override fun initView(savedInstanceState: Bundle?) {
        //状态页配置
        loadsir = loadServiceInit(swipeRefreshLayout) {
            //点击重试时触发的操作
            loadsir.showLoading()
            viewModel.getSystemData()
        }
        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), systemAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            it.initFloatBtn(floatbtn)
        }
        //初始化 SwipeRefreshLayout
        swipeRefreshLayout.init {
            //触发刷新监听时请求数据
            viewModel.getSystemData()
        }
        systemAdapter.run {
            setOnItemClickListener { _, view, position ->
                if(systemAdapter.data[position].children.isNotEmpty()){
                    nav().navigateAction(R.id.action_mainfragment_to_systemArrFragment,
                        Bundle().apply {
                            putParcelable("data", systemAdapter.data[position])
                        }
                    )
                }
            }
            setChildClick { item: SystemResponse, view, position ->
                nav().navigateAction(R.id.action_mainfragment_to_systemArrFragment,
                    Bundle().apply {
                        putParcelable("data", item)
                        putInt("index", position)
                    }
                )
            }
        }
    }

    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        viewModel.getSystemData()
    }

    override fun createObserver() {
        viewModel.systemDataState.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = false
            if (it.isSuccess) {
                loadsir.showSuccess()
                systemAdapter.setList(it.listData)
            } else {
                loadsir.showError(it.errMessage)
            }
        })

    }
}