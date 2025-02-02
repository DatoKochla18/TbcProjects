package com.example.tbcexercises.presentation.contactListScreen


import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcexercises.App
import com.example.tbcexercises.base.BaseFragment
import com.example.tbcexercises.databinding.FragmentContactListBinding
import com.example.tbcexercises.presentation.contactListScreen.adapter.ContactListAdapter
import com.example.tbcexercises.presentation.contactListScreen.viewmodel.ContactListViewModel
import com.example.tbcexercises.presentation.contactListScreen.viewmodel.ContactListViewModelFactory
import com.example.tbcexercises.util.collectLastState


class ContactListFragment :
    BaseFragment<FragmentContactListBinding>(FragmentContactListBinding::inflate) {

    private val contactViewModel: ContactListViewModel by viewModels {
        ContactListViewModelFactory((requireActivity().application as App).contactRepository)
    }
    private val contactListAdapter by lazy {
        ContactListAdapter(onEdit = { contactId ->
            editContact(id = contactId)
        })
    }

    override fun start() {
        binding.rvContanct.run {
            adapter = contactListAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }

        collectLastState(contactViewModel.allContact) { contacts ->
            contactListAdapter.submitList(contacts)
        }
    }

    override fun listeners() {
        binding.btnAddContact.setOnClickListener {
            findNavController().navigate(
                ContactListFragmentDirections.actionContactListFragmentToContactAddEditFragment(
                    null
                )
            )
        }
    }

    private fun editContact(id: Int) {

        findNavController().navigate(
            ContactListFragmentDirections.actionContactListFragmentToContactAddEditFragment(
                contactId = id.toString()
            )
        )
    }
}