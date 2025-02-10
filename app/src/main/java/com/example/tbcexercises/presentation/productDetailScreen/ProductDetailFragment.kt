package com.example.tbcexercises.presentation.productDetailScreen

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.tbcexercises.App
import com.example.tbcexercises.R
import com.example.tbcexercises.common.Resource
import com.example.tbcexercises.common.collectLastState
import com.example.tbcexercises.common.toast
import com.example.tbcexercises.data.imageLoader.CoilImageLoader
import com.example.tbcexercises.data.imageLoader.GlideImageLoader
import com.example.tbcexercises.databinding.FragmentProductDetailBinding
import com.example.tbcexercises.domain.model.Product
import com.example.tbcexercises.presentation.base.BaseFragment


class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding>(FragmentProductDetailBinding::inflate) {
    private val args: ProductDetailFragmentArgs by navArgs()

    private val viewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModelFactory((requireActivity().application as App).repository)
    }

    private val imageLoader = GlideImageLoader()

    override fun start() {
        viewModel.fetchProduct(args.productId)

        collectLastState(viewModel.product) { state ->
            when (state) {
                is Resource.Success -> {
                    val product = state.data
                    showSuccessScreen(product)
                }

                is Resource.Error -> toast(state.message)
                Resource.Loading -> {
                    showLoadingScreen(true)
                }
            }
        }
    }

    private fun showSuccessScreen(product: Product) {
        showLoadingScreen(false)
        binding.apply {
            txtProductTitle.text = product.title
            txtProductPrice.text = getString(R.string.product_price, product.price.toString())
            txtProductCategory.text = product.category
            productDescription.text = product.description
            rbRatingBar.rating = product.rating.rate.toFloat()
            txtRatingCount.text =
                getString(R.string.reviews, product.rating.count.toString())

            imageLoader.loadImage(product.image, imgProductImage)
        }
    }

    private fun showLoadingScreen(loading: Boolean) {
        binding.apply {
            txtProductTitle.isVisible = !loading
            txtProductPrice.isVisible = !loading
            txtProductCategory.isVisible = !loading
            productDescription.isVisible = !loading
            rbRatingBar.isVisible = !loading
            txtRatingCount.isVisible = !loading
            imgProductImage.isVisible = !loading
            progressBar.isVisible = loading
        }
    }

    override fun listeners() {
    }

}