package com.example.newsapp_android.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp_android.R
import com.example.newsapp_android.models.Article
import com.squareup.picasso.Picasso

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    var dataSet : List<Article>  = listOf<Article>();
    inner class ArticleViewHolder(view: View):RecyclerView.ViewHolder(view){
        val ivArticleImage: ImageView
        val tvSource: TextView
        val tvTitle: TextView
        val tvDescription: TextView
        val tvPublishedAt: TextView

        init {
            ivArticleImage =view.findViewById(R.id.ivArticleImage)
            tvSource =view.findViewById(R.id.tvSource)
            tvTitle =view.findViewById(R.id.tvTitle)
            tvDescription =view.findViewById(R.id.tvDescription)
            tvPublishedAt =view.findViewById(R.id.tvPublishedAt)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article= dataSet[position]
        holder.itemView.apply {
            Picasso.get().load(article.urlToImage).into(holder.ivArticleImage)
            holder.tvSource.text= article.source?.name
            holder.tvTitle.text= article.title
            holder.tvDescription.text= article.description
            holder.tvPublishedAt.text= article.publishedAt
            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    private var onItemClickListener:((Article)->Unit)?=null

    fun setOnItemClickListener(listener: (Article)->Unit){
        onItemClickListener= listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(dataList: List<Article>){
        this.dataSet = dataList;
        notifyDataSetChanged();
        Log.d("datalist",dataList.toString())
    }

    override fun getItemCount()= dataSet.size
}
