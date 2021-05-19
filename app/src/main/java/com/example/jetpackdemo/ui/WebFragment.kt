package com.example.jetpackdemo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.jetpackdemo.R
import com.example.jetpackdemo.data.bean.AriticleResponse
import com.example.jetpackdemo.data.bean.BannerResponse
import com.example.jetpackdemo.ext.hideSoftKeyboard
import com.example.jetpackdemo.ext.initClose
import com.just.agentweb.AgentWeb
import com.zzq.common.ext.nav
import com.zzq.common.util.LogUtil
import kotlinx.android.synthetic.main.fragment_web.*
import kotlinx.android.synthetic.main.toolbar.*

class WebFragment: Fragment() {
    private lateinit var mAgentWeb: AgentWeb
    private val INTEGRAL_RULE_KEY = "integral_rule"
    private val WANANDROID_KEY = "wanandroid"
    //标题
    var showTitle: String = ""
    //文章的网络访问路径
    var mUrl: String? = ""
    //是否收藏
    var isCollected = false
    lateinit var mActivity: AppCompatActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 添加Menu
        setHasOptionsMenu(true)
        mActivity = context as AppCompatActivity

        arguments?.run {
            getString(INTEGRAL_RULE_KEY)?.let {
                mUrl = it
                showTitle = "积分规则"
            }
            getString(WANANDROID_KEY)?.let {
                mUrl = it
                showTitle = "玩Android网站"
            }

            getParcelable<AriticleResponse>("ariticleData")?.let{
                mUrl = it.link
            }
            getParcelable<BannerResponse>("bannerdata")?.let{
                mUrl = it.url
            }
        }

        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(webcontent, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(mUrl)

        toolbar.run {
            //设置menu 关键代码
            mActivity.setSupportActionBar(this)
            initClose(showTitle) {
                hideSoftKeyboard(activity)
                mAgentWeb?.let { web ->
                    if (web.webCreator.webView.canGoBack()) {
                        web.webCreator.webView.goBack()
                    } else {
                        nav().navigateUp()
                    }
                }
            }
        }
        //back按键
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mAgentWeb?.let { web ->
                        if (web.webCreator.webView.canGoBack()) {
                            web.webCreator.webView.goBack()
                        } else {
                            nav().navigateUp()
                        }
                    }
                }
            })
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.web_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        //如果收藏了，右上角的图标相对应改变
        context?.let {
            if (isCollected) {
                menu.findItem(R.id.web_collect).icon =
                    ContextCompat.getDrawable(it, R.drawable.ic_collected)
            } else {
                menu.findItem(R.id.web_collect).icon =
                    ContextCompat.getDrawable(it, R.drawable.ic_collect)
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.web_share -> {
                LogUtil.logd("分享")
                //分享
                startActivity(Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "{${showTitle}}:${mUrl}")
                    type = "text/plain"
                }, "分享到"))
            }
            R.id.web_refresh -> {
                //刷新网页
                mAgentWeb?.urlLoader?.reload()
            }
            R.id.web_liulanqi -> {
                //用浏览器打开
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mUrl)))
            }
        }

        return super.onOptionsItemSelected(item)
    }


}