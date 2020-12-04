package com.example.jetpackdemo.ui.me

import android.media.CamcorderProfile
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.jetpackdemo.R
import com.example.jetpackdemo.data.model.Integral
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.databinding.FragmentMeBinding
import com.example.jetpackdemo.ext.init
import com.example.jetpackdemo.ext.jumpByLogin
import com.example.jetpackdemo.ui.login.viewmodel.LoginViewModel
import com.example.jetpackdemo.util.InjectorUtil
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.nav
import com.zzq.common.ext.parseState
import com.zzq.common.ext.util.notNull
import com.zzq.common.util.LogUtil
import kotlinx.android.synthetic.main.fragment_me.*

class MeFragment : BaseFragment<BaseViewModel, FragmentMeBinding>() {

    private var integral: Integral? = null

    private val meViewModel: MeViewModel by viewModels {
        InjectorUtil.getMeViewModelFactory()
    }

    override fun layoutId(): Int {
        return R.layout.fragment_me
    }

    override fun initView(savedInstanceState: Bundle?) {
//        mDatabind.vm = mViewModel
//        mDatabind.click = ProxyClick()
        mDatabind.apply {
            vm = meViewModel
            click = ProxyClick()
        }
        me_swipe.init {
            meViewModel.getIntegral()
        }
    }

    override fun lazyLoadData() {
        //用户信息不为空时调用获取积分接口
        appViewModel.userinfo.value?.run {
            me_swipe.isRefreshing = true
            LogUtil.logd("zzq, lazyLoadData")
            meViewModel.getIntegral()
        }
    }

    override fun createObserver() {
        meViewModel.meData.observe(viewLifecycleOwner, Observer { resultState ->
            me_swipe.isRefreshing = false
            parseState(resultState, {
                integral = it
                meViewModel.info.postValue("id：${it.userId}　排名：${it.rank}")
                meViewModel.integral.postValue(it.coinCount)
            }, {
                Toast.makeText(context,it.errorMsg,Toast.LENGTH_SHORT).show()
            })
        })


        appViewModel.run {
            appViewModel.userinfo.observe(viewLifecycleOwner, Observer {
                meViewModel.name.value = it.username
            })
            userinfo.observe(viewLifecycleOwner, Observer {
                it.notNull({
                    me_swipe.isRefreshing = true
                    meViewModel.getIntegral()
                }, {
                    meViewModel.name.postValue("请先登录~")
                    meViewModel.info.postValue("id：--　排名：--")
                    meViewModel.integral.postValue("0")
                })
            })
        }
    }


    inner class ProxyClick {
        /** 登录 */
        fun login() {
            nav().jumpByLogin {}
        }

        /** 头像点击事件 */
        fun avatar() {
            nav().jumpByLogin {
                //跳转到拍照或者从相册选择图片
                Toast.makeText(context,"change avatar",Toast.LENGTH_SHORT).show()
            }
        }
    }
}


