package com.example.admin_s.User.User.Controller

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.admin_s.R
import com.example.admin_s.User.User.Fragment.CategoryFragment

class HomeCategoryAdapter(
    val activity: FragmentActivity?,
    val image: Array<Int>,
    val name: Array<String>
) :
    RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.homecategory, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.icon.setImageResource(image.get(position))
        holder.name.setText(name.get(position))

        holder.icon.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return image.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var icon = itemView.findViewById<ImageView>(R.id.icon)
        var name = itemView.findViewById<TextView>(R.id.TextCat)
    }


}