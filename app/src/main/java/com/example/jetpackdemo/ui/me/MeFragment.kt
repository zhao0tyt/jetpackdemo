package com.example.jetpackdemo.ui.me

import android.os.Bundle
import android.util.Log
import com.example.jetpackdemo.R
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.databinding.FragmentMeBinding
import com.example.jetpackdemo.ext.jumpByLogin
import com.zzq.common.ext.nav

class MeFragment : BaseFragment<MeViewModel, FragmentMeBinding>() {
    override fun layoutId(): Int {
        return R.layout.fragment_me
    }

    override fun initView(savedInstanceState: Bundle?) {
        Log.d("zzq","initView")
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
//        mDatabind.apply {
//            vm = mViewModel
//            click = ProxyClick()
//        }
    }

    inner class ProxyClick {
        /** 登录 */
        fun login() {
            Log.d("zzq","login");
            nav().jumpByLogin {}
        }
    }
}


