package com.example.admin_s.User.User.Controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.admin_s.R
import com.example.admin_s.User.User.CartActivity
import com.example.admin_s.Utills.AddressInsert


class AddressShowAdapter(
    val cartActivity: CartActivity,
    val UserAddress: ArrayList<AddressInsert>
) :
    RecyclerView.Adapter<AddressShowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.addressview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        var suggestion = UserAddress.get(position)
        holder.TypeText.setText(UserAddress.get(position).location)
        holder.NameText.setText(UserAddress.get(position).name)
        holder.AddressText.setText(UserAddress.get(position).flatno)


        UserAddress.get(position).name
        UserAddress.get(position).mobile
        UserAddress.get(position).flatno
        UserAddress.get(position).landmark
        UserAddress.get(position).state
        UserAddress.get(position).city
        UserAddress.get(position).pincode
        UserAddress.get(position).location

    }

    override fun getItemCount(): Int {
        return UserAddress.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var TypeText = itemView.findViewById<TextView>(R.id.TypeText)
        var NameText = itemView.findViewById<TextView>(R.id.NameText)
        var AddressText = itemView.findViewById<TextView>(R.id.AddressText)
        var CheckBox = itemView.findViewById<CheckBox>(R.id.checkbox)
        var Temp = itemView.findViewById<RelativeLayout>(R.id.TempRelative)


    }


}