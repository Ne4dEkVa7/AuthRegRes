package com.example.authregres.Adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.authregres.Data.Item
import com.example.authregres.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.Shapeable

class CartAdapter (
    itemView: View,
    private val onHeaderImageClick: (Item) -> Unit,
    private val onDeleteClick: (Item) -> Unit,
    private val onIncreaseQuantity: (Item) -> Unit,
    private val onDecreaseQuantity: (Item) -> Unit
): RecyclerView.ViewHolder(itemView) {
    private val title: TextView = itemView.findViewById(R.id.title)
    private val price: TextView = itemView.findViewById(R.id.subhead)
    private val headerImage: ShapeableImageView = itemView.findViewById(R.id.header_image)
    private val deleteButton: ShapeableImageView = itemView.findViewById(R.id.delete_from_cart)
    private val quantityText: TextView = itemView.findViewById(R.id.text_quantity)
    private val increaseButton: ShapeableImageView = itemView.findViewById(R.id.button_increase)
    private val decreaseButton: ShapeableImageView = itemView.findViewById(R.id.button_decrease)
    fun bind(item: Item) {
        title.text = item.title
        price.text = item.price
        headerImage.setImageResource(item.imageRestId)
        quantityText.text = item.quantity.toString()
        headerImage.setOnClickListener {
            onHeaderImageClick(item)
        }
        deleteButton.setOnClickListener {
            onDeleteClick(item)
        }
        increaseButton.setOnClickListener {
            onIncreaseQuantity(item)
        }

        decreaseButton.setOnClickListener {
            onDecreaseQuantity(item)
        }
    }
}


