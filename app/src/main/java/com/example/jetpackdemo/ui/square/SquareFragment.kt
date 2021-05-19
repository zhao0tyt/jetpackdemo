package com.example.jetpackdemo.ui.square

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.FragmentViewpagerBinding
import com.example.jetpackdemo.ext.bindViewPager2
import com.example.jetpackdemo.ext.init
import com.example.jetpackdemo.ui.base.BaseFragment
import com.zzq.common.base.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.include_viewpager.*

class SquareFragment : BaseFragment<BaseViewModel, FragmentViewpagerBinding>() {
    var titleData = arrayListOf("广场", "每日一问", "体系", "导航")

    private var fragments: ArrayList<Fragment> = arrayListOf()

    init {
        fragments.add(SquareChildFragment())
        fragments.add(AskFragment())
        fragments.add(SystemFragment())
        fragments.add(NavigationFragment())
    }

    override fun layoutId() = R.layout.fragment_viewpager

    override fun initView(savedInstanceState: Bundle?)  {
        //初始化viewpager2
        view_pager.init(this, fragments).offscreenPageLimit = fragments.size
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager, mStringList = titleData) {
        }
    }

    override fun lazyLoadData() {

    }

    override fun createObserver() {
    }

}