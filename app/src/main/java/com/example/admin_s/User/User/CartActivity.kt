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
    var list = arrayListOf<DBCartProduct>()
    var UserAddress = arrayListOf<AddressInsert>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ReadData()
        AddressRead()

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
                list.clear()
                minteger = 0
                for (x in snapshot.children) {
                    var productname = x.child("productname").getValue().toString()
                    var description = x.child("description").getValue().toString()
                    var image = x.child("image").getValue().toString()
                    var price = x.child("price").getValue().toString()
                    var category = x.child("category").getValue().toString()
                    var cid = x.child("cid").getValue().toString()
                    var qu = x.child("qu").getValue().toString()
                    var lessprice = x.child("lessprice").getValue().toString()
                    var offer = x.child("offer").getValue().toString()
                    var rate = x.child("rate").getValue().toString()
                    var review = x.child("review").getValue().toString()


                    // Price Counter
                    minteger = (price.toInt() * qu.toInt()) + minteger


                    /*Toast.makeText(this@CartActivity, "${minteger}", Toast.LENGTH_SHORT).show()*/
                    binding.FinalTotal.setText(minteger.toString())


                    var key = x.key.toString()
                    var dbcart =
                        DBCartProduct(
                            productname,
                            description,
                            image,
                            price,
                            key,
                            category,
                            cid,
                            qu, lessprice, offer, rate, review
                        )


                    list.add(dbcart)

                }
                RvSetup(list)


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }


    private fun Bottom(minteger: Int) {

        var bottomdialog = BottomSheetDialog(this)

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

    private fun AddressRead() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var ref = firebaseDatabase.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid


        ref.child("Address").child(uid.toString())
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
                        var key = x.key.toString()

                        var DBAddress =
                            AddressInsert(
                                name, mobile, flatno, landmark, state, city, pincode, location
                            )


                        UserAddress.add(DBAddress)

                    }


                }

                override fun onCancelled(error: DatabaseError) {

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