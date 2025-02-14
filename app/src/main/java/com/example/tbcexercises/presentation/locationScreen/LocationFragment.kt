package com.example.tbcexercises.presentation.locationScreen


import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.tbcexercises.databinding.FragmentLocationBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.locationScreen.viewpager.ViewPagerAdapter
import com.example.tbcexercises.utils.helpers.Resource
import com.example.tbcexercises.utils.extension.collectLastState
import com.example.tbcexercises.utils.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class LocationFragment : BaseFragment<FragmentLocationBinding>(FragmentLocationBinding::inflate) {
    private val viewModel: LocationViewModel by viewModels()

    private val viewPagerAdapter by lazy {
        ViewPagerAdapter()
    }

    override fun start() {
        setupViewPager()

        collectLastState(viewModel.locationResponse) { result ->
            when (result) {
                is Resource.Loading -> showLoadingScreen(true)
                is Resource.Error -> {
                    showLoadingScreen(false)
                    toast(result.message)
                }

                is Resource.Success -> {
                    showLoadingScreen(false)
                    viewPagerAdapter.submitList(result.data.toList())
                }

                null -> {}
            }
        }


    }

    private fun setupViewPager() {
        val carouselAdapter = viewPagerAdapter
        val transformer = CompositePageTransformer()

        transformer.addTransformer(MarginPageTransformer(40))

        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        binding.viewPager.apply {
            adapter = carouselAdapter
            setPageTransformer(transformer)
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            setPadding(100, 0, 100, 0)
        }
    }

    private fun showLoadingScreen(isLoading: Boolean) {
        binding.viewPager.isVisible = !isLoading
        binding.txtStatistics.isVisible = !isLoading
        binding.progressBar.isVisible = isLoading
    }

}