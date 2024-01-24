package com.example.newsapp_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.example.newsapp_android.R
import com.example.newsapp_android.adapters.NewsAdapter
import com.example.newsapp_android.databinding.FragmentSavedNewsBinding
import com.example.newsapp_android.MainActivity
import com.example.newsapp_android.viewModel.NewsViewModel

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    private lateinit var binding: FragmentSavedNewsBinding


    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentSavedNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= (activity as MainActivity).viewModel
        setupRecyclerView()


        newsAdapter.setOnItemClickListener {
            val bundle= Bundle().apply{
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }

        //swipe delete variable
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, //drag
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT // swipe
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position= viewHolder.adapterPosition
                val toDeleteArticle= newsAdapter.dataSet[position]
                viewModel.deleteSavedArticle(toDeleteArticle)

                Snackbar.make(view, " Article Deleted Successfully ", Snackbar.LENGTH_LONG).apply {
                    setAction("undo") {
                        viewModel.saveArticle(toDeleteArticle)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }

        //subscribe on live data
        viewModel.getSavedArticle().observe(viewLifecycleOwner, Observer { articles ->
            newsAdapter.updateList(articles) //update list
        })
    }


    private fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}