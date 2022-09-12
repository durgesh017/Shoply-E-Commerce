package com.example.admin_s.User.User.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin_s.Admin.LoginActivity.LoginPage
import com.example.admin_s.R
import com.example.admin_s.User.User.Controller.HomeCategoryAdapter
import com.example.admin_s.User.User.Controller.HomeShowAdapter
import com.example.admin_s.User.User.SearchActivity
import com.example.admin_s.Utills.DBShowData
import com.example.admin_s.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class HomeFragment : Fragment() {
    private var z: Int? = null
    private var temp: String? = null
    lateinit var binding: FragmentHomeBinding
    var list = arrayListOf<DBShowData>()
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        ReadData()
        Categories()

        binding.Clothes.setOnClickListener {
            Fliter(3)

        }
        binding.Sports.setOnClickListener {
            Fliter(1)

        }

        binding.SearchBtn.setOnClickListener {
            var intent = Intent(activity, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.Menubtn.setOnClickListener {
            Logout()
        }

        return binding.root

    }

    private fun Logout() {
        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        var intent = Intent(activity, LoginPage::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        imageSlider()
    }


    private fun imageSlider() {

// Register lifecycle. For activity this will be lifecycle/getLifecycle() and for fragment it will be viewLifecycleOwner/getViewLifecycleOwner().
        binding.carousel.registerLifecycle(lifecycle)

        val list = mutableListOf<CarouselItem>()

// Image URL with caption

        list.add(
            CarouselItem(
                imageUrl = "https://cdn.shopify.com/s/files/1/0303/2147/5643/files/22_1000x1000.jpg?v=1658132350"
            )
        )



// Just image URL
        list.add(
            CarouselItem(
                imageUrl = "https://cdn.shopify.com/s/files/1/0580/7639/7731/files/mens_fashion_banner_3_74d032f7-e76e-476a-89dd-51636dfc769d.jpg?v=1652711769"
            )
        )


// Image URL with header
        val headers = mutableMapOf<String, String>()
        headers["header_key"] = "header_value"

        list.add(
            CarouselItem(
                imageUrl = "https://image.shutterstock.com/image-vector/summer-sale-background-layout-banners-260nw-678851590.jpg",

                )
        )
        list.add(
            CarouselItem(
                imageUrl = "https://www.v2retail.com/wp-content/uploads/2021/02/WEBSITE-BANNER-V2RETAIL-2A.jpg",

                )
        )
        list.add(
            CarouselItem(
                imageUrl = "https://mir-s3-cdn-cf.behance.net/project_modules/fs/2bbcfa99737217.5ef9be3dbb9a9.jpg"

            )
        )




// ...

        binding.carousel.setData(list)
    }


    private fun ReadData() {

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


                    list.add(dbshow)

                }

                RecyclerViewSetup(list)

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun Fliter(i: Int) {

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

    private fun Categories() {


        var adapter = HomeCategoryAdapter(activity, image, name)
        var layoutManager = LinearLayoutManager(activity)
        binding.CategoryRvView.layoutManager = layoutManager
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.CategoryRvView.adapter = adapter

    }


    private fun RecyclerViewSetup(list: ArrayList<DBShowData>) {
        var adapter = HomeShowAdapter(activity, list)
        var layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.RvView.layoutManager = layoutManager
        binding.RvView.adapter = adapter
    }


}