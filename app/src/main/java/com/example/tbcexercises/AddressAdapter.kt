package com.example.tbcexercises

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.databinding.AddressItemBinding

class AddressAdapter(
    val removeItem: (Address) -> Unit,
    val editItem: (Address) -> Unit
) :
    ListAdapter<Address, AddressAdapter.AddressViewHolder>(AddressDiffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding =
            AddressItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class AddressViewHolder(private val binding: AddressItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(address: Address) {

            binding.apply {
                txtAddressTitle.text = address.title
                txtDetailedLocation.text = address.detailedLocation
                imLocation.setImageResource(address.img)

                root.setOnLongClickListener {
                    removeItem(address)
                    true
                }
                btnSelectAddress.setOnCheckedChangeListener { _, isChecked ->
                    changeTextColor(isChecked)

                }
                txtEdit.setOnClickListener {
                    if (btnSelectAddress.isChecked) {
                        editItem(address)
                    }
                }

            }
        }

        private fun changeTextColor(isChecked: Boolean) {
            if (isChecked) {
                binding.txtEdit.setTextColor(binding.root.context.getColor(R.color.selected_edit))
            } else {
                binding.txtEdit.setTextColor(binding.root.context.getColor(R.color.unselected_edit))
            }
        }
    }
}
