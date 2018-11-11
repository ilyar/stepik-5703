package org.stepik.a5703.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class InnerActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("live", "MainActivity.onCreate")
        setContentView(R.layout.activity_inner)

        val vEdit=findViewById<EditText>(R.id.activity_inner__edit)

        findViewById<Button>(R.id.activity_inner__button).setOnClickListener {
            val newText=vEdit.text.toString()
            val i=Intent()
            i.putExtra("textFromInner", newText)
            setResult(0, i)
            finish()
        }

        val str=intent.getStringExtra("textFromMain")

        vEdit.setText(str)
    }
}