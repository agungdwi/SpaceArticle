package com.compose.spacearticle.presenter.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.compose.spacearticle.R
import com.compose.spacearticle.Utils.getFirstSentence
import com.compose.spacearticle.Utils.withDateFormat
import com.compose.spacearticle.databinding.ActivityDetailBinding
import com.compose.spacearticle.domain.model.Article

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val detailArcticle = intent.getParcelableExtra<Article>(EXTRA_DATA)
        if (detailArcticle != null) {
            showDetail(detailArcticle)
        }

    }

    private fun showDetail(detail: Article){
        binding.apply {
            tvTitle.text = detail.title
            tvNewsite.text = detail.newsSite
            tvPublishedat.text = getString(R.string.published_on_template, detail.publishedAt.withDateFormat())
            Glide.with(this@DetailActivity)
                .load(detail.imageUrl)
                .placeholder(R.drawable.image_not_found)
                .into(imageView)
            tvSummary.text = getFirstSentence(detail.summary)
            buttonRead.setOnClickListener {
                openArticleUrl(detail.url)
            }
        }
    }

    private fun openArticleUrl(url: String?) {
        if (!url.isNullOrBlank()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } else {
            Toast.makeText(this, "URL not available", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}

