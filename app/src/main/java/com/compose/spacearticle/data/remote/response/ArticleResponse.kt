package com.compose.spacearticle.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("previous")
	val previous: String? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem>? = null
)

data class ResultsItem(

	@field:SerializedName("summary")
	val summary: String,

	@field:SerializedName("news_site")
	val newsSite: String,

	@field:SerializedName("featured")
	val featured: Boolean,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("published_at")
	val publishedAt: String,

	@field:SerializedName("url")
	val url: String,

)