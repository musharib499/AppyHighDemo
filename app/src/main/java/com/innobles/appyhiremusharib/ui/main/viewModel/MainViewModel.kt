package com.innobles.appyhiremusharib.ui.main.viewModel

import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.provider.BaseColumns
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.lifecycle.viewModelScope
import com.innobles.appyhiremusharib.BuildConfig
import com.innobles.appyhiremusharib.R
import com.innobles.appyhiremusharib.databinding.MainFragmentBinding
import com.innobles.appyhiremusharib.myutils.*
import com.innobles.appyhiremusharib.networkcall.module.NewsFeedResponse
import com.innobles.appyhiremusharib.networkcall.module.SearchResult
import com.innobles.appyhiremusharib.networkcall.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val mainRepository: MainRepository, private val utils: Utils) : ViewModel(){

    private val mArticle = MutableLiveData<Resource<NewsFeedResponse>>()
    val article: LiveData<Resource<NewsFeedResponse>> get() =  mArticle

 init {
     fetchArticle()
 }

      fun fetchArticle(country:String = "in", category:String = "",search:String = "") {
        val param = hashMapOf<String,String>(COUNTRY_KEY to country, CATEGORY_KEY to category,"q" to search,
            API_KEY to BuildConfig.SERVER_KEY)
        viewModelScope.launch {
            mArticle.postValue(Resource.loading(null))
            if (utils.isNetworkConnected()) {
                mainRepository.getArticle(param).let {
                    if (it.isSuccessful) {
                        if (it.body()?.status == "ok") {
                            mArticle.postValue(Resource.success(it.body()))
                        }else{
                            mArticle.postValue(Resource.error(it.body()?.message?:"", null))
                        }
                    } else {
                        mArticle.postValue(Resource.error(it.errorBody().toString(), null))
                    }

                }
            } else
                mArticle.postValue(Resource.error("No internet connection", null)
                )
        }
    }



}