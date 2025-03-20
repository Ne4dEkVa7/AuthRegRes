package com.example.authregres.Presentation.Home

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.authregres.R

class ItemDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_details_screen)

        val title = intent.getStringExtra("item_title")
        val price = intent.getStringExtra("item_price")
        val description= intent.getStringExtra("item_descriptions")
        val imageResId = intent.getStringExtra("item_image")

        findViewById<TextView>(R.id.details_title).text = title
        findViewById<TextView>(R.id.details_price).text = price
        findViewById<TextView>(R.id.details_description).text = description
        findViewById<TextView>(R.id.details_image).text = imageResId
    }
}