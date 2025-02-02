package com.example.tbcexercises.presentation.callDialogScreen


import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.App
import com.example.tbcexercises.base.BaseFragment
import com.example.tbcexercises.data.model.Contact
import com.example.tbcexercises.data.model.Dialog
import com.example.tbcexercises.databinding.FragmentCallDialogBinding
import com.example.tbcexercises.presentation.callDialogScreen.adaper.DialogAdapter
import com.example.tbcexercises.presentation.callDialogScreen.adaper.DialogResultAdapter
import com.example.tbcexercises.presentation.callDialogScreen.viewmodel.CallDialogViewModel
import com.example.tbcexercises.presentation.callDialogScreen.viewmodel.CallDialogViewModelFactory
import com.example.tbcexercises.util.collectLastState


class CallDialogFragment :
    BaseFragment<FragmentCallDialogBinding>(FragmentCallDialogBinding::inflate) {

    private val dialogViewModel: CallDialogViewModel by viewModels {
        CallDialogViewModelFactory(
            (requireActivity().application as App).dialogRepository,
            (requireActivity().application as App).contactRepository
        )
    }

    private val dialogAdapter: DialogAdapter by lazy {
        DialogAdapter(onClick = { addNumberToText(it) })
    }

    private val dialogResultAdapter by lazy {
        DialogResultAdapter(onClick = { contact ->
            binding.txtCallNumber.text = contact.phoneNumber
        })
    }

    override fun start() {
        setUpDialogAdapter()
        setUpDialogResultAdapter()

        collectLastState(dialogViewModel.dialogs) {
            dialogAdapter.submitList(it)
        }

    }

    override fun listeners() {

        binding.btnBackSpace.apply {
            setOnClickListener {
                removeNumberToText()
            }
            setOnLongClickListener {
                binding.txtCallNumber.text = ""
                true
            }
        }

        binding.txtCallNumber.doAfterTextChanged { editText ->
            val searchText = editText?.toString() ?: ""
            search(searchText)
        }

    }

    private fun addNumberToText(dialog: Dialog) {
        val oldText = binding.txtCallNumber.text.toString()
        val newText = oldText + dialog.id

        binding.txtCallNumber.text = newText
    }

    private fun setUpDialogAdapter() {
        binding.rvDialog.apply {
            adapter = dialogAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    private fun setUpDialogResultAdapter() {
        binding.rvDialogResult.apply {
            adapter = dialogResultAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun removeNumberToText() {
        val oldText = binding.txtCallNumber.text.toString()
        if (oldText.isNotEmpty()) {
            val newText = oldText.substring(0, oldText.length - 1)
            binding.txtCallNumber.text = newText

        }
    }

    private fun search(text: String) {

        if (text.isEmpty()) {
            dialogResultAdapter.submitList(mutableListOf<Contact>())
            binding.apply {
                btnCamera.visibility = View.GONE
                btnBackSpace.visibility = View.GONE
            }
        } else {
            dialogViewModel.getContactByPhoneNumber(text)

            collectLastState(dialogViewModel.contacts) {
                dialogResultAdapter.submitList(it.toList())
                Log.d("tag", it.toString())
            }
            binding.apply {

                btnCamera.visibility = View.VISIBLE
                btnBackSpace.visibility = View.VISIBLE
            }
        }
    }


}