package com.compose.spacearticle.presenter.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.compose.spacearticle.R
import com.compose.spacearticle.databinding.ActivityMainBinding
import com.compose.spacearticle.presenter.detail.DetailActivity
import com.compose.spacearticle.presenter.recent.RecentSearchActivity
import com.compose.spacearticle.ui.ArticlePagingAdapter
import com.compose.spacearticle.ui.LoadingStateAdapter
import com.compose.spacearticle.ui.NewsSiteAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterRv: ArticlePagingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterRv = ArticlePagingAdapter()

        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapterRv.withLoadStateFooter(
                footer = LoadingStateAdapter{
                    adapterRv.retry()
                }
            )
        }
        adapterRv.onItemClick = {data ->
            val intent = Intent(applicationContext, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, data)
            startActivity(intent)
        }

        viewModel.articles.observe(this) {
            adapterRv.submitData(lifecycle, it)
        }

        binding.topSec.searchView.setOnQueryTextListener(object :OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.onSearch(query)
                binding.topSec.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    viewModel.onSearch("") // Handle clearing the search logic
                }
                return true
            }
        })

        binding.topSec.ivRecentSearch.setOnClickListener {
            val intent = Intent(this, RecentSearchActivity::class.java)
            startActivity(intent)
        }

        showBottomSheet()
        setDataHandler()


    }

    private fun setDataHandler(){
        adapterRv.addLoadStateListener { combinedLoadState ->
            val loadState = combinedLoadState.refresh

            if(loadState is LoadState.Loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }


            if (loadState is LoadState.NotLoading) {
                viewModel.title.observe(this) { title ->
                    viewModel.newsSite.observe(this) { newsSite ->
                        val shouldShowNotFound =( title.isNotBlank() || newsSite.isNotBlank()) && adapterRv.itemCount < 1
                        binding.notfoundLy.notfoundMain.visibility = if (shouldShowNotFound) View.VISIBLE else View.GONE
                    }
                }
            }

            if(loadState is LoadState.Error){
                binding.errorLy.errorMain.visibility = View.VISIBLE
                binding.errorLy.retryButton.setOnClickListener {
                    adapterRv.retry()
                }
            }else{
                binding.errorLy.errorMain.visibility = View.GONE
            }
        }
    }

    private fun showBottomSheet(){
        binding.topSec.btnNewssite.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this@MainActivity)
            val view1 = layoutInflater.inflate(R.layout.site_bottom_sheet, null)
            bottomSheetDialog.setContentView(view1)

            val recyclerView: RecyclerView = view1.findViewById(R.id.newssite_rv)
            recyclerView.layoutManager = LinearLayoutManager(this)

            val newsSites = resources.getStringArray(R.array.news_sites_array)

            val adapter = NewsSiteAdapter(newsSites)
            adapter.onItemClick = { newsSite ->
                binding.topSec.btnNewssite.text = if(newsSite == "All") "News Site" else newsSite
                viewModel.onNewSiteChanges(if (newsSite == "All") "" else newsSite)
                bottomSheetDialog.dismiss()
            }

            recyclerView.adapter = adapter

            bottomSheetDialog.show()



        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.option_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.search_menu->{
//                val intent = Intent(this, RecentSearchActivity::class.java)
//                startActivity(intent)
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

//    override fun onResume() {
//        super.onResume()
//        val newsSite = resources.getStringArray(R.array.news_sites_array)
//        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, newsSite)
//        binding.topSec.SpinnerAutoComplete.setAdapter(arrayAdapter)
//
//    }
}