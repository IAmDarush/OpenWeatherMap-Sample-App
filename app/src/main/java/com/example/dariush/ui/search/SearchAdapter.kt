package com.example.dariush.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dariush.databinding.ItemSearchBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

  private val items = mutableListOf<SearchItemViewModel>()

  fun setItems(newItems: List<SearchItemViewModel>) {
    val diffCallback = SearchDiffCallback(ArrayList(items), ArrayList(newItems))
    val diffResult = DiffUtil.calculateDiff(diffCallback)
    items.clear()
    items.addAll(newItems)
    diffResult.dispatchUpdatesTo(this)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val binding = ItemSearchBinding.inflate(inflater, parent, false)
    return SearchViewHolder(binding)
  }

  override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
    holder.onBind(position)
  }

  override fun getItemCount(): Int {
    return items.size
  }


  inner class SearchViewHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {

    private fun clearData() {
      binding.tvKey.text = ""
      binding.tvValue.text = ""
    }

    fun onBind(position: Int) {
      clearData()

      val item = items[position]
      binding.viewModel = item

    }

  }

  class SearchDiffCallback(
    private val oldList: List<SearchItemViewModel>,
    private val newList: List<SearchItemViewModel>
  ) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      val oldItem = oldList[oldItemPosition]
      val newItem = newList[newItemPosition]

      return oldItem.key == newItem.key
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
      val oldItem = oldList[oldPosition]
      val newItem = newList[newPosition]

      return oldItem.key == newItem.key
          && oldItem.value == newItem.value
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
      return super.getChangePayload(oldPosition, newPosition)
    }
  }

}