package com.example.jetpackdemo.ui.square

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.jetpackdemo.R
import com.example.jetpackdemo.data.bean.SystemResponse
import com.example.jetpackdemo.databinding.FragmentSystemBinding
import com.example.jetpackdemo.ext.bindViewPager2
import com.example.jetpackdemo.ext.init
import com.example.jetpackdemo.ext.initClose
import com.example.jetpackdemo.ui.base.BaseFragment
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.nav
import kotlinx.android.synthetic.main.include_viewpager.*
import kotlinx.android.synthetic.main.toolbar.*

class SystemArrFragment: BaseFragment<BaseViewModel, FragmentSystemBinding>()  {
    lateinit var data: SystemResponse

    var index = 0

    private var fragments: ArrayList<Fragment> = arrayListOf()

    override fun layoutId() = R.layout.fragment_system

    override fun initView(savedInstanceState: Bundle?)  {
        arguments?.let {
            data = it.getParcelable("data")!!
            index = it.getInt("index")
        }
        toolbar.initClose(data.name) {
            nav().navigateUp()
        }
        //设置栏目标题居左显示
        (magic_indicator.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.LEFT

    }

    override fun lazyLoadData() {
        data.children.forEach {
            fragments.add(SystemChildFragment.newInstance(it.id))
        }
        //初始化viewpager2
        view_pager.init(this, fragments)
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager, data.children.map { it.name })

        view_pager.offscreenPageLimit = fragments.size

        view_pager.postDelayed({
            view_pager.currentItem = index
        },100)

    }
}