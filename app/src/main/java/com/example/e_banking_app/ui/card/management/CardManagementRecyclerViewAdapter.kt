package com.example.e_banking_app.ui.card.management

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_banking_app.data.model.card.Card
import com.example.e_banking_app.databinding.FragmentCardItemBinding
import com.maxpilotto.creditcardview.CreditCardView
import java.time.LocalDate

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class CardManagementRecyclerViewAdapter(
    private val values: List<Card>
) : RecyclerView.Adapter<CardManagementRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentCardItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.card.number = item.id_card

        val expired: LocalDate = LocalDate.parse(item.expired_at)

        holder.card.expiry = formatNumber(expired.dayOfMonth) + formatNumber(expired.monthValue)
        holder.card.holder = item.name
    }

    private fun formatNumber(num: Int): String =
        if (num / 10 > 0) num.toString() else "0$num"

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val card: CreditCardView = binding.card


        override fun toString(): String {
            return super.toString() + " '" + card.cardData.holder + "'"
        }
    }

}