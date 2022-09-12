package com.example.admin_s.User.User.Controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.admin_s.R
import com.example.admin_s.User.User.CartActivity
import com.example.admin_s.Utills.DBCartProduct
import com.example.admin_s.Utills.DBCartShowData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class CartShowAdapter(val cartActivity: CartActivity, val list: ArrayList<DBCartProduct>) :
    RecyclerView.Adapter<CartShowAdapter.ViewHolder>() {


    private var minteger: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.testingcartview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pname.setText(list.get(position).productname)
        holder.des.setText(list.get(position).description)
        holder.price.setText(list.get(position).price)
        Glide.with(cartActivity).load(list.get(position).image).into(holder.img)

        holder.Quntiity.setText(list.get(position).qu)

        holder.increment.setOnClickListener {

            var temp = list.get(position).qu.toInt()


            if (temp < 10) {
                minteger = temp + 1
                updateData(position, minteger)

            }

            temp = minteger
        }
        holder.decrement.setOnClickListener {

            var temp = list.get(position).qu.toInt()


            if (temp > 1) {
                minteger = temp - 1
                updateData(position, minteger)
            }

            temp = minteger
        }

        holder.lessprice.setText(list.get(position).lessprice)
        holder.offer.setText(list.get(position).offer)


    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var pname = itemView.findViewById<TextView>(R.id.TempProductText)
        var des = itemView.findViewById<TextView>(R.id.TempProductDes)
        var price = itemView.findViewById<TextView>(R.id.TempPriceText)
        var img = itemView.findViewById<ImageView>(R.id.ShowImageProduct4)
        var lessprice = itemView.findViewById<TextView>(R.id.TempLessPrice)
        var increment = itemView.findViewById<ImageView>(R.id.increment)
        var decrement = itemView.findViewById<ImageView>(R.id.decrement)
        var Quntiity = itemView.findViewById<TextView>(R.id.Quntiity)
        var offer = itemView.findViewById<TextView>(R.id.TempOfferText)


    }

    private fun updateData(position: Int, qu: Int) {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid.toString()

        var dbshow = DBCartShowData(
            list.get(position).productname,
            list.get(position).description,
            list.get(position).image,
            list.get(position).price,
            list.get(position).category,
            list.get(position).cid,
            qu.toString()

        )

        databaseReference.child("Cart").child(uid).child("${list.get(position).key}")
            .setValue(dbshow)


    }


}