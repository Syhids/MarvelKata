package io.mjimenez.marvelkata

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class Marvel(private val publicKey: String, private val privateKey: String) {
    private val mapper = ComicsMapper()

    private val url = "http://gateway.marvel.com:80/v1/public/comics?" +
            "dateDescriptor=nextWeek&" +
            "ts=%s&" +
            "apikey=%s&" +
            "hash=%s"

    fun buildUrl(timestamp: Long) = String.format(url,
            timestamp,
            publicKey,
            hash(timestamp)
    )

    fun hash(timestamp: Long) = HASH.md5("$timestamp" + privateKey + publicKey)

    fun getFutureComics(): List<Comic> {
        val url = buildUrl(System.currentTimeMillis())

        val call = OkHttpClient().newCall(Request.Builder()
                .url(url)
                .build())
        val response = call.execute()
        val json = response.body().string()

        val comicsResponse = Gson().fromJson(json, ComicsResponse::class.java)

        return mapper.map(comicsResponse)
    }

    data class Comic(
            val title: String,
            val price: String,
            val thumbnail: String
    )
}