package com.example.admin_s.Admin.ProductActivity.Controller

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.admin_s.Admin.ProductActivity.MainActivity
import com.example.admin_s.Admin.ProductActivity.UpdateActivity
import com.example.admin_s.R
import com.example.admin_s.Utills.DBShowData
import com.google.firebase.database.FirebaseDatabase

class ShowProductView(val mainActivity: MainActivity, val list: ArrayList<DBShowData>) :
    RecyclerView.Adapter<ShowProductView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.productview, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pname.setText(list.get(position).productname)
        holder.des.setText(list.get(position).description)
        holder.price.setText(list.get(position).price)
        Glide.with(mainActivity).load(list.get(position).image).into(holder.img)
        holder.cat.setText(list.get(position).category)

        holder.Update.setOnClickListener {


            var intent = Intent(mainActivity, UpdateActivity::class.java)
            intent.putExtra("n1", list[position].productname)
            intent.putExtra("n2", list[position].description)
            intent.putExtra("n3", list[position].price)
            intent.putExtra("n4", list[position].category)
            intent.putExtra("n5", list[position].image)
            intent.putExtra("n6", list[position].cid)
            intent.putExtra("n7", list[position].key)
            intent.putExtra("n8", list[position].lessprice)
            intent.putExtra("n9", list[position].offer)
            intent.putExtra("n10", list[position].rate)
            intent.putExtra("n11", list[position].review)

            mainActivity.startActivity(intent)
        }

        holder.Delete.setOnClickListener {
            delete(position)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pname = itemView.findViewById<TextView>(R.id.TestProductText)
        var des = itemView.findViewById<TextView>(R.id.TestdesText)
        var price = itemView.findViewById<TextView>(R.id.TestPriceText)
        var img = itemView.findViewById<ImageView>(R.id.img)
        var cat = itemView.findViewById<TextView>(R.id.category_txt2)
        var Delete = itemView.findViewById<CardView>(R.id.deleteCardView2)
        var Update = itemView.findViewById<CardView>(R.id.EditProduct)

    }

    private fun delete(position: Int) {

        var firebaseDatabase = FirebaseDatabase.getInstance()

        var databaseReference = firebaseDatabase.reference

        databaseReference.child("Product").child("${list.get(position).key}")
            .removeValue()

        list.clear()
    }

}