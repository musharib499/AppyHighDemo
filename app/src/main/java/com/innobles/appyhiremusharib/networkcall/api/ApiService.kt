package com.innobles.appyhiremusharib.networkcall.api

import com.innobles.appyhiremusharib.networkcall.module.NewsFeedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by Musharib Ali on 31/10/20.
 * I.S.T Pvt. Ltd
 * musharib.ali@innobles.com
 */
interface ApiService {

    @GET("top-headlines")
    suspend fun getArticle(@QueryMap param: HashMap<String, String>): Response<NewsFeedResponse>

    @GET("everything")
    suspend fun getArticleSearch(@QueryMap param: HashMap<String, String>): Response<NewsFeedResponse>
}