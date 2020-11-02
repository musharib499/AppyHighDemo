package com.innobles.appyhiremusharib.networkcall.api

import com.innobles.appyhiremusharib.networkcall.module.NewsFeedResponse
import retrofit2.Response

/**
 * Created by Musharib Ali on 31/10/20.
 * I.S.T Pvt. Ltd
 * musharib.ali@innobles.com
 */
interface ApiHelper {
    suspend fun getArticle(param: HashMap<String, String>): Response<NewsFeedResponse>
    suspend fun getArticleSearch(param: HashMap<String, String>): Response<NewsFeedResponse>
}