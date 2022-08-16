package com.example.e_banking_app.ui.passbook.category

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.e_banking_app.R
import com.example.e_banking_app.data.model.passbook.PassbookCategory
import com.example.e_banking_app.databinding.FragmentPassbookCategoryItemBinding


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class PassbookCategoryRecyclerViewAdapter(
    private val values: List<PassbookCategory>
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
        holder.idView.text = item.id_category_passbook
        holder.contentView.text = item.name_passbook

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentPassbookCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }

    }

}