package com.interview.darrengu.resumeweather.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.interview.darrengu.resumeweather.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list_country.*

class CountryListAdapter(private val countryList: List<String>, private val onClickItem: (String) -> Unit) : RecyclerView.Adapter<CountryListAdapter.CountryListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_country, parent, false)
        val viewHolder = CountryListViewHolder(itemView)
        itemView.setOnClickListener{
            onClickItem(countryList[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun getItemCount(): Int = countryList.size

    override fun onBindViewHolder(viewHolder: CountryListViewHolder, position: Int) {
        viewHolder.countryNameTextView.text = countryList[position]
    }

    class CountryListViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}