package com.weather.task7_3notebook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.task7_3notebook.databinding.ItemContactBinding

class ContactAdapter(
    private val onItemDeleteClickListener: (position: Int, contact: Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.Holder>() {

    var contactList: List<Contact> = emptyList()
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflate)
        return Holder(binding, onItemDeleteClickListener)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(contactList[position])
    }

    /**
     * Обновить список
     */
    fun updateList(list: List<Contact>) {
        contactList = list
    }

    /**
     * Удалить элемент из списка адаптера
     */
    fun deleteContactList(contact: Contact) {
        val listContact = contactList.filter { currentContact ->
            currentContact != contact
        }
        contactList = listContact
    }

    class Holder(
        private val binding: ItemContactBinding,
        private val onItemDeleteClickListener: (position: Int, contact: Contact) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            with(binding) {
                textViewName.text = contact.name
                textViewLastName.text = contact.lastName
                textViewNumberPhone.text = contact.number.toString()
                binding.buttonDelete.setOnClickListener {
                    onItemDeleteClickListener.invoke(adapterPosition, contact)
                }
            }
        }
    }
}
