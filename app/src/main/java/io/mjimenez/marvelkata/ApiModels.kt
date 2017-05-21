package io.mjimenez.marvelkata

data class ComicsResponse(val data: Data)

data class Data(
        val results: List<ComicResponse>
)

data class ComicResponse(
        val title: String,
        val prices: List<Price>,
        val thumbnail: Thumbnail
)

data class Price(val price: Double)

data class Thumbnail(val path: String, val extension: String) {
    val url: String
        get() = "$path.$extension"
}