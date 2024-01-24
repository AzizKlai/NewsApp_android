package com.example.newsapp_android.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp_android.NewsBagApplication
import com.example.newsapp_android.models.Article
import com.example.newsapp_android.models.NewsResponse
import com.example.newsapp_android.repository.NewsRepository
import com.example.newsapp_android.utils.Constants.Companion.msg_NO_INTERNET
import com.example.newsapp_android.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(
    app: Application,
    val newsRepository: NewsRepository
) : AndroidViewModel(app){
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()

    //Pagination
    var breakingNewsPage= 1
    var searchNewsPage= 1
    var breakingNewsResponse : NewsResponse? = null
    var searchNewsResponse : NewsResponse? = null


    init {
        getBreakingNews("in")
    }
    fun refresh(){
        //reset pages
        breakingNewsPage= 1
        searchNewsPage= 1
        breakingNewsResponse =null
        searchNewsResponse = null
        getBreakingNews("in")
    }



    fun getBreakingNews(countryCode: String)= viewModelScope.launch {
       safeBreakingNewsCall(countryCode)
    }


    fun searchNews(searchQuery: String)= viewModelScope.launch {
        safeSearchNewsCall(searchQuery)

    }


    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse== null){
                    breakingNewsResponse= resultResponse
                }else{
                    val oldArticles= breakingNewsResponse?.articles
                    val newArticle= resultResponse.articles
                    oldArticles?.addAll(newArticle)
                }
                return  Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse== null){
                    searchNewsResponse= resultResponse
                }else{
                    val oldArticles= searchNewsResponse?.articles
                    val newArticle= resultResponse.articles
                    oldArticles?.addAll(newArticle) //add the new articles to old articles
                }
                return  Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    fun saveArticle(article: Article)= viewModelScope.launch {
        newsRepository.upsert(article)
    }


    fun getSavedArticle()= newsRepository.getSavedNews()


    fun deleteSavedArticle(article: Article)= viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    private suspend fun safeBreakingNewsCall(countryCode: String){
        breakingNews.postValue(Resource.Loading())
        try{
            if (hasInternetConnection()){
                val response= newsRepository.getHeadlineNews(countryCode, breakingNewsPage)

                breakingNews.postValue(handleBreakingNewsResponse(response))
            }else{
                breakingNews.postValue(Resource.Error(msg_NO_INTERNET))
            }

        } catch (t: Throwable){
            when(t){
                is IOException-> breakingNews.postValue(Resource.Error("Network Failure"))
                else-> breakingNews.postValue(Resource.Error("Conversion Failure"))
            }
        }
    }

    private suspend fun safeSearchNewsCall(searchQuery: String){
        searchNews.postValue(Resource.Loading())
        try{
            if (hasInternetConnection()){
                val response= newsRepository.searchNews(searchQuery, searchNewsPage)
                //handling response
                searchNews.postValue(handleSearchNewsResponse(response))
            }else{
                searchNews.postValue(Resource.Error(msg_NO_INTERNET))
            }

        } catch (t: Throwable){
            when(t){
                is IOException-> searchNews.postValue(Resource.Error("Network Failure"))
                else-> searchNews.postValue(Resource.Error("Conversion Failure"))
            }
        }
    }


    private fun hasInternetConnection(): Boolean{
        val connectivityManager= getApplication<NewsBagApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork= connectivityManager.activeNetwork?: return false
        val capabilities= connectivityManager.getNetworkCapabilities(activeNetwork)?: return false

        return when{
            capabilities.hasTransport(TRANSPORT_WIFI)-> true
            capabilities.hasTransport(TRANSPORT_CELLULAR)-> true
            capabilities.hasTransport(TRANSPORT_ETHERNET)->true
            else -> false
        }
    }
}