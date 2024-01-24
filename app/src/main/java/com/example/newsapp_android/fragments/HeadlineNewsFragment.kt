package com.example.newsapp_android.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp_android.R
import com.example.newsapp_android.adapters.NewsAdapter
import com.example.newsapp_android.databinding.FragmentHeadlineNewsBinding
import com.example.newsapp_android.MainActivity
import com.example.newsapp_android.viewModel.NewsViewModel
import com.example.newsapp_android.utils.Constants.Companion.msg_NO_INTERNET
import com.example.newsapp_android.utils.Resource


class HeadlineNewsFragment : Fragment(R.layout.fragment_headline_news) {

    private lateinit var binding: FragmentHeadlineNewsBinding

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentHeadlineNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        fun refresh(){
            binding.imgNoInternet.visibility=View.GONE
            binding.rvBreakingNews.visibility=View.VISIBLE

            //refresh the view model to fetch data again
        viewModel.refresh()
        setupRecyclerView()


        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }

            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }

        //subscribe to news livedata
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    //check null
                    response.data?.let { newsResponse ->
                        newsAdapter.updateList(newsResponse.articles.toList())

                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e("error log", "error: $message")
                        Toast.makeText(activity, "error: $message", Toast.LENGTH_LONG)
                            .show()

                        if(message== msg_NO_INTERNET)
                        {
                            binding.imgNoInternet.visibility=View.VISIBLE
                            binding.rvBreakingNews.visibility=View.INVISIBLE
                        }
                    }
                }
                is Resource.Loading -> {
                }
            }
        })

    }

    binding.swipeToRefresh.setOnRefreshListener {
        refresh()
        binding.swipeToRefresh.isRefreshing=false
    }
        refresh()
    }

    private fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }

}