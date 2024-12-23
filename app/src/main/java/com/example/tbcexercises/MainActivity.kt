package com.example.tbcexercises

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var photoAdapter: PhotoAdapter
    private val categories = listOf(
        Category(1, null, "All"),
        Category(2, R.drawable.party, "Party"),
        Category(3, R.drawable.mountain, "Camping"),
        Category(4, null, "Category1"),
        Category(5, null, "Category2"),
        Category(6, null, "Category3"),
    )

    private val data = listOf(
        Photo(
            id = 1, image = R.drawable.img1, categoryType = "Party", price = 123, title = "opaaaa"
        ),
        Photo(
            id = 2,
            image = R.drawable.img1,
            categoryType = "Camping",
            price = 234234,
            title = "opaaa343243423a"
        ),
        Photo(
            id = 3, image = R.drawable.img2, categoryType = "Party", price = 10003, title = "nike"
        ),
        Photo(
            id = 4, image = R.drawable.img3, categoryType = "Camping", price = 123, title = "adidas"
        ),
        Photo(
            id = 3, image = R.drawable.img4, categoryType = "Party", price = 10003, title = "nike"
        ),
        Photo(
            id = 4, image = R.drawable.img2, categoryType = "Camping", price = 123, title = "adidas"
        ),
        Photo(
            id = 3, image = R.drawable.img3, categoryType = "Party", price = 10003, title = "nike"
        ),
        Photo(
            id = 4, image = R.drawable.img1, categoryType = "Camping", price = 123, title = "adidas"
        ),
        Photo(
            id = 3, image = R.drawable.img1, categoryType = "Party", price = 10003, title = "nike"
        ),
        Photo(
            id = 4, image = R.drawable.img1, categoryType = "Camping", price = 123, title = "adidas"
        ),
    )

    private var selectedCategory: String = "All"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val photosLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        val categoriesLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        photoAdapter = PhotoAdapter(data)

        binding.apply {
            rvPhotos.layoutManager = photosLayoutManager
            rvPhotos.adapter = photoAdapter

            rvCategories.layoutManager = categoriesLayoutManager
            rvCategories.adapter = CategoryAdapter(categories = categories,
                onCategoryClick = { category ->
                    onCategorySelected(category)
                }
            )
        }
    }

    private fun onCategorySelected(category: String) {
        selectedCategory = category
        val filteredData =
            if (category.lowercase() == "all") data else data.filter { it.categoryType == category }
        photoAdapter.photos = filteredData
        photoAdapter.notifyDataSetChanged()
    }


}
