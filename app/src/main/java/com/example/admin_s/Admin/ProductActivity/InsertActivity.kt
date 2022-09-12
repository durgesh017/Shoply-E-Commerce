package com.example.admin_s.Admin.ProductActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.admin_s.Utills.CategoryInsert
import com.example.admin_s.Utills.ProductInsert
import com.example.admin_s.databinding.ActivityInsertBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class InsertActivity : AppCompatActivity() {
    var image: Uri? = null
    lateinit var uri: Uri
    lateinit var binding: ActivityInsertBinding
    var list = arrayListOf<CategoryInsert>()
    var category: String = null.toString()
    var cid: Int? = null

    companion object {
        var data = arrayOf<String>("Select")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ReadData()

        binding.ProductImageIns.setOnClickListener {
            var intent = Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(intent, 100)
        }


        binding.InsertBtn.setOnClickListener {
            UploadImage()
            Toast.makeText(this@InsertActivity, "Successfully", Toast.LENGTH_SHORT).show()
        }
        binding.BackBtn.setOnClickListener {
            onBackPressed()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (100 == requestCode) {

            uri = data?.data!!
            binding.ProductImageIns.setImageURI(uri)
        }
    }

    private fun UploadImage() {

        var file = File(uri.toString())
        var storage = Firebase.storage
        var ref = storage.reference.child("${file.name}")

        var uploadTask = ref.putFile(uri)

        uploadTask.addOnSuccessListener { snapshot ->
            snapshot.storage.downloadUrl.addOnSuccessListener { result ->

                image = result
                insertdata()
            }
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener { error ->
            Toast.makeText(this, "${error}", Toast.LENGTH_SHORT).show()
        }

    }

    private fun insertdata() {
        var firebaseDatabas = FirebaseDatabase.getInstance()
        var ref = firebaseDatabas.reference


        var ProductInsert = ProductInsert(
            binding.ProductNameEdtIns.text.toString(),
            binding.ProductDescriptionEdtIns.text.toString(),
            binding.ProductPriceEdtIns.text.toString(),
            category,
            cid!!,
            image.toString(),
            binding.CutoffPrice.text.toString(),
            binding.Offer.text.toString(),
            binding.Rate.text.toString(),
            binding.Review.text.toString()


            )


        ref.child("Product").push().setValue(ProductInsert)


        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)


    }

    private fun ReadData() {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var ref = firebaseDatabase.reference

        ref.child("Category").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                data = emptyArray()
                for (x in snapshot.children) {
                    var id = x.child("id").getValue().toString()
                    var cat = x.child("category").getValue().toString()

                    var temp = CategoryInsert(id, cat)
                    list.add(temp)

                    data += x.child("category").getValue().toString()
                    Log.e("TAG", "onDataChange: ${data}")
                }
                setupSpinner(data)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }

    fun setupSpinner(data: Array<String>) {

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
        binding.SpinnerIns.adapter = arrayAdapter
        arrayAdapter.notifyDataSetChanged()


        binding.SpinnerIns.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, postion: Int, p3: Long) {
                category = data[postion]
                cid = postion + 1

                /* Toast.makeText(this@InsertActivity, "${data[postion]}", Toast.LENGTH_SHORT).show()*/
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


    }
}