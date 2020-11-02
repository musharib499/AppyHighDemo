package com.innobles.appyhiremusharib.ui.main.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innobles.appyhiremusharib.BuildConfig
import com.innobles.appyhiremusharib.myutils.*
import com.innobles.appyhiremusharib.networkcall.module.NewsFeedResponse
import com.innobles.appyhiremusharib.networkcall.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val utils: Utils
) : ViewModel() {

    private val mArticle = MutableLiveData<Resource<NewsFeedResponse>>()
    val article: LiveData<Resource<NewsFeedResponse>> get() = mArticle

    private val usArticle = MutableLiveData<Resource<NewsFeedResponse>>()
    val usarticle: LiveData<Resource<NewsFeedResponse>> get() = usArticle


    init {
    }

    fun fetchArticle(country: String = "in", category: String = "", search: String = "") {
        val param = hashMapOf<String, String>(
            COUNTRY_KEY to country, CATEGORY_KEY to category, "q" to search,
            API_KEY to BuildConfig.SERVER_KEY
        )
        viewModelScope.launch {
            mArticle.postValue(Resource.loading(null))
            if (utils.isNetworkConnected()) {
                mainRepository.getArticle(param).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status == "ok") {
                            mArticle.postValue(Resource.success(it.body()))
                        } else {
                            mArticle.postValue(Resource.error(it.body()?.message ?: "", null))
                        }
                    } else {
                        mArticle.postValue(Resource.error(it.errorBody().toString(), null))
                    }

                }
            } else
                mArticle.postValue(
                    Resource.error("No internet connection", null)
                )
        }
    }

    fun fetchArticleUs(country: String = "us", category: String = "business", search: String = "") {
        val param = hashMapOf<String, String>(
            COUNTRY_KEY to country, CATEGORY_KEY to category, "q" to search,
            API_KEY to BuildConfig.SERVER_KEY
        )
        viewModelScope.launch {
            usArticle.postValue(Resource.loading(null))
            if (utils.isNetworkConnected()) {
                mainRepository.getArticle(param).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status == "ok") {
                            usArticle.postValue(Resource.success(it.body()))
                        } else {
                            usArticle.postValue(Resource.error(it.body()?.message ?: "", null))
                        }
                    } else {
                        usArticle.postValue(Resource.error(it.errorBody().toString(), null))
                    }

                }
            } else
                usArticle.postValue(
                    Resource.error("No internet connection", null)
                )
        }
    }


}