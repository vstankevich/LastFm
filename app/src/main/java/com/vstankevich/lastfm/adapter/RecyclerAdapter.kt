package by.synesis.common.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vstankevich.lastfm.RecyclerItem

class RecyclerAdapter(private val context: Context,
                      items: List<RecyclerItem>,
                      private val click: (RecyclerItem) -> Unit = {}) :
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private val items = arrayListOf<RecyclerItem>()

    init {
        this.items.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(viewType, parent, false)
        return ViewHolder(view, click)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val renderFirstTime = holder.holderItem == null || holder.holderItem != item
        holder.holderItem = item
        val holderView = holder.holderView
        if (renderFirstTime) holder.holderItem?.initView(context, holderView)
        holder.holderItem?.renderView(context, holderView)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].getLayoutId()

    fun updateAdapter(newItems: List<RecyclerItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun removeLastItem() {
        val lastIndex = items.lastIndex
        items.removeAt(lastIndex)
        notifyItemRemoved(lastIndex)
    }

    fun addItem(item: RecyclerItem) {
        items.add(item)
        notifyDataSetChanged()
    }

    fun updateItem(item: RecyclerItem, position: Int) {
        items[position] = item
        notifyItemChanged(position)
    }

    fun updateItem(position: Int) {
        notifyItemChanged(position)
    }

    class ViewHolder(val holderView: View,
                     private val clickListener: (RecyclerItem) -> Unit) :
            RecyclerView.ViewHolder(holderView), View.OnClickListener {

        var holderItem: RecyclerItem? = null

        init {
            holderView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            holderItem?.let { clickListener(it) }
        }

    }
}