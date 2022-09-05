package com.example.e_banking_app.ui.bill.bill_list

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_banking_app.R
import com.example.e_banking_app.data.model.bill.BillItem
import com.example.e_banking_app.data.model.bill.getIconResource
import com.example.e_banking_app.databinding.FragmentBillItemBinding
import com.example.e_banking_app.utils.CurrencyUtils


/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class BillItemRecyclerViewAdapter(
    private val values: List<BillItem>,
    private val context: Context,
    private val onItemClick: ((BillItem) -> Unit)? = null,
) : RecyclerView.Adapter<BillItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentBillItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.icon.setImageResource(item.billType.getIconResource())
        holder.billAmount.text = CurrencyUtils.format(item.money)
        holder.createdAt.text = item.created_at
        holder.billName.text = item.name_bill
        holder.unit.text = item.name_3rd

        holder.billStatus.text = context.resources.getString(item.statusValue)

        val color = if (item.statusValue == R.string.unpaid) "#eb4034"
        else "#059913"
        holder.billStatus.setTextColor(Color.parseColor(color))

        R.string.pay
    }


    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentBillItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val icon: ImageView = binding.typeIcon
        val billAmount: TextView = binding.billAmount
        val unit: TextView = binding.billUnit
        val createdAt: TextView = binding.createdAt
        val billName: TextView = binding.billName
        val billStatus: TextView = binding.billStatus
//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(values[absoluteAdapterPosition])
            }
        }
    }

}