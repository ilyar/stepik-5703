package org.stepik.a5703.myapplication

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

const val URL_FEED: String =
    "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.jpl.nasa.gov%2Fmultimedia%2Frss%2Fnews.xml"

class MainActivity : AppCompatActivity() {

    lateinit var vItems: RecyclerView
    var request: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("live", "MainActivity.onCreate")
        setContentView(R.layout.activity_main)

        vItems = findViewById<RecyclerView>(R.id.activity_main__list)
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
        vItems.adapter = Adapter(feedList)
        vItems.layoutManager = LinearLayoutManager(this)
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

class Adapter(val items: ArrayList<FeedItem>) : RecyclerView.Adapter<RecHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.activity_main__item, parent, false)

        return RecHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }
}

class RecHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: FeedItem) {
        val vTitle = itemView.findViewById<TextView>(R.id.activity_main__title)
        val vDesc = itemView.findViewById<TextView>(R.id.activity_main__desc)
        val vThumb = itemView.findViewById<ImageView>(R.id.activity_main__thumb)

        vTitle.text = item.title
        vDesc.text = Html.fromHtml(item.description)
        Picasso.get().load(item.thumbnail).into(vThumb);

        itemView.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(item.link)
            vThumb.context.startActivity(i)
        }
    }
}