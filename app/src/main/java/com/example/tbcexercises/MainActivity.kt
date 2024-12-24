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
    private val categories = getCategories()

    private val data = getPhotos()

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
        setUp()

    }

    private fun setUp() {

        val photosLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        val categoriesLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        photoAdapter = PhotoAdapter(data)

        binding.apply {
            rvPhotos.layoutManager = photosLayoutManager
            rvPhotos.adapter = photoAdapter

            rvCategories.layoutManager = categoriesLayoutManager
            rvCategories.adapter =
                CategoryAdapter(categories = categories, onCategoryClick = { category ->
                    onCategorySelected(category)
                })
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
