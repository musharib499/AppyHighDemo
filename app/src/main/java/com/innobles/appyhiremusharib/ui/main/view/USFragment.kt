package com.innobles.appyhiremusharib.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.innobles.appyhiremusharib.R
import com.innobles.appyhiremusharib.databinding.ItemFeedNewsBinding
import com.innobles.appyhiremusharib.databinding.MainFragmentBinding
import com.innobles.appyhiremusharib.myutils.Status
import com.innobles.appyhiremusharib.myutils.categoryResponse
import com.innobles.appyhiremusharib.networkcall.module.NewsFeedResponse
import com.innobles.appyhiremusharib.networkcall.module.SearchResult
import com.innobles.appyhiremusharib.ui.main.view.adapter.BaseAdapterBinding
import com.innobles.appyhiremusharib.ui.main.viewModel.MainViewModel
import com.innobles.appyhiremusharib.ui.main.viewModel.USViewModel


class USFragment : Fragment(), BaseAdapterBinding.BindAdapterListener {

    companion object {
        fun newInstance() = USFragment()
    }

    private lateinit var viewModel: USViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var baseAdapterBinding: BaseAdapterBinding<NewsFeedResponse.Article?>
    private val searchResultsQueue = ArrayDeque<SearchResult>()
    private var currentSearchResult: SearchResult? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        setUp()
        return binding.root
    }

    private fun setUp(){
        viewModel = activity?.let { ViewModelProvider(it).get(USViewModel::class.java) }!!
        setLiveData()
        setCategoryData()
        onSearching()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun setLiveData() {
        baseAdapterBinding = BaseAdapterBinding(
            activity?.baseContext!!,
            arrayListOf(),
            this,
            R.layout.item_feed_news
        )
        binding.recyclerView.adapter = baseAdapterBinding
        viewModel.article.observe(viewLifecycleOwner, {
            binding.search.clearSearch()
            when (it.status) {

                Status.LOADING -> {
                    binding.loading = true
                }
                Status.SUCCESS -> {
                    if (it.data?.articles != null && it.data.articles.isNotEmpty()) {
                        baseAdapterBinding.setData(it.data.articles)
                        baseAdapterBinding.notifyDataSetChanged()
                        binding.error = false
                    } else {
                        binding.error = true
                        binding.message = "No Result found"
                    }
                    binding.loading = false

                }
                Status.ERROR -> {
                    binding.loading = false
                    binding.error = true
                    binding.message = it.message
                }
            }
        })

    }


    private fun onSearching() {
        binding.search.setOnSearchListener {
            val searchResultFor = "Result for $it"
            currentSearchResult?.let { c -> searchResultsQueue.add(c) } ?: kotlin.run {
                currentSearchResult = SearchResult(it, searchResultFor)
            }
            viewModel.fetchArticle(search = it)
        }

    }


    @SuppressLint("InflateParams")
    private fun setCategoryData() {
        for (item in categoryResponse.category) {
            val chip: Chip =
                layoutInflater.inflate(R.layout.item_categorie_tag, null, false) as Chip
            chip.setOnCheckedChangeListener { _, _ ->
                item.key?.let { viewModel.fetchArticle(category = it) }

            }
            chip.text = item.value
            binding.chipGroup.addView(chip)

        }

    }

    override fun onBind(holder: BaseAdapterBinding.DataBindingViewHolder, position: Int) {
        when (holder.binding) {
            is ItemFeedNewsBinding -> {
                holder.binding.item = baseAdapterBinding.getItem(position)
            }

        }

    }


}