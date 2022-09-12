package com.example.admin_s.User.User

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.admin_s.Utills.DBShowData
import com.example.admin_s.databinding.ActivitySearchBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    var list = arrayListOf<DBShowData>()

    var temp = arrayOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ReadData()

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

                    temp += productname


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
                SearchView()


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun SearchView() {
        val mArrayAdapter = ArrayAdapter(this, R.layout.simple_list_item_1, temp)
        binding.listView.adapter = mArrayAdapter


        Toast.makeText(this, "${temp.size}", Toast.LENGTH_SHORT).show()
        // TextWatcher to check if the EditText text changes
        binding.SearchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do Nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mArrayAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {
                // Do Nothing
            }

        })
    }

}
