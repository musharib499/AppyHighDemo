package com.innobles.appyhiremusharib.networkcall.api

import com.innobles.appyhiremusharib.networkcall.module.NewsFeedResponse
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Musharib Ali on 31/10/20.
 * I.S.T Pvt. Ltd
 * musharib.ali@innobles.com
 */
class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getArticle(param: HashMap<String, String>): Response<NewsFeedResponse> =
        apiService.getArticle(param)

    override suspend fun getArticleSearch(param: HashMap<String, String>): Response<NewsFeedResponse> =
        apiService.getArticleSearch(param)

}