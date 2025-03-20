package com.example.authregres.Presentation.Home.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.authregres.Adapters.ItemAdapter
import com.example.authregres.Data.Item
import com.example.authregres.Filter
import com.example.authregres.Domain.SharedViewModel
import com.example.authregres.Presentation.Home.ItemDetails
import com.example.authregres.R
import com.example.authregres.Search
import org.w3c.dom.Text


class ItemFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var searchEditText: EditText
    private lateinit var originalItems: List<Item>
    private lateinit var search: Search
    private lateinit var filter: Filter
    private lateinit var filterButton: ImageView
    private lateinit var gridChangeButton: ImageView
    private lateinit var sharedViewModel: ViewModel


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item, container, false)

        gridChangeButton = view.findViewById(R.id.recycler_change_grid_button)
        var isClicked = true

        gridChangeButton.setOnClickListener()
        {
            if (isClicked) {
                gridChangeButton.setImageResource(R.drawable.icons_vertical_grid)
                recyclerView.layoutManager = GridLayoutManager(context, 2)
            } else {
                gridChangeButton.setImageResource(R.drawable.icons_horizontal_grid)
                recyclerView.layoutManager = LinearLayoutManager(context)
            }
            isClicked = !isClicked
        }

        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(context)

        originalItems = listOf(
            Item(
                "Nescafe 3 in 1",
                "35",
                "Представляем вам кофе 3 в 1 — идеальное сочетание насыщенного вкуса натурального кофе, нежной сладости сахара и бархатистого сливочного крема. Этот напиток подарит вам заряд бодрости и энергии на весь день!",
                R.drawable.cofe
            ),
            Item(
                "Jagermeister 0.7л",
                "2400",
                "Откройте для себя уникальный вкус Jägermeister, настойки, созданной из 56 натуральных ингредиентов. Этот легендарный напиток сочетает в себе силу трав, корений и специй, собранных в гармонии идеальной формулы.",
                R.drawable.jagermeister
            ),
            Item(
                "Dodge SRT 6.2л",
                "6300000",
                "Представляем Dodge SRT — автомобиль, созданный для тех, кто не боится бросать вызов дорогам и самому себе. Сочетание агрессивного дизайна, невероятной мощности двигателя и инновационных технологий делает этот автомобиль настоящим воплощением скорости и стиля.",
                R.drawable.car
            ),
            Item(
                "Adidas Yeezy 451",
                "4300",
                "Встречайте новые Adidas Yeezy 451 — кроссовки, которые сочетают в себе инновационный дизайн, премиальные материалы и комфорт на высшем уровне. Эти кроссовки созданы для тех, кто стремится быть на шаг впереди моды и технологий.",
                R.drawable.sneakers
            )
        )

        filter = Filter(originalItems)
        search = Search(originalItems)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        adapter = ItemAdapter(
            originalItems,
            onAddToCartClick = { item -> addToCart(item) },
            onDetailsClick = { item -> sharedViewModel.showDetails(requireContext(), item) },
            onHeaderImageClick = { item -> sharedViewModel.showDetails(requireContext(), item) })

        recyclerView.adapter = adapter
        filterButton = view.findViewById(R.id.filter_button)
        filterButton.setOnClickListener() {
            showFilterOptions()
        }
        searchEditText = view.findViewById(R.id.search_edit_text)
        searchEditText.addTextChangedListener(textWatcher)
        return view
    }

    private fun addToCart(item: Item) {
        Toast.makeText(context, "Товар '${item.title}' добавлен в корзину", Toast.LENGTH_LONG)
            .show()
        sharedViewModel.addItem(item)
    }

    private fun showFilterOptions() {
        val options = arrayOf("Дешевле 3000", "Только Adidas", "Только Nescafe", "Сбросить фильтры")
        AlertDialog.Builder(requireContext())
            .setTitle("Выберите фильтр")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        val filteredItems = filter.filterByPrice(3000)
                        adapter.updateItems(filteredItems)
                    }

                    1 -> {
                        val filteredItems = filter.filterByCategory("Adidas")
                        adapter.updateItems(filteredItems)
                    }

                    2 -> {
                        val filteredItems = filter.filterByCategory("Nescafe")
                        adapter.updateItems(filteredItems)
                    }

                    3 -> {
                        val resetItems = filter.resetFilter()
                        adapter.updateItems(resetItems)
                    }
                }
            }
            .setNegativeButton("Отмена", null)
            .show()
    }
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val query = s.toString()
            val filteredItems = search.filter(query)
            adapter.updateItems(filteredItems)
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    fun showDetails(item: Item){
        val intent = Intent(context, ItemDetails::class.java).apply{
            putExtra("item_title", item.title)
            putExtra("item_price", item.price)
            putExtra("item_description", item.description)
            putExtra("item_image", item.imageRestId)
        }
        startActivity(intent)
    }
}







