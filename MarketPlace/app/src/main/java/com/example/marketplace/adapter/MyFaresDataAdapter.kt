package com.example.marketplace.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.R
import com.example.marketplace.model.Order
import com.example.marketplace.util.Constants
import com.google.android.material.textfield.TextInputLayout

class MyFaresDataAdapter(
    private var list: ArrayList<Order>,
    private var stateIsEditable: Boolean,
    private val context: Context,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MyFaresDataAdapter.MyFaresViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(orderId: String)
        fun onUpdateOrderClick(orderId: String, status: String)
    }

    /** 1. user defined ViewHolder type - Embedded class! */
    inner class MyFaresViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener,
        AdapterView.OnItemClickListener {
        val textViewUserOrBuyerName: TextView = itemView.findViewById(R.id.tv_my_fares_buyer_name)
        val autoCompleteTextView: AutoCompleteTextView =
            itemView.findViewById(R.id.autoCompleteTextView)
        val textInputLayout: TextInputLayout = itemView.findViewById(R.id.my_fares_order_states)
        val textViewOrderState: TextView = itemView.findViewById(R.id.tv_my_fares_order_state)
        val textViewProductTitle: TextView = itemView.findViewById(R.id.tv_my_fares_product_title)
        val textViewProductAmount: TextView = itemView.findViewById(R.id.textView_my_fares_amount)
        val textViewPrice: TextView = itemView.findViewById(R.id.textView_my_fares_price)
        val imageViewOrderStatus: ImageView = itemView.findViewById(R.id.my_fares_order_state_image)
        val arrowButton: ImageButton = itemView.findViewById(R.id.arrow_button)
        val textViewDescription: TextView =
            itemView.findViewById(R.id.textView_my_fares_description)
        val imageViewSellerBuyer: ImageView = itemView.findViewById(R.id.imageView_my_fares_profile)
        val imageViewProduct: ImageView =
            itemView.findViewById(R.id.imageView_my_fares_product_image)

        init {
            itemView.setOnClickListener(this)
            arrowButton.setOnClickListener(this)
            autoCompleteTextView.setOnItemClickListener(this)
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                /**Change the visibility of the description*/
                arrowButton.id -> {
                    if (textViewDescription.visibility == View.GONE) {
                        textViewDescription.visibility = View.VISIBLE
                        arrowButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                    } else {
                        textViewDescription.visibility = View.GONE
                        arrowButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                    }
                }
                /**Navigate to order detail screen*/
                else -> {
                    listener.onItemClick(list[this.adapterPosition].order_id)
                }
            }
        }

        override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            val status = when (autoCompleteTextView.text.toString()) {
                context.getString(R.string.incoming_order) -> Constants.INCOMING
                context.getString(R.string.accepted_order) -> Constants.ACCEPTED
                context.getString(R.string.declined_order) -> Constants.DECLINED
                context.getString(R.string.delivering_order) -> Constants.DELIVERING
                context.getString(R.string.delivered_order) -> Constants.DELIVERED
                else -> ""
            }
            if (status == "DECLINED") {
                /** Declined order icon */
                Glide.with(context)
                    .load(R.drawable.ic_button_x)
                    .override(25, 25)
                    .into(imageViewOrderStatus)
            } else {
                Glide.with(context)
                    .load(R.drawable.ic_accept_order)
                    .override(25, 25)
                    .into(imageViewOrderStatus)
            }
            listener.onUpdateOrderClick(list[this.adapterPosition].order_id, status)
        }
    }

    /** 2. Called only a few times = number of items on screen + a few more ones */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFaresViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.ongoing_sale_order_item, parent, false)
        return MyFaresViewHolder(itemView)
    }

    /** 3. Called many times, when we scroll the list*/
    override fun onBindViewHolder(holder: MyFaresViewHolder, position: Int) {
        val currentItem = list[position]
        holder.textViewProductTitle.text = currentItem.title
        holder.textViewProductAmount.text = context.getString(R.string.amount, currentItem.units)
        holder.textViewPrice.text = context.getString(R.string.price, currentItem.price_per_unit)
        holder.textViewDescription.text = currentItem.description
        holder.textViewOrderState.text = currentItem.status
        holder.textViewDescription.visibility = View.GONE
        holder.arrowButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)

        /** Get corresponding status for view */
        val statusText: String = when (currentItem.status) {
            Constants.OPEN -> context.getString(R.string.incoming_order)
            Constants.INCOMING -> context.getString(R.string.incoming_order)
            Constants.ACCEPTED -> context.getString(R.string.accepted_order)
            Constants.DECLINED -> context.getString(R.string.declined_order)
            Constants.DELIVERING -> context.getString(R.string.delivering_order)
            Constants.DELIVERED -> context.getString(R.string.delivered_order)
            else -> "undefined"
        }

        /**Check whether this item appears in sales screen or orders screen
        On the sales screen appears the buyer name and product states is editable
        On the orders screen appears the seller name and product state isn't editable*/
        if (stateIsEditable) {
            holder.textInputLayout.visibility = View.VISIBLE
            holder.textViewOrderState.visibility = View.GONE
            holder.textViewUserOrBuyerName.text = currentItem.username
            /** Set corresponding status in view */
            holder.autoCompleteTextView.setText(statusText)
        } else {
            holder.textViewOrderState.visibility = View.VISIBLE
            holder.textInputLayout.visibility = View.GONE
            holder.textViewUserOrBuyerName.text = currentItem.owner_username
            /** Set corresponding status in view */
            holder.textViewOrderState.text = statusText
        }

        /**Set state image depending on product status */
        if (currentItem.status == "DECLINED") {
            /** Declined order icon */
            Glide.with(this.context)
                .load(R.drawable.ic_button_x)
                .override(25, 25)
                .into(holder.imageViewOrderStatus)
        } else {
            Glide.with(this.context)
                .load(R.drawable.ic_accept_order)
                .override(25, 25)
                .into(holder.imageViewOrderStatus)
        }

        /**Set options for product states*/
        val states = context.resources.getStringArray(R.array.states)
        val arrayAdapter = ArrayAdapter(context, R.layout.dropdown_item_for_order_state, states)
        holder.autoCompleteTextView.setAdapter(arrayAdapter)
    }

    override fun getItemCount() = list.size

    /** Update the list and the state*/
    fun setData(newList: ArrayList<Order>, stateIsEditable: Boolean) {
        list = newList
        this.stateIsEditable = stateIsEditable
    }
}