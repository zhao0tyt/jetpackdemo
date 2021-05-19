package com.example.jetpackdemo.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.jetpackdemo.App.Companion.context
import com.example.jetpackdemo.R
import com.example.jetpackdemo.data.bean.BannerResponse
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class HomeBannerAdapter: BaseBannerAdapter<BannerResponse, HomeBannerViewHolder>() {
    override fun getLayoutId(viewType: Int): Int {
        return R.layout.banner_itemhome
    }

    override fun createViewHolder(itemView: View, viewType: Int): HomeBannerViewHolder {
        return HomeBannerViewHolder(itemView, context);
    }

    override fun onBind(
        holder: HomeBannerViewHolder?,
        data: BannerResponse?,
        position: Int,
        pageSize: Int
    ) {
        holder?.bindData(data, position, pageSize);
    }


}


class HomeBannerViewHolder(view: View, context: Context) : BaseViewHolder<BannerResponse>(view) {
    override fun bindData(data: BannerResponse?, position: Int, pageSize: Int) {
        val img = itemView.findViewById<ImageView>(R.id.bannerhome_img)
        data?.let {
            Glide.with(context)
                .load(it.imagePath)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(img)
        }
    }

}