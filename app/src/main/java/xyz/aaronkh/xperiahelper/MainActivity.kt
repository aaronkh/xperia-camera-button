package xyz.aaronkh.xperiahelper

import android.app.Activity
import android.os.Bundle
import android.widget.Button

class MainActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val button = findViewById<Button>(R.id.button)

        button.text = "Request Shizuku permissions"
        button.setOnClickListener {
            ShizukuUtils.requestPermissions()
        }
    }
}