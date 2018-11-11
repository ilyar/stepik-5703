package org.stepik.a5703.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

const val URL_FEED: String =
    "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Ffeeds.bbci.co.uk%2Fnews%2Frss.xml"

class MainActivity : AppCompatActivity() {

    lateinit var vText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("live", "MainActivity.onCreate")
        setContentView(R.layout.activity_main)

        vText = findViewById<TextView>(R.id.activity_main__text)
        vText.setTextColor(0xFFFFF0000.toInt())
        vText.setOnClickListener {
            Log.d("live", "MainActivity.vText.setOnClickListener")

            val o = createRequest(URL_FEED)
                .map { Gson().fromJson(it, Feed::class.java) }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

            o.subscribe({
                // result
                Log.d("live", "MainActivity.request.result")
                for (item in it.items) {
                    Log.d("live", "title: ${item.title}")
                }
            }, {
                // error
                Log.d("live", "MainActivity.request.error:  ${it.message}")
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("live", "MainActivity.onActivityResult $requestCode $resultCode")

        if (data !== null) {
            val str = data.getStringExtra("textFromInner")

            vText.text = str
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
