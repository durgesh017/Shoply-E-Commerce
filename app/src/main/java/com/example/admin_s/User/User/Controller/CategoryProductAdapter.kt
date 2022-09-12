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
import com.example.admin_s.Utills.DBShowData

class CategoryProductAdapter(val activity: FragmentActivity?, val list: ArrayList<DBShowData>) :
    RecyclerView.Adapter<CategoryProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.testingview5, parent, false)
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

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pname = itemView.findViewById<TextView>(R.id.TempProductText3)
        var des = itemView.findViewById<TextView>(R.id.TempProductDes3)
        var price = itemView.findViewById<TextView>(R.id.TempPriceText3)
        var img = itemView.findViewById<ImageView>(R.id.ShowImageProduct3)
        var lessprice = itemView.findViewById<TextView>(R.id.TempLessPrice3)

        /* var rate = itemView.findViewById<TextView>(R.id.TempRateText2)
         var review = itemView.findViewById<TextView>(R.id.TempReviewText2)*/
        var offer = itemView.findViewById<TextView>(R.id.TempOfferText3)
        var Add = itemView.findViewById<CardView>(R.id.Add3)
    }
}