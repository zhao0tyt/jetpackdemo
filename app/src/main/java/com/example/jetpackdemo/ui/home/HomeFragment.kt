package com.example.jetpackdemo.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.example.jetpackdemo.R
import com.example.jetpackdemo.adapter.AriticleAdapter
import com.example.jetpackdemo.adapter.HomeBannerAdapter
import com.example.jetpackdemo.adapter.HomeBannerViewHolder
import com.example.jetpackdemo.data.bean.BannerResponse
import com.example.jetpackdemo.databinding.FragmentHomeBinding
import com.example.jetpackdemo.ext.*
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.ui.collect.CollectViewModel
import com.example.jetpackdemo.ui.project.ProjectViewModel
import com.example.jetpackdemo.util.CacheUtil
import com.example.jetpackdemo.util.InjectorUtil
import com.example.jetpackdemo.widget.SpaceItemDecoration
import com.kingja.loadsir.core.LoadService
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.zhpan.bannerview.BannerViewPager
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.nav
import com.zzq.common.ext.navigateAction
import com.zzq.common.ext.parseState
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*
import kotlinx.android.synthetic.main.toolbar.*

class HomeFragment : BaseFragment<BaseViewModel,FragmentHomeBinding>() {
    //适配器
    private val articleAdapter: AriticleAdapter by lazy { AriticleAdapter(arrayListOf(), true) }

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //收藏viewmodel
    private val collectViewModel: CollectViewModel by viewModels {
        InjectorUtil.getCollectViewModelFactory(requireContext())
    }

    //请求数据ViewModel
    private val viewModel: HomeViewModel by viewModels {
        InjectorUtil.getHomeViewModelFactory(requireContext())
    }

    override fun layoutId() = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {
        //状态页配置
        loadsir = loadServiceInit(swipeRefreshLayout) {
            //点击重试时触发的操作
            loadsir.showLoading()
            viewModel.getBannerData()
            viewModel.getHomeData(true)
        }
        //初始化 toolbar
        toolbar.run {
            init("首页")
            inflateMenu(R.menu.home_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.home_search -> {
                        nav().navigateAction(R.id.action_mainfragment_to_searchFragment)
                    }
                }
                true
            }
        }
        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), articleAdapter).run {
            this.initFloatBtn(floatbtn)
            this.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
        }
        //初始化 SwipeRefreshLayout
        swipeRefreshLayout.init {
            //触发刷新监听时请求数据
            viewModel.getHomeData(true)
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
            setOnItemClickListener { adapter, view, position ->
                nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                    putParcelable(
                        "ariticleData",
                        articleAdapter.data[position]
                    )
                })
            }
            addChildClickViewIds(R.id.item_home_author, R.id.item_project_author)
            setOnItemChildClickListener { adapter, view, position ->
                when (view.id) {
                    R.id.item_home_author, R.id.item_project_author -> {
//                        nav().navigateAction(
//                            R.id.action_mainfragment_to_lookInfoFragment,
//                            Bundle().apply {
//                                putInt(
//                                    "id",
//                                    articleAdapter.data[position - this@HomeFragment.recyclerView.headerCount].userId
//                                )
//                            })
                    }
                }
            }
            //加载更多
            loadMoreModule.setOnLoadMoreListener {
                viewModel.getHomeData(false)
            }
        }
    }

    /**
     * 懒加载
     */
    override fun lazyLoadData() {
        //设置界面 加载中
        loadsir.showLoading()
        //请求轮播图数据
        viewModel.getBannerData()
        //请求文章列表数据
        viewModel.getHomeData(true)
    }

    override fun createObserver() {
        viewModel.run {
            //监听首页文章列表请求的数据变化
            homeDataState.observe(viewLifecycleOwner, Observer {
                //设值 新写了个拓展函数，搞死了这个恶心的重复代码
                loadListData(it, articleAdapter, loadsir, swipeRefreshLayout)
            })
            //监听轮播图请求的数据变化
            bannerData.observe(viewLifecycleOwner, Observer { resultState ->
                parseState(resultState, { data ->
                    //请求轮播图数据成功，添加轮播图到headview ，如果等于0说明没有添加过头部，添加一个
                    if (articleAdapter.headerLayoutCount == 0) {
                        val headview = LayoutInflater.from(context).inflate(R.layout.include_banner, null).apply {
                            findViewById<BannerViewPager<BannerResponse, HomeBannerViewHolder>>(R.id.banner_view).apply {
                                adapter = HomeBannerAdapter()
                                setLifecycleRegistry(lifecycle)
                                setOnPageClickListener {
                                    nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {putParcelable("bannerdata", data[it])})
                                }
                                create(data)
                            }
                        }
                        articleAdapter.addHeaderView(headview)
                        recyclerView.scrollToPosition(0)
                    }
                })
            })
        }
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
            //监听全局的收藏信息 收藏的Id跟本列表的数据id匹配则需要更新
//            eventViewModel.collectEvent.observeInFragment(this@HomeFragment, Observer {
//                for (index in articleAdapter.data.indices) {
//                    if (articleAdapter.data[index].id == it.id) {
//                        articleAdapter.data[index].collect = it.collect
//                        articleAdapter.notifyItemChanged(index)
//                        break
//                    }
//                }
//            })
        }
    }

}