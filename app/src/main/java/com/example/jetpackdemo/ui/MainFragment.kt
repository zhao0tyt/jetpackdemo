package com.example.jetpackdemo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.FragmentMainBinding
import com.example.jetpackdemo.ext.init
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.ui.home.HomeFragment
import com.example.jetpackdemo.ui.me.MeFragment
import com.example.jetpackdemo.ui.officialaccount.OfficialAccountFragment
import com.example.jetpackdemo.ui.project.ProjectFragment
import com.example.jetpackdemo.ui.square.SquareFragment
import com.zzq.common.base.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<BaseViewModel, FragmentMainBinding>() {
    //fragment集合
    private var fragments: ArrayList<Fragment> = arrayListOf()

    override fun layoutId() = R.layout.fragment_main

    override fun initView(savedInstanceState: Bundle?) {
        //创建fragment list
        initFragments()
        //初始化viewpager2
        mainViewpager.init(this,fragments).run {
            //是否可滑动
            this.isUserInputEnabled = false
            this.offscreenPageLimit = 5
        }
        //初始化 bottombar
        mainBottom.init{
            when (it) {
                R.id.menu_home -> mainViewpager.setCurrentItem(0, false)
                R.id.menu_project -> mainViewpager.setCurrentItem(1, false)
                R.id.menu_square -> mainViewpager.setCurrentItem(2, false)
                R.id.menu_official -> mainViewpager.setCurrentItem(3, false)
                R.id.menu_me -> mainViewpager.setCurrentItem(4, false)
            }
        }

    }

    fun initFragments(){
        fragments.add(HomeFragment())
        fragments.add(ProjectFragment())
        fragments.add(SquareFragment())
        fragments.add(OfficialAccountFragment())
        fragments.add(MeFragment())
    }
}