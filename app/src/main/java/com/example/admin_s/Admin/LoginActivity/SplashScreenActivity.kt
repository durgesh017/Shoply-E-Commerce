package com.example.admin_s.Admin.LoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.admin_s.Admin.ProductActivity.MainActivity
import com.example.admin_s.User.User.UserHomeActivity
import com.example.admin_s.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser

        Handler().postDelayed({
            if (user != null) {
                val intent = Intent(this, UserHomeActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
            }
            finish()
        }, 2500)
    }
}