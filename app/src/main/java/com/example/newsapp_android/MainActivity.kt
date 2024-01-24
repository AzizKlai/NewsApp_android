package com.example.newsapp_android

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp_android.databinding.ActivityMainBinding
import com.example.newsapp_android.db.ArticleDatabase
import com.example.newsapp_android.repository.NewsRepository
import com.example.newsapp_android.viewModel.NewsViewModel
import com.example.newsapp_android.viewModel.NewsViewModelProviderFactory


class MainActivity : AppCompatActivity() {
    private var isdark:Boolean=false
    lateinit var viewModel: NewsViewModel
    lateinit var viewModelProviderFactory: NewsViewModelProviderFactory

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
     try{   super.onCreate(savedInstanceState)
        Thread.sleep(2000)
        installSplashScreen()
        binding=ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)


        val repository = NewsRepository(ArticleDatabase(this))
        viewModelProviderFactory = NewsViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
        val navHostFragment= supportFragmentManager.findFragmentById(R.id.newsNavHostFrag) as NavHostFragment
        val navController= navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)


//         switch_theme.setOnCheckedChangeListener { buttonView, isChecked ->
//             if (isChecked) {
//                 AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//             } else {
//                 AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//             }
//         }


    }catch (e:Exception ) {
        Log.d( "onCreateView", e.toString());
        throw e;
    }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_bar_menu, menu)
        val menuItem: MenuItem = menu!!.findItem(R.id.switch_mode)
        menuItem.setOnMenuItemClickListener { _->
                isdark = if (isdark) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);false
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);true
                }
                true

        }
        return true
    }


}


