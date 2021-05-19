package com.example.jetpackdemo.ui.square

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.example.jetpackdemo.R
import com.example.jetpackdemo.adapter.AriticleAdapter
import com.example.jetpackdemo.databinding.IncludeListBinding
import com.example.jetpackdemo.ext.*
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.ui.collect.CollectViewModel
import com.example.jetpackdemo.util.CacheUtil
import com.example.jetpackdemo.util.InjectorUtil
import com.example.jetpackdemo.widget.SpaceItemDecoration
import com.kingja.loadsir.core.LoadService
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.nav
import com.zzq.common.ext.navigateAction
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*

class AskFragment: BaseFragment<BaseViewModel, IncludeListBinding>() {
    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    private val viewModel: SquareViewModel by viewModels {
        InjectorUtil.getSquareViewModelFactory(requireContext())
    }

    private val articleAdapter: AriticleAdapter by lazy {
        AriticleAdapter(
            arrayListOf(),
            showTag = true
        )
    }

    //收藏viewmodel
    private val collectViewModel: CollectViewModel by viewModels {
        InjectorUtil.getCollectViewModelFactory(requireContext())
    }

    override fun layoutId() = R.layout.include_list

    override fun initView(savedInstanceState: Bundle?) {
        //状态页配置
        loadsir = loadServiceInit(swipeRefreshLayout) {
            //点击重试时触发的操作
            loadsir.showLoading()
            viewModel.getAskData(true)
        }
        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), articleAdapter).run {
            this.initFloatBtn(floatbtn)
            this.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
        }
        //初始化 SwipeRefreshLayout
        swipeRefreshLayout.init {
            //触发刷新监听时请求数据
            viewModel.getAskData(true)
        }
        articleAdapter.run {
            setCollectClick { item, v, position ->
                if(CacheUtil.isLogin()) {
                    if (v.isChecked) {
                        v.isChecked = false
                        v.setCancel()
                        collectViewModel.uncollect(item.id)
                    } else {
                        v.isChecked = true
                        v.showAnim()
                        collectViewModel.collect(item.id)
                    }
                } else {
                    nav(v).navigateAction(R.id.action_to_loginFragment)
                }
            }
            setOnItemClickListener { _, view, position ->
                nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                    putParcelable("ariticleData", articleAdapter.data[position])
                })
            }
            addChildClickViewIds(R.id.item_home_author, R.id.item_project_author)
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
//                    R.id.item_home_author, R.id.item_project_author -> {
//                        nav().navigateAction(
//                            R.id.action_mainfragment_to_lookInfoFragment,
//                            Bundle().apply {
//                                putInt(
//                                    "id",
//                                    articleAdapter.data[position - this@AskFragment.recyclerView.headerCount].userId
//                                )
//                            })
//                    }
                }
            }
        }
    }

    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        viewModel.getAskData(true)
    }

    override fun createObserver() {
        viewModel.askDataState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, articleAdapter, loadsir, swipeRefreshLayout)
        })
        collectViewModel.collectUiState.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                //收藏或取消收藏操作成功，发送全局收藏消息
//                eventViewModel.collectEvent.value = CollectBus(it.id, it.collect)
            } else {
                showMessage(it.errorMsg)
                for (index in articleAdapter.data.indices) {
                    if (articleAdapter.data[index].id == it.id) {
                        articleAdapter.data[index].collect = it.collect
                        articleAdapter.notifyItemChanged(index)
                        break
                    }
                }
            }
        })
        appViewModel.run {
            //监听账户信息是否改变 有值时(登录)将相关的数据设置为已收藏，为空时(退出登录)，将已收藏的数据变为未收藏
            userinfo.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    it.collectIds.forEach { id ->
                        for (item in articleAdapter.data) {
                            if (id.toInt() == item.id) {
                                item.collect = true
                                break
                            }
                        }
                    }
                } else {
                    for (item in articleAdapter.data) {
                        item.collect = false
                    }
                }
                articleAdapter.notifyDataSetChanged()
            })
        }
        //监听全局的收藏信息 收藏的Id跟本列表的数据id匹配则需要更新
//        eventViewModel.collectEvent.observeInFragment(this, Observer {
//            for (index in articleAdapter.data.indices) {
//                if (articleAdapter.data[index].id == it.id) {
//                    articleAdapter.data[index].collect = it.collect
//                    articleAdapter.notifyItemChanged(index)
//                    break
//                }
//            }
//        })
    }
}