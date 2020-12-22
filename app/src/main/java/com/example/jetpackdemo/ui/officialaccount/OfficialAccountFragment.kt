package com.example.jetpackdemo.ui.officialaccount

import android.os.Bundle
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.FragmentViewpagerBinding
import com.example.jetpackdemo.ui.base.BaseFragment

class OfficialAccountFragment : BaseFragment<OfficialAccountViewModel, FragmentViewpagerBinding>() {
    override fun layoutId(): Int {
        return R.layout.fragment_viewpager
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun lazyLoadData() {
        super.lazyLoadData()

    }
}