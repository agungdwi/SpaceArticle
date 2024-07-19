package com.compose.spacearticle.presenter.recent

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.compose.spacearticle.R
import com.compose.spacearticle.databinding.ActivityRecentSearchBinding
import com.compose.spacearticle.presenter.detail.DetailActivity
import com.compose.spacearticle.ui.RecentSearchListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecentSearchActivity : AppCompatActivity() {

    private val viewModel: RecentViewModel by viewModel()
    private lateinit var adapter: RecentSearchListAdapter
    private lateinit var binding: ActivityRecentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecentSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Recent Search"

        adapter = RecentSearchListAdapter()

        binding.rvRecent.apply {
            layoutManager = LinearLayoutManager(this@RecentSearchActivity)
            adapter = this@RecentSearchActivity.adapter
        }

        adapter.onItemClick = {data ->
            val intent = Intent(applicationContext, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, data)
            startActivity(intent)
        }

        viewModel.recentArticle.observe(this){ article ->
            if(article.isEmpty()){
                binding.emptyLy.emptyMain.visibility = View.VISIBLE
                binding.rvRecent.visibility = View.GONE
            }else{
                binding.emptyLy.emptyMain.visibility = View.GONE
                adapter.submitList(article)
                binding.rvRecent.visibility = View.VISIBLE
            }


        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.delete_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_menu->{
                viewModel.deleteAll()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}