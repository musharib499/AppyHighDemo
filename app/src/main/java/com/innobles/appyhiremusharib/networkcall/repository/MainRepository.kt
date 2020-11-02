package com.innobles.appyhiremusharib.networkcall.repository

import com.innobles.appyhiremusharib.networkcall.api.ApiHelper
import javax.inject.Inject

/**
 * Created by Musharib Ali on 31/10/20.
 * I.S.T Pvt. Ltd
 * musharib.ali@innobles.com
 */
class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getArticle(param: HashMap<String, String>) = apiHelper.getArticle(param)
    suspend fun getArticleSearch(param: HashMap<String, String>) = apiHelper.getArticle(param)
}