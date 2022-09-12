package com.example.admin_s.User.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin_s.R
import com.example.admin_s.User.User.Controller.CartShowAdapter
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ReadData()

        binding.CheckoutBtn.setOnClickListener {
            Bottom()
        }
    }

    private fun Bottom() {
        var dialog = BottomSheetDialog(this)

        var view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)

        val btnClose = view.findViewById<Button>(R.id.idBtnDismiss)

        btnClose.setOnClickListener {

            dialog.dismiss()
        }

        dialog.setCancelable(false)


        dialog.setContentView(view)

        dialog.show()

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


                    Toast.makeText(this@CartActivity, "${minteger}", Toast.LENGTH_SHORT).show()
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

    private fun RvSetup(list: ArrayList<DBCartProduct>) {
        var adapter = CartShowAdapter(this, list)
        var layoutManager = LinearLayoutManager(this)
        binding.CartRvView.layoutManager = layoutManager
        binding.CartRvView.adapter = adapter
    }
}