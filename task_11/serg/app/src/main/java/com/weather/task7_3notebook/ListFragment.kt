package com.weather.task7_3notebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.weather.task7_3notebook.databinding.FragmentListBinding
import com.weather.task7_3notebook.databinding.ItemContactBinding

interface IListFragment {
    /**
     * Обработать результат поиска
     */
    fun processSearchResult(listContact: List<Contact>)
}

class ListFragment : Fragment(R.layout.fragment_list), IListFragment {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showListContact()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun processSearchResult(listContact: List<Contact>) {
        // удаление всех вью
        binding.rootFragmentList.removeAllViews()
        listContact.forEach {
            createViewContent(it)
        }
    }

    fun showListContact() {
        binding.rootFragmentList.removeAllViews()
        (activity as IMainActivity).getListContact().forEach {
            createViewContent(contact = it)
        }
    }

    /**
     * Создание View из объекта Contact
     */
    private fun createViewContent(contact: Contact) {
        val view = ItemContactBinding.inflate(
            layoutInflater,
            binding.rootFragmentList,
            false
        )
        view.textViewName.text = contact.name
        view.textViewLastName.text = contact.lastName
        view.textViewNumberPhone.text = contact.number.toString()

        view.buttonDelete.setOnClickListener {
            (activity as IMainActivity).removeContact(contact)
            binding.rootFragmentList.removeView(view.root)
        }

        binding.rootFragmentList.addView(view.root)
    }

    companion object {
        const val FRAGMENT_TAG = "ListFragment"
    }
}
