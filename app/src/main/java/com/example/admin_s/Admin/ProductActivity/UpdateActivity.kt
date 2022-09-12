package com.example.admin_s.Admin.ProductActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.admin_s.Admin.ProductActivity.InsertActivity.Companion.data
import com.example.admin_s.Utills.CategoryInsert
import com.example.admin_s.Utills.ProductInsert
import com.example.admin_s.databinding.ActivityUpdateBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class UpdateActivity : AppCompatActivity() {
    var z: String? = null
    var demo: String? = null
    lateinit var binding: ActivityUpdateBinding
    var id: String? = null
    var temp: Int? = null
    var cid: Int? = null
    var image: Uri? = null
    var uri: Uri? = null
    var category: String = null.toString()
    var list = arrayListOf<CategoryInsert>()
    var store = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        readData()


    }


    private fun getData() {

        var productname = intent.getStringExtra("n1")
        var des = intent.getStringExtra("n2")
        var price = intent.getStringExtra("n3")
        var cat = intent.getStringExtra("n4")
        var id = intent.getStringExtra("n6")
        var img = intent.getStringExtra("n5").toString()
        var key = intent.getStringExtra("n7").toString()
        var lessprice = intent.getStringExtra("n8").toString()
        var offer = intent.getStringExtra("n9").toString()
        var rate = intent.getStringExtra("n10").toString()
        var review = intent.getStringExtra("n11").toString()

        temp = id!!.toInt()

        demo = img


        binding.ProductNameEdtIns.setText(productname)
        binding.ProductDescriptionEdtIns.setText(des)
        binding.ProductPriceEdtIns.setText(price)

        binding.CutoffPrice.setText(lessprice)
        binding.Offer.setText(offer)
        binding.Rate.setText(rate)
        binding.Review.setText(review)
        Glide.with(this).load(img).into(binding.ProductImageIns)




        binding.UpdateBtn.setOnClickListener {

            updateData(img)
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.ProductImageIns.setOnClickListener {
            updateImage()

        }
    }

    private fun updateImage() {
        var intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, 1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {

            image = data?.data!!
            binding.ProductImageIns.setImageURI(image)
            updateImageToStorage()
        }
    }

    private fun updateImageToStorage() {

        var file = File(image.toString())

        var storage = Firebase.storage

        var storageReference = storage.reference.child("${file.name}")

        var upload = storageReference.putFile(image!!)


        upload.addOnSuccessListener { snapshot ->

            snapshot.storage.downloadUrl.addOnSuccessListener { result ->

                var temp = result
                updateData(temp.toString())

            }
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener { error ->

            Toast.makeText(this, "Failed Storage", Toast.LENGTH_SHORT).show()

        }
    }

    private fun updateData1(uri: Uri) {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference
        val key = intent.getStringExtra("n7")

        /*     val productData = ProductInsert(
                 binding.ProductNameEdtIns.text.toString(),
                 binding.ProductPriceEdtIns.text.toString(),
                 binding.ProductDescriptionEdtIns.text.toString(),
                 category,
                 cid!!,
                 uri.toString(),
             )*/

        databaseReference.child("Product").child("${key!!}")


    }

    private fun updateData(uri: String) {

        var firebaseDatabase = FirebaseDatabase.getInstance()
        var databaseReference = firebaseDatabase.reference
        val key = intent.getStringExtra("n7")

        val productData = ProductInsert(
            binding.ProductNameEdtIns.text.toString(),
            binding.ProductDescriptionEdtIns.text.toString(),
            binding.ProductPriceEdtIns.text.toString(),
            category,
            cid!!,
            uri,
            binding.CutoffPrice.text.toString(),
            binding.Offer.text.toString(),
            binding.Rate.text.toString(),
            binding.Review.text.toString()

        )

        databaseReference.child("Product").child("${key!!}").setValue(productData)


    }


    private fun readData() {


        var firebaseDatabase = FirebaseDatabase.getInstance()
        var ref = firebaseDatabase.reference

        ref.child("Category").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                store.clear()
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

        binding.SpinnerIns.setSelection(temp!! - 1)

        binding.SpinnerIns.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, postion: Int, p3: Long) {
                category = data[postion]
                cid = postion + 1

                Toast.makeText(this@UpdateActivity, "${data[postion]}", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


    }
}