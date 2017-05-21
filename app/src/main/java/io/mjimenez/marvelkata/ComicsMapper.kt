package io.mjimenez.marvelkata

class ComicsMapper {
    fun map(comic: ComicsResponse) = comic.data.results.map {
        Marvel.Comic(it.title, it.prices.first().price.toString(), it.thumbnail.url)
    }
}