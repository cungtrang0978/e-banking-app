package com.example.e_banking_app.ui.passbook.category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_banking_app.data.model.passbook.PassbookCategory
import com.example.e_banking_app.databinding.FragmentPassbookCategoryItemBinding


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class PassbookCategoryRecyclerViewAdapter(
    private val values: List<PassbookCategory>,
    private val onItemClick: ((PassbookCategory) -> Unit)? = null,
) : RecyclerView.Adapter<PassbookCategoryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        return ViewHolder(
            FragmentPassbookCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.name.text = item.name_passbook
        holder.period.text = item.period

    }

    //    fun <T : RecyclerView.ViewHolder> T.onClick(event: (view: View, position: Int, type: Int) -> Unit): T {
//        itemView.setOnClickListener {
//            event.invoke(it, getAdapterPosition(), getItemViewType())
//        }
//        return this
//    }
    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentPassbookCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.name
        val period: TextView = binding.period

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(values[absoluteAdapterPosition])
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + name.text + "'"
        }

    }

}