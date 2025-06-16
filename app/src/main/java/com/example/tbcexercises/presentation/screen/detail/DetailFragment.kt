package com.example.tbcexercises.presentation.screen.detail

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.tbcexercises.databinding.FragmentDetailBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.extension.collectLatestFlow
import com.example.tbcexercises.presentation.extension.loadImg
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(FragmentDetailBinding::inflate) {

    private val args by navArgs<DetailFragmentArgs>()
    private val viewmodel: DetailViewModel by viewModels()

    override fun start() {
        Log.d("argshex",args.hex)
        viewmodel.process(DetailViewModel.DetailEvent.GetImage(args.hex))
        collectLatestFlow(viewmodel.state) {
            Log.d("DetailState", it.toString())
            binding.apply {
                imageBadge.loadImg(it.detailImage?.badgeUrl)
                textTitle.text = it.detailImage?.title
                textDescription.text = it.detailImage?.description
                textUserName.text = it.detailImage?.userName
                textDateCreated.text = it.detailImage?.dateCreated
            }

        }

    }

    override fun listeners() {
    }

}