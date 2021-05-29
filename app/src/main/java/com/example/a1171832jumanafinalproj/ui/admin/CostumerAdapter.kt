package com.example.a1171832jumanafinalproj.ui.admin

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.a1171832jumanafinalproj.R
import com.example.a1171832jumanafinalproj.User

class CostumerAdapter(
    private val interaction: Interaction? = null
)  : RecyclerView.Adapter<RecyclerView.ViewHolder>(){



    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.equals(newItem)
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CountryItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.customer_list_item_lyout,
                parent,
                false
            ),
            interaction
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<User>) {
        differ.submitList(list)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CountryItemViewHolder -> {
                holder.bind(differ.currentList[position])

            }
        }
    }
    inner class CountryItemViewHolder constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView)  {

        fun bind( user : User) = with(itemView) {
            val textView = this.findViewById<TextView>(R.id.email_txt_view)
            textView.text = user.email
        }

    }

    interface Interaction {
        fun onItemSelected(position: Int, item: User)
    }
}