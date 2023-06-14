package com.weather.task7_3notebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.task7_3notebook.databinding.FragmentListBinding

interface IListFragment {
    /**
     * Обработать результат поиска
     */
    fun processSearchResult(listContact: List<Contact>)
}

class ListFragment : Fragment(R.layout.fragment_list), IListFragment {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun processSearchResult(listContact: List<Contact>) {
        updateRecyclerView(listContact)
    }

    /**
     * Инициализация RecyclerView
     */
    private fun initRecyclerView() {
        // создание адаптера
        adapter = ContactAdapter { position, contact ->
            adapter?.let {
                deleteContact(position, contact)
            }
        }
        // получение списка из MainActivity
        val listContact = (activity as IMainActivity).getListContact()
        binding.rootFragmentList.adapter = adapter
        binding.rootFragmentList.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        // добавляем отступ между элементами
        binding.rootFragmentList.addItemDecoration(VerticalSpaceItemDecoration(VERTICAL_SPACE_HEIGHT))

        // обновляем данные в адаптере
        updateRecyclerView(listContact)
    }

    /**
     * Удалить контакт
     * @param position - позиция удаляемого контакта
     * @param contact - экземпляр удаляемого объекта
     */
    private fun deleteContact(position: Int, contact: Contact) {
        adapter?.let {
            (activity as IMainActivity).removeContact(contact)
            it.deleteContactList(contact)
            it.notifyItemRemoved(position)
        }
    }

    /**
     * Обновляем данные в адаптере
     * @param newList новый список данных
     */
    private fun updateRecyclerView(newList: List<Contact>) {
        adapter?.let {
            it.updateList(newList)
            it.notifyDataSetChanged()
        }
    }

    companion object {
        const val FRAGMENT_TAG = "ListFragment"
        private const val VERTICAL_SPACE_HEIGHT = 20
    }
}
