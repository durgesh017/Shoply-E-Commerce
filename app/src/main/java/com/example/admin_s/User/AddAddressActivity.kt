package com.example.admin_s.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.admin_s.R
import com.example.admin_s.User.User.CartActivity
import com.example.admin_s.Utills.AddressInsert
import com.example.admin_s.databinding.ActivityAddAddressBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddAddressActivity : AppCompatActivity() {
    private var type: String? = null
    lateinit var binding: ActivityAddAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.BackBtn.setOnClickListener {
            onBackPressed()
        }
        binding.CheckoutBtn.setOnClickListener {
            InsertAddressData()
            Bottom()

        }
        binding.CheckoutBtn.isEnabled = false

        binding.CheckBoxBtn.setOnClickListener {
            binding.CheckoutBtn.isEnabled=!(binding.CheckoutBtn.isEnabled)
        }

    }

    private fun InsertAddressData() {
        var firebaseDatabas = FirebaseDatabase.getInstance()
        var ref = firebaseDatabas.reference

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser
        var uid = user?.uid

        if (binding.RGGroup.getCheckedRadioButtonId() == R.id.HomeRGBtn) {
            type = "Home"
        } else if (binding.RGGroup.getCheckedRadioButtonId() == R.id.OfficeRGBtn) {
            type = "Office"
        }


        var UserAddress = AddressInsert(

            binding.NameEdt.text.toString(),
            binding.MobileEdt.text.toString(),
            binding.FlatEdt.text.toString(),
            binding.LandmarkEdt.text.toString(),
            binding.StateEdt.text.toString(),
            binding.CityEdt.text.toString(),
            binding.PincodeEdt.text.toString(),
            type.toString()


        )


        ref.child("Address").child(uid.toString()).push().setValue(UserAddress)

        /*  Toast.makeText(this@AddAddressActivity, "Successfully", Toast.LENGTH_SHORT).show()*/

    }

    private fun Bottom() {
        var dialog = BottomSheetDialog(this)

        var view = layoutInflater.inflate(R.layout.bottom_sheet_success_dialog, null)

        val Donebtn = view.findViewById<Button>(R.id.DoneBtn)


        Donebtn.setOnClickListener {
            dialog.dismiss()
            var intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        dialog.setCancelable(false)


        dialog.setContentView(view)

        dialog.show()

    }

}