package com.weather.task7_3notebook.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.task7_3notebook.R
import com.weather.task7_3notebook.databinding.FragmentListBinding
import com.weather.task7_3notebook.model.Contact
import com.weather.task7_3notebook.view.adapter.ContactAdapter
import com.weather.task7_3notebook.view.adapter.CustomDuffCallback
import com.weather.task7_3notebook.view.decoration.VerticalSpaceItemDecoration
import com.weather.task7_3notebook.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ListContactFragment : Fragment(R.layout.fragment_list) {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    private var adapter: ContactAdapter? = null

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
        initRecyclerView()
        initObserve()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Инициализация RecyclerView
     */
    private fun initRecyclerView() {
        // создание адаптера
        adapter = ContactAdapter { contact ->
            adapter?.let {
                deleteContact(contact)
            }
        }
        binding.rootFragmentList.adapter = adapter
        binding.rootFragmentList.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        // добавляем отступ между элементами
        binding.rootFragmentList.addItemDecoration(VerticalSpaceItemDecoration(VERTICAL_SPACE_HEIGHT))
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.contactsFlow.onEach {
                updateRecyclerView(it)
            }.collect()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.filterContactsFlow.onEach {
                updateRecyclerView(it)
            }.collect()
        }
    }

    /**
     * Удалить контакт
     * @param position - позиция удаляемого контакта
     * @param contact - экземпляр удаляемого объекта
     */
    private fun deleteContact(contact: Contact) {
        adapter?.let {
            mainViewModel.removeContact(contact)
        }
    }

    /**
     * Обновляем данные в адаптере
     * @param newList новый список данных
     */
    private fun updateRecyclerView(newList: List<Contact>) {
        val customDuffCallback = CustomDuffCallback(adapter?.contactList.orEmpty(), newList)
        val diffResult = DiffUtil.calculateDiff(customDuffCallback)
        adapter?.let {
            it.updateList(newList)
            diffResult.dispatchUpdatesTo(it)
        }
    }

    companion object {
        private const val VERTICAL_SPACE_HEIGHT = 20
    }
}
