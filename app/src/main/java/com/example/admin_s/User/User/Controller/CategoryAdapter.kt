package com.example.admin_s.User.User.Controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin_s.R
import com.example.admin_s.User.User.Fragment.CategoryFragment.Companion.sbinding
import com.example.admin_s.Utills.DBShowData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CategoryAdapter(
    val activity: FragmentActivity?,
    val image: Array<Int>,
    val name: Array<String>,
    val no: Array<Int>,
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    var z: Int = 0
    var list = arrayListOf<DBShowData>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.homecategory, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.icon.setImageResource(image.get(position))
        holder.name.setText(name.get(position))
        CategoryFliter(1)

        holder.icon.setOnClickListener {

            CategoryFliter(no.get(position))

        }
    }

    override fun getItemCount(): Int {
        return image.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var icon = itemView.findViewById<ImageView>(R.id.icon)
        var name = itemView.findViewById<TextView>(R.id.TextCat)
    }

    private fun CategoryFliter(i: Int) {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var ref = firebaseDatabase.reference


        ref.child("Product").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (x in snapshot.children) {
                    var productname = x.child("pname").getValue().toString()
                    var description = x.child("pdescription").getValue().toString()
                    var image = x.child("pimage").getValue().toString()
                    var price = x.child("price").getValue().toString()
                    var category = x.child("pcat").getValue().toString()
                    var cid = x.child("cid").getValue().toString()
                    var lessprice = x.child("lessprice").getValue().toString()
                    var rate = x.child("rate").getValue().toString()
                    var review = x.child("review").getValue().toString()
                    var offer = x.child("offer").getValue().toString()

                    var key = x.key.toString()


                    if (i == cid.toInt()) {
                        var dbshow =
                            DBShowData(
                                productname,
                                description,
                                image,
                                price,
                                key,
                                category,
                                cid,
                                lessprice,
                                rate,
                                review,
                                offer
                            )
                        list.add(dbshow)

                    }


                }

                RecyclerViewSetup(list)

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun RecyclerViewSetup(list: ArrayList<DBShowData>) {
        var adapter = CategoryProductAdapter(activity, list)
        var layoutManager = GridLayoutManager(activity, 2)
        sbinding.CategoryProductRvView.layoutManager = layoutManager
        sbinding.CategoryProductRvView.adapter = adapter

    }
}