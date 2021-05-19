package com.example.jetpackdemo.ui.project

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.example.jetpackdemo.R
import com.example.jetpackdemo.adapter.AriticleAdapter
import com.example.jetpackdemo.databinding.FragmentListBinding
import com.example.jetpackdemo.ext.*
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.ui.collect.CollectViewModel
import com.example.jetpackdemo.util.CacheUtil
import com.example.jetpackdemo.util.InjectorUtil
import com.example.jetpackdemo.widget.SpaceItemDecoration
import com.kingja.loadsir.core.LoadService
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.nav
import com.zzq.common.ext.navigateAction
import kotlinx.android.synthetic.main.include_list.*
import kotlinx.android.synthetic.main.include_recyclerview.*

class ProjectChildFragment : BaseFragment<BaseViewModel,FragmentListBinding>() {

    //适配器
    private val articleAdapter: AriticleAdapter by lazy { AriticleAdapter(arrayListOf()) }

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //是否是最新项目
    private var isNew = false

    //改项目对应的id
    private var cid = 0

    //收藏viewmodel
    private val collectViewModel: CollectViewModel by viewModels {
        InjectorUtil.getCollectViewModelFactory(requireContext())
    }

    //请求的ViewModel
    private val viewModel: ProjectViewModel by viewModels {
        InjectorUtil.getProjectViewModelFactory(requireContext())
    }

    override fun layoutId() = R.layout.include_list

    override fun initView(savedInstanceState: Bundle?) {
        arguments?.let {
            isNew = it.getBoolean("isNew")
            cid = it.getInt("cid")
        }
        //状态页配置
        loadsir = loadServiceInit(swipeRefreshLayout) {
            //点击重试时触发的操作
            loadsir.showLoading()
            viewModel.getProjectData(true, cid, isNew)
        }
        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), articleAdapter).run {
            this.initFloatBtn(floatbtn)
            this.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
        }
        //初始化 SwipeRefreshLayout
        swipeRefreshLayout.init {
            //触发刷新监听时请求数据
            viewModel.getProjectData(true, cid, isNew)
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
//                                putInt("id", articleAdapter.data[position].userId)
//                            })
//                    }
                }
            }
            //加载更多
            loadMoreModule.setOnLoadMoreListener {
                viewModel.getProjectData(false, cid, isNew)
            }
        }
    }

    override fun lazyLoadData() {
        loadsir.showLoading()
        viewModel.getProjectData(true, cid, isNew)
    }

    override fun createObserver() {
        viewModel.projectDataState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, articleAdapter, loadsir, swipeRefreshLayout)
        })
        collectViewModel.collectUiState.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
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
//            eventViewModel.collectEvent.observeInFragment(this@ProjectChildFragment, Observer {
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

    companion object {
        fun newInstance(cid: Int, isNew: Boolean): ProjectChildFragment {
            val args = Bundle()
            args.putInt("cid", cid)
            args.putBoolean("isNew", isNew)
            val fragment = ProjectChildFragment()
            fragment.arguments = args
            return fragment
        }
    }
}