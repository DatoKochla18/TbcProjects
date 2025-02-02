package com.example.tbcexercises.presentation.contactAddEditScreen


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbcexercises.App
import com.example.tbcexercises.base.BaseFragment
import com.example.tbcexercises.data.model.Contact
import com.example.tbcexercises.databinding.FragmentContactAddEditBinding
import com.example.tbcexercises.presentation.contactAddEditScreen.viewmodel.ContactAddEditViewModel
import com.example.tbcexercises.presentation.contactAddEditScreen.viewmodel.ContactAddEditViewModelFactory
import com.example.tbcexercises.util.collectLastState


class ContactAddEditFragment :
    BaseFragment<FragmentContactAddEditBinding>(FragmentContactAddEditBinding::inflate) {
    private val args: ContactAddEditFragmentArgs by navArgs()

    private val viewModel: ContactAddEditViewModel by viewModels {
        ContactAddEditViewModelFactory((requireActivity().application as App).contactRepository)
    }

    override fun start() {
        args.contactId?.let {
            viewModel.getContactOrNull(it.toInt())
        }

        collectLastState(viewModel.contactFlow) { contact ->
            if (contact != null) {
                binding.etName.setText(contact.name)
                binding.etPhoneNumber.setText(contact.phoneNumber)
            }
        }
    }

    override fun listeners() {

        binding.btnConfirm.setOnClickListener {
            confirmAddOrEdit()
        }

        binding.btnArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun confirmAddOrEdit() {
        val name = binding.etName.text.toString()
        val phoneNumber = binding.etPhoneNumber.text.toString()

        if (name.isNotEmpty() && phoneNumber.isNotEmpty()) {
            when (val contactId = args.contactId) {
                null -> viewModel.addContact(Contact(name = name, phoneNumber = phoneNumber))
                else -> viewModel.addContact(
                    Contact(
                        id = contactId.toInt(),
                        name = name,
                        phoneNumber = phoneNumber
                    )
                )
            }
        }
        findNavController().navigateUp()
    }
}