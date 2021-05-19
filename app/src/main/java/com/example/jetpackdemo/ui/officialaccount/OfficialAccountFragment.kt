package com.example.jetpackdemo.ui.officialaccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.FragmentViewpagerBinding
import com.example.jetpackdemo.ext.*
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.util.InjectorUtil
import com.example.jetpackdemo.widget.ErrorCallback
import com.kingja.loadsir.core.LoadService
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.parseState
import kotlinx.android.synthetic.main.include_viewpager.*

class OfficialAccountFragment : BaseFragment<BaseViewModel, FragmentViewpagerBinding>() {

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //fragment集合
    private var fragments: ArrayList<Fragment> = arrayListOf()

    //标题集合
    private var mDataList: ArrayList<String> = arrayListOf()

    private val viewModel: OfficialAccountViewModel by viewModels {
        InjectorUtil.getOfficialAccountViewModelFactory(requireContext())
    }

    override fun layoutId(): Int {
        return R.layout.fragment_viewpager
    }

    override fun initView(savedInstanceState: Bundle?) {
        //初始化viewpager2
        view_pager.init(this,fragments)
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager,mDataList) {
            // magic_indicator title 点击或者被选中的回调
            // Toast.makeText(context,"title click",Toast.LENGTH_SHORT).show()
        }
        //状态页配置
        loadsir = loadServiceInit(view_pager) {
            //点击重试时触发的操作
            loadsir.showLoading()
            viewModel.getOfficialAccountTitle()
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        //设置界面 加载中
        loadsir.showLoading()
        //请求标题数据
        viewModel.getOfficialAccountTitle()

    }

    override fun createObserver() {
        super.createObserver()
        viewModel.titleData.observe(viewLifecycleOwner, Observer { data ->
            parseState(data, {
                mDataList.addAll(it.map { it.name })
                it.forEach { classify ->
                    fragments.add(OfficialAccountChildFragment.newInstance(classify.id))
                }
                magic_indicator.navigator.notifyDataSetChanged()
                view_pager.adapter?.notifyDataSetChanged()
                view_pager.offscreenPageLimit = fragments.size
                loadsir.showSuccess()
            }, {
                //请求项目标题失败
                loadsir.showError(it.errorMsg)
            })
        })
    }
}