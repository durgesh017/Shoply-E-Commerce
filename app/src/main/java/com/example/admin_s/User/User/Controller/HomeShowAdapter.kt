package com.example.admin_s.User.User.Controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.admin_s.R
import com.example.admin_s.Utills.DBCartData
import com.example.admin_s.Utills.DBShowData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HomeShowAdapter(val activity: FragmentActivity?, val list: ArrayList<DBShowData>) :
    RecyclerView.Adapter<HomeShowAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.testingview4, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pname.setText(list.get(position).productname)
        holder.des.setText(list.get(position).description)
        holder.price.setText(list.get(position).price)
        Glide.with(activity!!).load(list.get(position).image).into(holder.img)
        holder.lessprice.setText(list.get(position).lessprice)
/*        holder.rate.setText(list.get(position).rate)
        holder.review.setText(list.get(position).review)*/
        holder.offer.setText(list.get(position).offer)

        holder.Add.setOnClickListener {
            //AddToCart(position)
            Add(position)

        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pname = itemView.findViewById<TextView>(R.id.TempProductText2)
        var des = itemView.findViewById<TextView>(R.id.TempProductDes2)
        var price = itemView.findViewById<TextView>(R.id.TempPriceText2)
        var img = itemView.findViewById<ImageView>(R.id.ShowImageProduct2)
        var lessprice = itemView.findViewById<TextView>(R.id.TempLessPrice2)
       /* var rate = itemView.findViewById<TextView>(R.id.TempRateText2)
        var review = itemView.findViewById<TextView>(R.id.TempReviewText2)*/
        var offer = itemView.findViewById<TextView>(R.id.TempOfferText2)
        var Add = itemView.findViewById<CardView>(R.id.Add)

    }

    /* private fun AddToCart(position: Int) {
         var firebaseDatabase = FirebaseDatabase.getInstance()
         var ref = firebaseDatabase.reference

         var firebaseAuth = FirebaseAuth.getInstance()
         var user = firebaseAuth.currentUser
         var uid = user?.uid

         var quentity: Int = 1

         var temp = DBCartData(
             list.get(position).productname,
             list.get(position).description,
             list.get(position).price,
             list.get(position).category,
             list.get(position).cid.toInt(),
             list.get(position).image,
             quentity,
             qu.toString()
         )


         var cart = ProductInsert(
             list.get(position).productname,
             list.get(position).description,
             list.get(position).price,
             list.get(position).category,
             list.get(position).cid.toInt(),
             list.get(position).image,

             )

         ref.child("Cart").child(uid.toString()).push().setValue(temp)


     }*/
    private fun Add(position: Int) {
        var firebaseDatabase = FirebaseDatabase.getInstance()
        var ref = firebaseDatabase.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid


        var temp = DBCartData(
            list.get(position).productname,
            list.get(position).description,
            list.get(position).image,
            list.get(position).price,
            list.get(position).category,
            list.get(position).cid,
            1,
            list.get(position).lessprice,
            list.get(position).offer,
            list.get(position).rate,
            list.get(position).review,

        )



        ref.child("Cart").child(uid.toString()).push().setValue(temp)


    }
}