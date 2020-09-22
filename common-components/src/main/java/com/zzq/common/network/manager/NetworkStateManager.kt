package com.zzq.common.network.manager

import com.kunminx.architecture.ui.callback.UnPeekLiveData

/**
 * 描述　: 网络变化管理者
 */
class NetworkStateManager private constructor() {

    val mNetworkStateCallback = UnPeekLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }

}