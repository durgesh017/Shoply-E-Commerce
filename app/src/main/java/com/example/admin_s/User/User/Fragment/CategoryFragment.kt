package com.example.admin_s.User.User.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin_s.R
import com.example.admin_s.User.User.Controller.CategoryAdapter
import com.example.admin_s.Utills.DBShowData
import com.example.admin_s.databinding.FragmentCategoryBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CategoryFragment : Fragment() {

    companion object {
        lateinit var sbinding: FragmentCategoryBinding
    }

    var tempcheck = arrayListOf<DBShowData>()
    private var z: Int? = null
    private var temp: String? = null

    var image = arrayOf(
        R.drawable.clothes,
        R.drawable.laptop,
        R.drawable.washing_machine,
        R.drawable.lipstick,
        R.drawable.running_shoes,
        R.drawable.couch,
        R.drawable.watch
    )
    var name = arrayOf(
        "Fashion",
        "Electronics",
        "Appliances",
        "Beauty",
        "Shoes",
        "Furniture",
        "Watches"
    )

    var no = arrayOf(
        1, 2, 3, 4, 5, 6,7
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sbinding = FragmentCategoryBinding.inflate(layoutInflater)
        ReadData()

        return sbinding.root
    }


    private fun ReadData() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var ref = firebaseDatabase.reference


        ref.child("Product").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tempcheck.clear()
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

                    temp = key
                    z = cid.toInt()

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


                    tempcheck.add(dbshow)

                }

                Categories(activity, image, name)

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun Categories(
        activity: FragmentActivity?,
        image: Array<Int>,
        name: Array<String>,
    ) {


        var adapter = CategoryAdapter(activity, image, name,no)
        var layoutManager = LinearLayoutManager(activity)
        sbinding.CategoryRvView.layoutManager = layoutManager
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        sbinding.CategoryRvView.adapter = adapter

    }


}