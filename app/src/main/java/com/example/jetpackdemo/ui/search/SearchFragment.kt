package com.example.jetpackdemo.ui.search

import android.os.Bundle
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.FragmentSearchBinding
import com.example.jetpackdemo.ui.base.BaseFragment
import com.zzq.common.base.viewmodel.BaseViewModel

class SearchFragment: BaseFragment<BaseViewModel,FragmentSearchBinding>() {
    override fun layoutId(): Int {
        return R.layout.fragment_search
    }

    override fun initView(savedInstanceState: Bundle?) {
    }
}