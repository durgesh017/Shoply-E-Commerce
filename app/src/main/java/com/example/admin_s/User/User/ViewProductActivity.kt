package com.example.admin_s.User.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.admin_s.Utills.DBCartData
import com.example.admin_s.Utills.ProductInsert
import com.example.admin_s.databinding.ActivityViewProductBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ViewProductActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setdata()


        binding.BackBtn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun setdata() {


        var productname = intent.getStringExtra("n1")
        var description = intent.getStringExtra("n2")
        var price = intent.getStringExtra("n3")
        var image = intent.getStringExtra("n4")
        var lessprice = intent.getStringExtra("n5")
        var rate = intent.getStringExtra("n6")
        var review = intent.getStringExtra("n7")
        var offer = intent.getStringExtra("n8")
        var cid = intent.getStringExtra("n9")
        var category = intent.getStringExtra("n10")




        Glide.with(this).load(image).into(binding.productImg)
        binding.productNameTxt.setText(productname)
        binding.productPriceTxt.setText(price)
        binding.productDescriptionTxt.setText(description)
        binding.productDiscountTxt.setText(offer)


        wishlist(
            productname,
            description,
            price,
            image,
            lessprice,
            rate,
            review,
            offer,
            cid,
            category
        )


    }

    private fun wishlist(
        productname: String?,
        description: String?,
        price: String?,
        image: String?,
        lessprice: String?,
        rate: String?,
        review: String?,
        offer: String?,
        cid: String?,
        category: String?
    ) {
        binding.Wishlist.setOnClickListener {

            var firebaseDatabase = FirebaseDatabase.getInstance()
            var databaseReference = firebaseDatabase.reference

            var firebaseAuth = FirebaseAuth.getInstance()
            var user = firebaseAuth.currentUser
            var uid = user?.uid

            var ProductInsert = ProductInsert(
                productname.toString(),
                description.toString(),
                price.toString(),
                category.toString(),
                cid!!.toInt(),
                image.toString(),
                lessprice.toString(),
                offer.toString(),
                rate.toString(),
                review.toString()


            )

            databaseReference.child("WishList").child(uid.toString()).push().setValue(ProductInsert)

            Toast.makeText(this, "WishListed SuccessFully.", Toast.LENGTH_SHORT).show()


        }
    }


}