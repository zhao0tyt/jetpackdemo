package com.example.jetpackdemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.jetpackdemo.R
import com.example.jetpackdemo.data.bean.AriticleResponse

class NavigationChildAdapter(data: ArrayList<AriticleResponse>) :
    BaseQuickAdapter<AriticleResponse, BaseViewHolder>(R.layout.flow_layout, data) {

    override fun convert(holder: BaseViewHolder, item: AriticleResponse) {
        holder.setText(R.id.flow_tag,item.title)
    }

}