package com.example.marvel_comics

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    private lateinit var comicList: MutableList<String>
    private lateinit var comicNameList: MutableList<String>
    private lateinit var comicDes: MutableList<String>
    private lateinit var rvComics: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvComics = findViewById(R.id.comic_list)
        comicList = mutableListOf()
        comicNameList = mutableListOf()
        comicDes = mutableListOf()

        getMarvelComicURL()
    }

    private val publicKey = "ee844cdd9e062392fa43d3f76a0feaff"
    private val hash = "b20561998053f17e3aa09a7b26adc5c4"
    private val url =
        "https://gateway.marvel.com:443/v1/public/comics?dateDescriptor=lastWeek&limit=20&ts=1&apikey=$publicKey&hash=$hash"
    private fun getMarvelComicURL() {
        val client = AsyncHttpClient()

        client[url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Marvel Comic Success", "$json")

                for (i in 0 until 20) {
                    val thumbnail = json.jsonObject.getJSONObject("data").getJSONArray("results").getJSONObject(i).getJSONObject("thumbnail")
                    val path = thumbnail.getString("path").addCharAtIndex('s', 4)
                    val comicImageURL = "${path}.${thumbnail.getString("extension")}"
                    comicList.add(comicImageURL)
                    val name = json.jsonObject.getJSONObject("data").getJSONArray("results").getJSONObject(i).getString("title")
                    val description = json.jsonObject.getJSONObject("data").getJSONArray("results").getJSONObject(i).getString("description")
                    comicNameList.add(name)
                    comicDes.add(description)

                }
                val adapter = ComicAdapter(comicList, comicNameList, comicDes)
                rvComics.adapter = adapter
                rvComics.layoutManager = LinearLayoutManager(this@MainActivity)

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Marvel Error", errorResponse)
            }
        }]
    }
    fun String.addCharAtIndex(char: Char, index: Int) =
        StringBuilder(this).apply { insert(index, char) }.toString()
}