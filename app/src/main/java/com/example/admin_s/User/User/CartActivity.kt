package com.example.admin_s.User.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin_s.R
import com.example.admin_s.User.AddAddressActivity
import com.example.admin_s.User.User.Controller.AddressShowAdapter
import com.example.admin_s.User.User.Controller.CartShowAdapter
import com.example.admin_s.Utills.AddressInsert
import com.example.admin_s.Utills.DBCartProduct
import com.example.admin_s.databinding.ActivityCartBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartActivity : AppCompatActivity() {

    lateinit var binding: ActivityCartBinding
    var quenity: Int? = null
    private var total: Int? = null

    private var minteger: Int = 0
    private var temp: Int = 0
    var UserAddress = arrayListOf<AddressInsert>()


    companion object {

        lateinit var bottomdialog: BottomSheetDialog

        var productname1: String? = null
        var description1: String? = null
        var image1: String? = null
        var price1: String? = null
        var category1: String? = null
        var cid1: String? = null
        var qu1: String? = null
        var lessprice1: String? = null
        var offer1: String? = null
        var rate1: String? = null
        var review1: String? = null
        var cartlist = arrayListOf<DBCartProduct>()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ReadData()
        readAddress()

        binding.CheckoutBtn.setOnClickListener {
            Bottom(minteger)
        }
        binding.FinalTotal.setText(minteger.toString())

        binding.BackBtn.setOnClickListener {
            onBackPressed()
        }

    }


    private fun ReadData() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var ref = firebaseDatabase.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid


        ref.child("Cart").child(uid.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cartlist.clear()
                minteger = 0
                for (x in snapshot.children) {
                    productname1 = x.child("productname").getValue().toString()
                    description1 = x.child("description").getValue().toString()
                    image1 = x.child("image").getValue().toString()
                    price1 = x.child("price").getValue().toString()
                    category1 = x.child("category").getValue().toString()
                    cid1 = x.child("cid").getValue().toString()
                    qu1 = x.child("qu").getValue().toString()
                    lessprice1 = x.child("lessprice").getValue().toString()
                    offer1 = x.child("offer").getValue().toString()
                    rate1 = x.child("rate").getValue().toString()
                    review1 = x.child("review").getValue().toString()


                    // Price Counter
                    minteger = (price1!!.toInt() * qu1!!.toInt()) + minteger


                    /*Toast.makeText(this@CartActivity, "${minteger}", Toast.LENGTH_SHORT).show()*/
                    binding.FinalTotal.setText(minteger.toString())


                    var key = x.key.toString()
                    var dbcart =
                        DBCartProduct(
                            productname1!!,
                            description1!!,
                            image1!!,
                            price1!!,
                            key,
                            category1!!,
                            cid1!!,
                            qu1!!, lessprice1!!, offer1!!, rate1!!, review1!!
                        )


                    cartlist.add(dbcart)

                }
                RvSetup(cartlist)


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }


    private fun Bottom(minteger: Int) {

        bottomdialog = BottomSheetDialog(this)

        var view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)

        val btnClose = view.findViewById<ImageView>(R.id.CloseBtn)
        var Addressbtn = view.findViewById<TextView>(R.id.AddAddress)
        var AddressRvSetup = view.findViewById<RecyclerView>(R.id.AddressRvSetup)

        Addressbtn.setOnClickListener {
            var intent = Intent(this, AddAddressActivity::class.java)
            startActivity(intent)

            bottomdialog.dismiss()
        }




        btnClose.setOnClickListener {
            bottomdialog.dismiss()
        }

        /*     btnClose.setOnClickListener {
                 val amt = 1

                 // rounding off the amount.
                 val amount = Math.round(amt.toFloat() * 100).toInt()

                 // on below line we are
                 // initializing razorpay account
                 val checkout = Checkout()

                 // on the below line we have to see our id.
                 checkout.setKeyID("rzp_test_KVmgulfD5V2DZB")

                 // set image
                 checkout.setImage(R.drawable.shoply)

                 // initialize json object
                 val obj = JSONObject()
                 try {
                     // to put name
                     obj.put("name", "Shoply")

                     // put description
                     obj.put("description", "Shopping")

                     // to set theme color
                     obj.put("theme.color", "")

                     // put the currency
                     obj.put("currency", "INR")

                     // put amount
                     obj.put("amount", amount)

                     // put mobile number
                     obj.put("prefill.contact", "9723808873")

                     // put email
                     obj.put("prefill.email", "durgeshmishra6099@gmail.com")

                     // open razorpay to checkout activity
                     checkout.open(this, obj)
                 } catch (e: JSONException) {
                     e.printStackTrace()
                 }

             }*/


        var adapter = AddressShowAdapter(this, UserAddress)
        var layoutManager = LinearLayoutManager(this)
        AddressRvSetup.layoutManager = layoutManager
        AddressRvSetup.adapter = adapter



        bottomdialog.setCancelable(false)


        bottomdialog.setContentView(view)

        bottomdialog.show()

    }

    private fun readAddress() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid.toString()

        databaseReference.child("Address").child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    UserAddress.clear()

                    for (x in snapshot.children) {

                        var name = x.child("name").getValue().toString()
                        var mobile = x.child("mobile").getValue().toString()
                        var flatno = x.child("flatno").getValue().toString()
                        var landmark = x.child("landmark").getValue().toString()
                        var state = x.child("state").getValue().toString()
                        var city = x.child("city").getValue().toString()
                        var pincode = x.child("pincode").getValue().toString()
                        var location = x.child("location").getValue().toString()

                        var address = AddressInsert(
                            name,
                            mobile,
                            flatno,
                            landmark,
                            state,
                            city,
                            pincode,
                            location
                        )

                        UserAddress.add(address)

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }


    private fun RvSetup(list: ArrayList<DBCartProduct>) {
        var adapter = CartShowAdapter(this, list)
        var layoutManager = LinearLayoutManager(this)
        binding.CartRvView.layoutManager = layoutManager
        binding.CartRvView.adapter = adapter
    }


}