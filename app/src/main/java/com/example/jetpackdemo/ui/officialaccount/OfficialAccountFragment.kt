package com.example.jetpackdemo.ui.officialaccount

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.FragmentViewpagerBinding
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.ui.me.MeViewModel
import com.example.jetpackdemo.util.InjectorUtil
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.parseState
import com.zzq.common.util.LogUtil

class OfficialAccountFragment : BaseFragment<BaseViewModel, FragmentViewpagerBinding>() {

    private val viewModel: OfficialAccountViewModel by viewModels {
        InjectorUtil.getOfficialAccountViewModelFactory(requireContext())
    }

    override fun layoutId(): Int {
        return R.layout.fragment_viewpager
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        viewModel.getOfficialAccountTitle()

    }

    override fun createObserver() {
        super.createObserver()
        viewModel.titleData.observe(viewLifecycleOwner, Observer { data ->
            parseState(data,{
                LogUtil.logd("获取数据成功")
            },{},{})
        })
    }
}