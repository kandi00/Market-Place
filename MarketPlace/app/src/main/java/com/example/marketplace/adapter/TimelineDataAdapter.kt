package com.example.marketplace.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.R
import com.example.marketplace.model.Product

class TimelineDataAdapter(
    private var list: ArrayList<Product>,
    private val context: Context,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<TimelineDataAdapter.TimelineProductViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(productId: String)
        fun onOrderNowButtonClick(productId : String)
    }

    // 1. user defined ViewHolder type - Embedded class!
    inner class TimelineProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val textViewName: TextView = itemView.findViewById(R.id.tv_timeline_product_title)
        val textViewPrice: TextView = itemView.findViewById(R.id.tv_timeline_price)
        val textViewSeller: TextView = itemView.findViewById(R.id.tv_timeline_seller)
        val imageViewProduct : ImageView = itemView.findViewById(R.id.imageView_timeline_product_image)
        val imageViewSeller  : ImageView = itemView.findViewById(R.id.imageView_timeline_profile)
        private val orderNowButton : Button = itemView.findViewById(R.id.button_order_now)

        init{
            itemView.setOnClickListener(this)
            orderNowButton.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val currentPosition = this.adapterPosition
            when(v?.id){
                orderNowButton.id -> listener.onOrderNowButtonClick(list[currentPosition].product_id)
                else -> listener.onItemClick(list[currentPosition].product_id)
            }
        }
    }

    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimelineProductViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.product_item_timeline, parent, false)
        return TimelineProductViewHolder(itemView)
    }

    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: TimelineProductViewHolder, position: Int) {
        val currentItem = list[position]
        holder.textViewName.text = currentItem.title
        holder.textViewPrice.text = "${currentItem.price_per_unit} ${currentItem.price_type} / ${currentItem.amount_type}"
        holder.textViewSeller.text = currentItem.username
        val images = currentItem.images
        if( images != null && images.size > 0) {
            Log.d("xxx", "#num_images: ${images.size}")
        }
        Glide.with(this.context)
            .load(R.drawable.ic_user)
            .override(200, 200)
            .into(holder.imageViewProduct)
    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newlist: ArrayList<Product>){
        list = newlist
    }

}