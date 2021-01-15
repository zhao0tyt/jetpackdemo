package com.example.jetpackdemo.ui.me

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.example.jetpackdemo.R
import com.example.jetpackdemo.data.bean.IntegralResponse
import com.example.jetpackdemo.databinding.FragmentMeBinding
import com.example.jetpackdemo.ext.jumpByLogin
import com.example.jetpackdemo.ui.base.BaseFragment
import com.example.jetpackdemo.util.InjectorUtil
import com.zzq.common.base.viewmodel.BaseViewModel
import com.zzq.common.ext.nav
import com.zzq.common.ext.navigateAction
import com.zzq.common.ext.parseState
import com.zzq.common.ext.util.notNull
import com.zzq.common.util.LogUtil

class MeFragment : BaseFragment<BaseViewModel, FragmentMeBinding>(){
    private val WANANDROID_KEY = "wanandroid"
    private val WANANDROID_URL = "https://www.wanandroid.com/"
    private var integralResponse: IntegralResponse? = null

    private val meViewModel: MeViewModel by viewModels {
        InjectorUtil.getMeViewModelFactory(requireContext())
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
    }

    override fun lazyLoadData() {
        //用户信息不为空时调用获取积分接口
        appViewModel.userinfo.value?.run {
            meViewModel.getIntegral(appViewModel.userinfo.value!!.id)
        }
    }

    override fun createObserver() {
        meViewModel.meData.observe(viewLifecycleOwner, Observer {resultState ->
            parseState(resultState, {
                integralResponse = it
                meViewModel.info.value = "id：${it.userId}　排名：${it.rank}"
                meViewModel.integral.value = it.coinCount
            }, {
                ToastUtils.showShort(it.errorMsg)
            })
        })

        appViewModel.run {
            userinfo.observe(viewLifecycleOwner, Observer {
                it.notNull({
                    meViewModel.name.value = it.username
                    meViewModel.getIntegral(appViewModel.userinfo.value!!.id)
                }, {
                    meViewModel.name.value = "请先登录~"
                    meViewModel.info.value = "id：--　排名：--"
                    meViewModel.integral.value = "0"
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

        /** 积分点击事件 */
        fun integral() {
            nav().jumpByLogin {
                it.navigateAction(R.id.action_mainfragment_to_integralFragment,
                    Bundle().apply {
                        putParcelable("rank", integralResponse)
                    }
                )
            }
        }

        /** 玩Android开源网站 */
        fun about() {
            nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                putString(WANANDROID_KEY, WANANDROID_URL)
            })
        }
    }

}


