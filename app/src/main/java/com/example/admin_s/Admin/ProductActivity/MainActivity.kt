package com.example.admin_s.Admin.ProductActivity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.admin_s.Admin.LoginActivity.LoginPage
import com.example.admin_s.Admin.ProductActivity.Controller.ShowProductView
import com.example.admin_s.R
import com.example.admin_s.Utills.CategoryInsert
import com.example.admin_s.Utills.DBShowData
import com.example.admin_s.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    var cat: Int? = null
    lateinit var binding: ActivityMainBinding
    var list = arrayListOf<DBShowData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ReadData()

        binding.AddCategory.setOnClickListener {
            AddCategory()

        }

        binding.InsertDataBtn.setOnClickListener {
            var intent = Intent(this, InsertActivity::class.java)
            startActivity(intent)
        }
        binding.MenuBtn.setOnClickListener {
                Logout()
        }


    }

    private fun Logout() {
        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
        var intent = Intent(this, LoginPage::class.java)
        startActivity(intent)
    }

    private fun AddCategory() {
        var dialog = Dialog(this)
        dialog.setContentView(R.layout.dialogbox)
        dialog.show()

        var Add = dialog.findViewById<Button>(R.id.AddBtn)
        var id = dialog.findViewById<EditText>(R.id.CategoryIdEdtTxt)
        var category = dialog.findViewById<EditText>(R.id.AddCategoryEdt)
        var CategoryId = dialog.findViewById<TextView>(R.id.CategoryID)
        var firebaseDatabase = FirebaseDatabase.getInstance()

        var databaseReference = firebaseDatabase.reference

        databaseReference.child("Category").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (x in snapshot.children) {

                    var category = x.child("id").getValue().toString()

                    cat = category.toInt()
                }

                CategoryId.text = (cat!! + 1).toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        Add.setOnClickListener {

            if (id.length() == 0) {

                id.error = "Id"

            } else if (category.length() == 0) {

                category.error = "Name"
            } else {
                var dbCategory = CategoryInsert(
                    id.text.toString(),
                    category.text.toString()
                )

                databaseReference.child("Category").push().setValue(dbCategory)

                dialog.dismiss()
            }


        }


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
                RvSetup(list)


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    private fun RvSetup(list: ArrayList<DBShowData>) {
        var adapter = ShowProductView(this, list)
        var layoutManager = GridLayoutManager(this, 2)
        binding.ProductShowRv.layoutManager = layoutManager
        binding.ProductShowRv.adapter = adapter
    }
}