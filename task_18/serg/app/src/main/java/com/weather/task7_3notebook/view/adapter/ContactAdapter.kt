package com.weather.task7_3notebook.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.task7_3notebook.databinding.ItemContactBinding
import com.weather.task7_3notebook.model.Contact

class ContactAdapter(
    private val onItemDeleteClickListener: (contact: Contact) -> Unit
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

    class Holder(
        private val binding: ItemContactBinding,
        private val onItemDeleteClickListener: (contact: Contact) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            with(binding) {
                textViewName.text = contact.name
                textViewLastName.text = contact.lastName
                textViewNumberPhone.text = contact.number.toString()
                contact.city?.let {
                    binding.textViewCityName.text = it.nameCity
                }
                binding.buttonDelete.setOnClickListener {
                    onItemDeleteClickListener.invoke(contact)
                }
            }
        }
    }
}
