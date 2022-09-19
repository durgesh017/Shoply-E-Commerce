package com.example.admin_s.User.User.Controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.admin_s.R
import com.example.admin_s.User.User.CartActivity
import com.example.admin_s.User.User.CartActivity.Companion.bottomdialog
import com.example.admin_s.User.User.CartActivity.Companion.cartlist
import com.example.admin_s.Utills.AddressInsert
import com.example.admin_s.Utills.DBOrder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


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


        holder.TypeText.setText(UserAddress.get(position).location)
        holder.NameText.setText(UserAddress.get(position).name)
        holder.AddressText.setText(UserAddress.get(position).flatno)




        holder.Temp.setOnClickListener {
            InsertOrderData(position)
            Toast.makeText(cartActivity, "${UserAddress[position].name}", Toast.LENGTH_SHORT).show()
            bottomdialog.dismiss()
        }


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


    private fun InsertOrderData(position: Int) {
        var firebaseDatabas = FirebaseDatabase.getInstance()
        var ref = firebaseDatabas.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid

        for (x in cartlist) {
            var Order = DBOrder(


                x.productname.toString(),
                x.description.toString(),
                x.image.toString(),
                x.price.toString(),
                x.category.toString(),
                x.cid.toString(),
                x.qu.toString(),
                x.lessprice.toString(),
                x.offer.toString(),
                x.rate.toString(),
                x.review.toString(),

                UserAddress.get(position).name,
                UserAddress.get(position).mobile,
                UserAddress.get(position).flatno,
                UserAddress.get(position).landmark,
                UserAddress.get(position).state,
                UserAddress.get(position).city,
                UserAddress.get(position).pincode,
                UserAddress.get(position).location,


                )
            ref.child("Order").child(uid.toString()).push().setValue(Order)
        }


        /*  Toast.makeText(this@AddAddressActivity, "Successfully", Toast.LENGTH_SHORT).show()*/

    }


}