package com.example.jetpackdemo.ui.me

import android.os.Bundle
import android.util.Log
import com.example.jetpackdemo.R
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.databinding.FragmentMeBinding

class MeFragment : BaseFragment<MeViewModel, FragmentMeBinding>() {
    override fun layoutId(): Int {
        return R.layout.fragment_me
    }

    override fun initView(savedInstanceState: Bundle?) {
        Log.d("zzq","initView")
        mDatabind.vm = mViewModel
    }


}