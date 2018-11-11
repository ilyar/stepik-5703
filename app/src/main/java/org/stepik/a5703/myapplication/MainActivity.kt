package org.stepik.a5703.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

const val URL_FEED: String =
    "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Ffeeds.bbci.co.uk%2Fnews%2Frss.xml"

class MainActivity : AppCompatActivity() {

    lateinit var vList: LinearLayout
    var request: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("live", "MainActivity.onCreate")
        setContentView(R.layout.activity_main)

        vList = findViewById<LinearLayout>(R.id.activity_main__list)
        val o = createRequest(URL_FEED)
            .map { Gson().fromJson(it, Feed::class.java) }
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        request = o.subscribe({
            // result
            Log.d("live", "MainActivity.request.result")
            showLinearLayout(it.items)
        }, {
            // error
            Log.d("live", "MainActivity.request.error:  ${it.message}")
        })
    }

    fun showLinearLayout(feedList: ArrayList<FeedItem>) {
        val inflater = layoutInflater
        for (item in feedList) {
            val view = inflater.inflate(R.layout.activity_main__item, vList, false)
            val vTitle = view.findViewById<TextView>(R.id.activity_main__title)
            vTitle.text = item.title
            vList.addView(view)
        }
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
