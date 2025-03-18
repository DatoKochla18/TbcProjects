package com.example.tbcexercises.presentation.screen.search.balls_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.ItemBallsBinding

class BallAdapter : ListAdapter<Ball, BallAdapter.BallViewHolder>(BallDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BallViewHolder {
        val binding = ItemBallsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BallViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BallViewHolder, position: Int) {
        holder.onBind()
    }

    inner class BallViewHolder(val binding:ItemBallsBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(){

        }
    }

}