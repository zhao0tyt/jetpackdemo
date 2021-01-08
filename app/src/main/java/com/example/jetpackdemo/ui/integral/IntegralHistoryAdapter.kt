package com.example.jetpackdemo.ui.integral

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.jetpackdemo.R
import com.example.jetpackdemo.data.bean.IntegralHistoryResponse
import com.example.jetpackdemo.util.DatetimeUtil

class IntegralHistoryAdapter(data: ArrayList<IntegralHistoryResponse>) : BaseQuickAdapter<IntegralHistoryResponse, BaseViewHolder>(
    R.layout.item_integral_history, data) {

    override fun convert(holder: BaseViewHolder, item: IntegralHistoryResponse) {
        //赋值
        item.run {
            val descStr =
                if (desc.contains("积分")) desc.subSequence(desc.indexOf("积分"), desc.length) else ""
            holder.setText(R.id.item_integralhistory_title, reason + descStr)
            holder.setText(
                R.id.item_integralhistory_date,
                DatetimeUtil.formatDate(date, DatetimeUtil.DATE_PATTERN_SS)
            )
            holder.setText(R.id.item_integralhistory_count, "+$coinCount")
        }
    }
}