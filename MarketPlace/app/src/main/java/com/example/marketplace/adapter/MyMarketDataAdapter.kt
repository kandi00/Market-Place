package com.example.marketplace.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.R
import com.example.marketplace.model.Product
import com.example.marketplace.util.Constants

class MyMarketDataAdapter(
    private var list: ArrayList<Product>,
    private val context: Context,
    private val listener: OnItemClickListener,
    private val listener_: OnItemLongClickListener
) : RecyclerView.Adapter<MyMarketDataAdapter.MyMarketProductViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(productId: String)
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(productId: String)
    }

    // 1. user defined ViewHolder type - Embedded class!
    inner class MyMarketProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val textViewName: TextView = itemView.findViewById(R.id.tv_my_market_product_title)
        val textViewPrice: TextView = itemView.findViewById(R.id.textView_my_market_price)
        val textViewSeller: TextView = itemView.findViewById(R.id.tv_my_market_seller)
        val imageViewProduct: ImageView = itemView.findViewById(R.id.imageView_my_market_product_image)
        val imageViewSeller: ImageView = itemView.findViewById(R.id.imageView_my_market_profile)
        val imageViewIsActive: ImageView = itemView.findViewById(R.id.sign_is_active)
        val textViewIsActive : TextView = itemView.findViewById(R.id.textView_is_active)

        init{
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(list[currentPosition].product_id)
        }

        override fun onLongClick(p0: View?): Boolean {
            val currentPosition = this.adapterPosition
            listener_.onItemLongClick(list[currentPosition].product_id)
            return true
        }
    }

    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMarketProductViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.product_item_my_market, parent, false)
        return MyMarketProductViewHolder(itemView)
    }


    // 3. Called many times, when we scroll the list
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyMarketProductViewHolder, position: Int) {
        val currentItem = list[position]
        holder.textViewName.text = currentItem.title
        holder.textViewPrice.text = "${currentItem.price_per_unit} ${currentItem.price_type} / ${currentItem.amount_type}"
        holder.textViewSeller.text = currentItem.username

        if (currentItem.is_active){
            holder.textViewIsActive.text = Constants.ACTIVE
            holder.textViewIsActive.setTextColor(Color.parseColor("#00B5C0"))
            /** Active icon */
            Glide.with(this.context)
                .load(R.drawable.active)
                .override(20, 20)
                .into(holder.imageViewIsActive)
        } else{
            holder.textViewIsActive.text = Constants.INACTIVE
            holder.textViewIsActive.setTextColor(Color.parseColor("#878787"))
            /** Inactive icon */
            Glide.with(this.context)
                .load(R.drawable.inactive)
                .override(20, 20)
                .into(holder.imageViewIsActive)
        }
    }

    override fun getItemCount() = list.size

    // Update the list
    fun setData(newlist: ArrayList<Product>){
        list = newlist
    }
}