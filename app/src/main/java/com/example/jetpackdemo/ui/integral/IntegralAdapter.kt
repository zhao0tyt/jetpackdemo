package com.example.jetpackdemo.ui.integral

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.jetpackdemo.R
import com.example.jetpackdemo.data.bean.IntegralResponse

//class IntegralAdapter: PagingDataAdapter <IntegralResponse, RecyclerView.ViewHolder>(REPO_COMPARATOR) {
//
//    companion object {
//        private val     REPO_COMPARATOR = object : DiffUtil.ItemCallback<IntegralResponse>() {
//            override fun areItemsTheSame(oldItem: IntegralResponse, newItem: IntegralResponse) =
//                oldItem.userId == newItem.userId
//
//            override fun areContentsTheSame(oldItem: IntegralResponse, newItem: IntegralResponse) =
//                oldItem == newItem
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        (holder as? IntegralViewHolder)?.tvName?.text = getItem(position)?.username
//        (holder as? IntegralViewHolder)?.tvRank?.text = getItem(position)?.rank
//        (holder as? IntegralViewHolder)?.tvCount?.text = getItem(position)?.coinCount
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return IntegralViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_integral,parent,false))
//    }
//
//    class IntegralViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//        val tvRank: TextView = itemView.findViewById(R.id.item_integral_rank)
//        val tvName: TextView = itemView.findViewById(R.id.item_integral_name)
//        val tvCount: TextView = itemView.findViewById(R.id.item_integral_count)
//    }
//}

/**
 * 积分排行 adapter
 * @Author:         hegaojian
 * @CreateDate:     2019/9/1 9:52
 */
class IntegralAdapter(data: ArrayList<IntegralResponse>):
    BaseQuickAdapter<IntegralResponse, BaseViewHolder>(R.layout.item_integral, data), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: IntegralResponse) {
        //赋值
        item.run {
            holder.setText(R.id.item_integral_rank, "${holder.adapterPosition + 1}")
            holder.setText(R.id.item_integral_name, username)
            holder.setText(R.id.item_integral_count, coinCount.toString())
        }
    }
}