package com.example.admin_s.Admin.LoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.admin_s.Admin.ProductActivity.MainActivity
import com.example.admin_s.User.User.UserHomeActivity
import com.example.admin_s.databinding.ActivityAdminLoginBinding
import com.example.admin_s.databinding.ActivityLoginPageBinding
import com.google.firebase.auth.FirebaseAuth

class AdminLogin : AppCompatActivity() {
    lateinit var binding: ActivityAdminLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.LoginBtn.setOnClickListener {
            Login(binding.EmailId.text.toString(), binding.Password.text.toString())

        }

    }

    private fun Login(Email: String, Password: String) {
        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(Email, Password)
            .addOnSuccessListener { res ->
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener { error ->
                Log.e("TAG", "Login: $error")
            }
    }
}