package org.stepik.a5703.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

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
            val i = Intent(this, InnerActivity::class.java)
            i.putExtra("textFromMain", vText.text)

            startActivityForResult(i, 0) // startActivity(i)
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
