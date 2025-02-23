package com.example.tbcexercises.presentation.home_screen

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.databinding.FragmentHomeBinding
import com.example.tbcexercises.presentation.base.BaseFragment
import com.example.tbcexercises.presentation.home_screen.post_adapter.PostListAdapter
import com.example.tbcexercises.presentation.home_screen.story_adapter.StoryListAdapter
import com.example.tbcexercises.util.extension.collectLastState
import com.example.tbcexercises.util.image_loader.ImageLoader
import com.example.tbcexercises.util.network_helper.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewmodel: HomeViewModel by viewModels()

    private val postAdapter by lazy {
        PostListAdapter(imageLoader)
    }
    private val storiesAdapter by lazy {
        StoryListAdapter(imageLoader)
    }

    override fun start() {
        binding.apply {
            rvStory.adapter = storiesAdapter
            rvStory.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            rvPost.adapter = postAdapter
            rvPost.layoutManager = LinearLayoutManager(requireContext())
        }


        collectLastState(viewmodel.posts) { state ->
            when (state) {
                is Resource.Error -> {
                }

                Resource.Loading -> {}
                is Resource.Success -> {
                    postAdapter.submitList(state.data.toList())
                }

                null -> {}
            }

        }

        collectLastState(viewmodel.stories) { state ->
            when (state) {
                is Resource.Error -> {
                }

                Resource.Loading -> {}
                is Resource.Success -> {
                    storiesAdapter.submitList(state.data.toList())
                }

                null -> {}
            }

        }
    }
}