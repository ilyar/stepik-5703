package org.stepik.a5703.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

const val URL_FEED: String =
    "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Ffeeds.bbci.co.uk%2Fnews%2Frss.xml"

class MainActivity : AppCompatActivity() {

    lateinit var vListView: ListView
    var request: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("live", "MainActivity.onCreate")
        setContentView(R.layout.activity_main)

        vListView = findViewById<ListView>(R.id.activity_main__list)
        val o = createRequest(URL_FEED)
            .map { Gson().fromJson(it, Feed::class.java) }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        request = o.subscribe({
            // result
            Log.d("live", "MainActivity.request.result")
            showListView(it.items)
        }, {
            // error
            Log.d("live", "MainActivity.request.error:  ${it.message}")
        })
    }

    fun showListView(feedList: ArrayList<FeedItem>) {
        vListView.adapter = Adapter(feedList)
    }

    override fun onStart() {
        super.onStart()
        Log.d("live", "MainActivity.onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("live", "MainActivity.onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("live", "MainActivity.onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("live", "MainActivity.onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("live", "MainActivity.onDestroy")
    }
}

class Feed(
    val items: ArrayList<FeedItem>
)

class FeedItem(
    val title: String,
    val link: String,
    val thumbnail: String,
    val description: String
)

class Adapter(val items: ArrayList<FeedItem>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(parent!!.context)

        val view = convertView ?: inflater.inflate(R.layout.activity_main__item, parent, false)
        val vTitle = view.findViewById<TextView>(R.id.activity_main__title)

        val item = getItem(position) as FeedItem

        vTitle.text = item.title

        return view
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}