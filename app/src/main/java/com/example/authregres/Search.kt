package com.example.authregres

import com.example.authregres.Data.Item

class Search(private val items: List<Item>) {
    fun filter(query: String): List<Item> {

        return items.filter {
            it.title.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
        }
    }
}