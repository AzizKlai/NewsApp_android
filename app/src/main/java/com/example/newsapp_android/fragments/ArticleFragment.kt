package com.example.newsapp_android.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.example.newsapp_android.R
import com.example.newsapp_android.databinding.FragmentArticleBinding
import com.example.newsapp_android.MainActivity
import com.example.newsapp_android.viewModel.NewsViewModel

class ArticleFragment : Fragment(R.layout.fragment_article){
    private lateinit var binding: FragmentArticleBinding
    lateinit var viewModel: NewsViewModel

    private val args: com.example.newsapp_android.fragments.ArticleFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = FragmentArticleBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= (activity as MainActivity).viewModel


        var article=args.article
        val articleUrl = article.url.toString()

        val webView=binding.webView
        val settings: WebSettings = webView.settings
        settings.allowContentAccess=true
        settings.javaScriptCanOpenWindowsAutomatically=true
        settings.offscreenPreRaster=true
        //settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true

        webView.apply {
            webViewClient=object : WebViewClient() {
                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    Log.e("error", error.toString())
                }}
            webViewClient= WebViewClient()
            loadUrl(articleUrl.toString())
        }

        binding.fab.setOnClickListener{
            viewModel.saveArticle(article)
            Snackbar.make(view, " Article Saved Successfully! ", Snackbar.LENGTH_SHORT).show()
        }


    }
}