package com.example.jetpackdemo.ui.integral

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jetpackdemo.R
import com.example.jetpackdemo.data.model.Integral
import com.example.jetpackdemo.databinding.FragmentIntegralBinding
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.ui.login.viewmodel.LoginViewModel
import com.example.jetpackdemo.util.InjectorUtil
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.zzq.common.base.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.include_recyclerview.*

class IntegralFragment : BaseFragment<BaseViewModel, FragmentIntegralBinding>() {

    private val viewModel: IntegralViewModel by viewModels {
        InjectorUtil.getIntegralViewModelFactory()
    }

    override fun layoutId() = R.layout.fragment_integral
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = viewModel

    }

    override fun lazyLoadData() {

    }

}