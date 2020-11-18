package com.example.jetpackdemo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.FragmentMainBinding
import com.example.jetpackdemo.ext.init
import com.example.jetpackdemo.ext.initMain
import com.example.jetpackdemo.ui.base.BaseFragment
import com.zzq.common.base.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<BaseViewModel, FragmentMainBinding>() {


    override fun layoutId() = R.layout.fragment_main

    override fun initView(savedInstanceState: Bundle?) {
        //初始化viewpager2
        mainViewpager.initMain(this)
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
}