package com.example.a1171832jumanafinalproj.ui.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.a1171832jumanafinalproj.R
import com.example.a1171832jumanafinalproj.User

class DeleteCostumerAdapter (
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
                R.layout.delete_costumer_layout,
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
            val checkBox = this.findViewById<CheckBox>(R.id.check_box)
            textView.text = user.email
            checkBox.isChecked = user.isDeleted

            checkBox.setOnClickListener {
                user.isDeleted = !user.isDeleted
                checkBox.isChecked = user.isDeleted
                interaction?.onItemSelected(adapterPosition , user , checkBox.isChecked)
            }
        }

    }

    interface Interaction {
        fun onItemSelected(position: Int, item: User , isChecked : Boolean )
    }
}