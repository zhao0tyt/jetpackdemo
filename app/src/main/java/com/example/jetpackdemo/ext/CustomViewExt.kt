package com.example.jetpackdemo.ext

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.jetpackdemo.R
import com.example.jetpackdemo.data.bean.state.ListDataUiState
import com.example.jetpackdemo.ui.home.HomeFragment
import com.example.jetpackdemo.ui.me.MeFragment
import com.example.jetpackdemo.ui.officialaccount.OfficialAccountFragment
import com.example.jetpackdemo.ui.project.ProjectFragment
import com.example.jetpackdemo.ui.square.SquareFragment
import com.example.jetpackdemo.widget.EmptyCallback
import com.example.jetpackdemo.widget.ErrorCallback
import com.example.jetpackdemo.widget.LoadingCallback
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.zzq.common.util.LogUtil
import kotlinx.android.synthetic.main.include_recyclerview.*

fun ViewPager2.initMain(fragment: Fragment): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = false
    this.offscreenPageLimit = 5
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    return HomeFragment()
                }
                1 -> {
                    return ProjectFragment()
                }
                2 -> {
                    return SquareFragment()
                }
                3 -> {
                    return OfficialAccountFragment()
                }
                4 -> {
                    return MeFragment()
                }
                else -> {
                    return HomeFragment()
                }
            }
        }
        override fun getItemCount() = 5
    }
    return this
}

fun BottomNavigationViewEx.init(navigationItemSelectedAction: (Int) -> Unit): BottomNavigationViewEx {
    enableAnimation(true)
    enableShiftingMode(false)
    enableItemShiftingMode(true)
    setTextSize(12F)
    setOnNavigationItemSelectedListener {
        navigationItemSelectedAction.invoke(it.itemId)
        true
    }
    return this
}

/**
 * 初始化普通的toolbar 只设置标题
 */
fun Toolbar.init(titleStr: String = ""): Toolbar {
    title = titleStr
    return this
}

/**
 * 初始化有返回键的toolbar
 */
fun Toolbar.initClose(
    titleStr: String = "",
    backImg: Int = R.drawable.ic_back,
    onBack: (toolbar: Toolbar) -> Unit
): Toolbar {
    title = titleStr
    setNavigationIcon(backImg)
    setNavigationOnClickListener {
        onBack.invoke(this) }
    return this
}
/**
 * 隐藏软键盘
 */
fun hideSoftKeyboard(activity: Activity?) {
    activity?.let { act ->
        val view = act.currentFocus
        view?.let {
            val inputMethodManager =
                act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}

/**
 * 初始化 SwipeRefreshLayout
 */
fun SwipeRefreshLayout.init(onRefreshListener: () -> Unit) {
    this.run {
        setOnRefreshListener {
            onRefreshListener.invoke()
        }
    }
}

/**
 * 初始化RecyclerView
 */
fun RecyclerView.init(
    layoutManger: RecyclerView.LayoutManager,
    bindAdapter: RecyclerView.Adapter<*>
): RecyclerView {
    layoutManager = layoutManger
    adapter = bindAdapter
    return this
}

/**
 * SmartRefreshLayout
 */
fun SmartRefreshLayout.init(
    header: RefreshHeader,
    footer: RefreshFooter,
    onRefreshListener: () -> Unit,
    OnLoadMoreListener: () -> Unit
):SmartRefreshLayout {
    this.run {
        setRefreshHeader(header)
        setRefreshFooter(footer)
        setOnRefreshListener { onRefreshListener.invoke() }
        setOnLoadMoreListener{OnLoadMoreListener.invoke()}
    }
    return this
}

/**
 * 作者　: hegaojian
 * 时间　: 2020/2/20
 * 描述　:项目中自定义类的拓展函数
 */


fun LoadService<*>.setErrorText(message: String) {
    if (message.isNotEmpty()) {
        this.setCallBack(ErrorCallback::class.java) { _, view ->
            view.findViewById<TextView>(R.id.error_text).text = message
        }
    }
}

/**
 * 设置错误布局
 * @param message 错误布局显示的提示内容
 */
fun LoadService<*>.showError(message: String = "") {
    this.setErrorText(message)
    this.showCallback(ErrorCallback::class.java)
}

/**
 * 设置空布局
 */
fun LoadService<*>.showEmpty() {
    this.showCallback(EmptyCallback::class.java)
}

/**
 * 设置加载中
 */
fun LoadService<*>.showLoading() {
    this.showCallback(LoadingCallback::class.java)
}

fun loadServiceInit(view: View, callback: () -> Unit): LoadService<Any> {
    val loadsir = LoadSir.getDefault().register(view) {
        //点击重试时触发的操作
        callback.invoke()
    }
    loadsir.showSuccess()
    return loadsir
}

/**
 * 加载列表数据
 */
fun <T> loadListData(
    data: ListDataUiState<T>,
    baseQuickAdapter: BaseQuickAdapter<T, *>,
    loadService: LoadService<*>,
    smartRefreshLayout: SmartRefreshLayout
) {
    if (data.isSuccess) {
        //成功
        when {
            //第一页并没有数据 显示空布局界面
            data.isFirstEmpty -> {
                loadService.showEmpty()
            }
            //是第一页
            data.isRefresh -> {
                baseQuickAdapter.setList(data.listData)
                LogUtil.logd("hasmore = "+data.hasMore)
                smartRefreshLayout.finishRefresh(true)
                loadService.showSuccess()
            }
            //不是第一页
            else -> {
                baseQuickAdapter.addData(data.listData)
                LogUtil.logd("hasmore = "+data.hasMore)
                smartRefreshLayout.finishLoadMore(0,true, !data.hasMore)
                loadService.showSuccess()
            }
        }
    } else {
        //失败
        if (data.isRefresh) {
            if (baseQuickAdapter.data.isEmpty()){
                //第一次加载出错，则显示错误界面
                loadService.showError(data.errMessage)
            }
            LogUtil.logd("hasmore = "+data.hasMore)
            smartRefreshLayout.finishRefresh(false)
        } else {
            //加载更多时失败
            LogUtil.logd("hasmore = "+data.hasMore)
            smartRefreshLayout.finishLoadMore(0,false, !data.hasMore)
        }
    }
}