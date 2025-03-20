package com.example.authregres.Presentation.Home.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.authregres.Adapters.ItemAdapter
import com.example.authregres.Data.Item
import com.example.authregres.Filter
import com.example.authregres.Presentation.Home.ItemDetails
import com.example.authregres.R
import com.example.authregres.Search

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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
            if (isClicked){
                gridChangeButton.setImageResource(R.drawable.icons_vertical_grid)
                recyclerView.layoutManager = GridLayoutManager(context, 2)
            }
            else{
                gridChangeButton.setImageResource(R.drawable.icons_horizontal_grid)
                recyclerView.layoutManager = LinearLayoutManager(context)
            }
            isClicked = !isClicked
        }

        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(context)



        originalItems = listOf(
            Item("Jagermeister, 0.7", "2349 руб", "Когда то его давали солдатам в качестве согревающего средства и лекарства от простуды, времена меняются, а его свойства - нет! Его и как прежде можно использовать в качестве согревающего средства со вкусом сиропа от боли в горле.", R.drawable.jagermeister),
            Item("Dodge SRT, Supercahrged 6,2л", "6500000 руб", "Оставаясь верным своим корням, демонстрируя производительность мирового класса и мощь бренда Dodge, Challenger не боится переступить любую черту, перед которой стоит. Обладающий невероятной мощностью и скоростью Dodge Challenger Super Stock 2023 года выпуска оснащен 6,2-литровым мощным двигателем HEMI® V8 с наддувом", R.drawable.car),
            Item("Adidas Yeezy 451", "5000 руб", "Кроссовки 451 серии по праву носят звание одной из самых необычных моделей, выпущенной в коллаборации легендарного производителя с американским исполнителем Канье Уэстом. Актуальная массивная подошва, уже реализованная во многих других моделях бренда, стала еще более выразительной в сникерсах Изи Буст 451. Кроме того, она приобрела хищные черты акульей пасти. В кроссовках имеется продуманная система амортизации, верх произведен из инновационных материалов. Кроме того, 451 модель отличаются улучшенной посадкой. В этой обуви будет комфортно вне зависимости от того, выбираете ли вы ее на повседневную носку или же исключительно для занятий спортом.", R.drawable.sneakers),
            Item("Nescafe 3in1", "35 руб", "Этот напиток сочетает в себе идеальный баланс кофеина, молока и сахара, чтобы подарить тебе мгновенный заряд бодрости и отличное настроение. С ним ты забудешь про усталость и готов будешь покорять любые вершины!", R.drawable.cofe)
        )
        filter = Filter(originalItems)
        search = Search(originalItems)
        adapter = ItemAdapter(originalItems,
            onAddToCartClick = { item -> addToCart(item) },
            onDetailsClick = { item -> sharedViewModel.showDetails(requireContext()) },
            onHeaderImageClick = {item -> sharedViewModel.showDetails(requireContext())})
        recyclerView.adapter = adapter
        filterButton = view.findViewById(R.id.filter_button)
        filterButton.setOnClickListener(){
            showFilterOptions()
        }
        searchEditText = view.findViewById(R.id.search_edit_text)
        searchEditText.addTextChangedListener(textWatcher)
        return view
    }
    private fun showFilterOptions(){
        val options = arrayOf("Дешевле 10000", "Только Adidas", "Только автомобили", "Съедобное")
        AlertDialog.Builder(requireContext())
            .setTitle("Выберите фильтр")
            .setItems(options) {_, which ->
                when (which) {
                    0-> {
                        val filteredItems = filter.filterByPrice(10000)
                        adapter.updateItems(filteredItems)
                    }
                    1 -> {
                        val filteredItems = filter.filterByCategory("Adidas")
                        adapter.updateItems(filteredItems)
                    }
                    2->{
                        val filteredItems = filter.filterByCategory("Nescafe")
                        adapter.updateItems(filteredItems)
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
    private fun addToCart (item: Item) {
        Toast.makeText(context, "Toвap '${item.title}' добавлен в корзину", Toast.LENGTH_SHORT)
            .show()
        sharedViewModel.addItem(item)
    }


    private fun showDetails(item: Item) {
        val intent = Intent(context, ItemDetails::class.java).apply {
            putExtra("item_title", item.title)
            putExtra("item_price", item.price)
            putExtra("item_description", item.description)
            putExtra("item_image", item. imageRestId)
        }
        startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragmen.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}