package com.dca.mettest.adaptor

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dca.mettest.R
import com.dca.mettest.model.itemsmodel
import com.dca.mettest.view.getProgressDrawable
import com.dca.mettest.view.loadImage
import kotlinx.android.synthetic.main.item_product.view.*

class productListAdapter(var itemslist: ArrayList<itemsmodel>): RecyclerView.Adapter<productListAdapter.CountryViewHolder>() {

    fun updateitemslist(newitemslist: List<itemsmodel>) {
        itemslist.clear()
        itemslist.addAll(newitemslist)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
    )

    override fun getItemCount() = itemslist.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(itemslist[position])
    }

    class CountryViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val imageView = view.imageView
        private val productname = view.name
        private val productcat = view.catname
        private val price=view.price
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(item: itemsmodel) {
            productname.text = item.product_name
            productcat.text = item.category_name
            price.text=item.price.toDouble().toString()
            imageView.loadImage(item.product_image, progressDrawable)
        }
    }
}