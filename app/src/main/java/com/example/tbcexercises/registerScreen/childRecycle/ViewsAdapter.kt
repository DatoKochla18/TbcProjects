package com.example.tbcexercises.registerScreen.childRecycle

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tbcexercises.R
import com.example.tbcexercises.databinding.ItemChooseViewBinding
import com.example.tbcexercises.databinding.ItemInputViewBinding
import com.example.tbcexercises.models.Views

class ViewsAdapter(val onChangedText: (Int, String?) -> Unit) :
    ListAdapter<Views, RecyclerView.ViewHolder>(ViewDiffUtil) {

    companion object {
        private const val INPUT = 1
        private const val CHOOSER = 2
    }

    val gender = arrayOf("Choose Gender", "Male", "Female", "Two Spirited Penguin")
    val dates =
        arrayOf("Choose Date", "2001/09/11", "1878/04/24", "2005/05/11", "2005/05/12", "2005/05/13")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            INPUT -> {
                InputViewHolder(
                    ItemInputViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                ChooserViewHolder(
                    ItemChooseViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChooserViewHolder -> holder.onBind()
            is InputViewHolder -> holder.onBind()
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).filedType == "input") INPUT else CHOOSER
    }


    inner class ChooserViewHolder(private val binding: ItemChooseViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val item = getItem(adapterPosition)
            val data = if (item.fieldId == 89) dates else gender
            val arrayAdapter = ArrayAdapter(
                binding.root.context, R.layout.spinner_layout, data
            )
            binding.spinner.adapter = arrayAdapter

            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) {
                        onChangedText(item.fieldId, null)
                    } else {
                        onChangedText(item.fieldId, null)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }


    inner class InputViewHolder(private val binding: ItemInputViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            val view = getItem(adapterPosition)

            val inputType = when (view.keyboard) {
                "text" -> InputType.TYPE_CLASS_TEXT
                "number" -> InputType.TYPE_CLASS_NUMBER
                else -> InputType.TYPE_NULL
            }

            binding.etInput.hint = view.hint
            binding.etInput.inputType = inputType

            binding.etInput.doAfterTextChanged {
                onChangedText(view.fieldId, it.toString())

            }

        }
    }

}