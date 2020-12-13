package com.example.jetpackdemo.ui.integral

import androidx.paging.PagingSource
import com.example.jetpackdemo.data.model.IntegralResponse
import com.example.jetpackdemo.data.repository.AppRepository


class IntegralPagingSource(private val appRepository: AppRepository): PagingSource<Int, IntegralResponse>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, IntegralResponse> {
        // Api接口是从第一页开始获取数据
        // 如果key是null，那就加载第1页的数据
        val page = params.key ?: 1
        val response = appRepository.getIntegralRank(page)
        // 如果成功加载，那么返回一个LoadResult.Page,如果失败就返回一个Error
        // Page里传进列表数据，以及上一页和下一页的页数,具体的是否最后一页或者其他逻辑就自行判断
        // 需要注意的是，如果是第一页，prevKey就传null，如果是最后一页那么nextKey也传null
        // 其他情况prevKey就是page-1，nextKey就是page+1
        return try {
            LoadResult.Page(
                data = response.data.datas,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (!response.data.hasMore()) null else page + 1
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }

    }

}