package com.example.admin_s.Admin.LoginActivity

import android.R.attr.button
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.example.admin_s.Admin.ProductActivity.MainActivity
import com.example.admin_s.User.User.UserHomeActivity
import com.example.admin_s.databinding.ActivityLoginPageBinding
import com.google.firebase.auth.FirebaseAuth


class LoginPage : AppCompatActivity() {
    lateinit var binding: ActivityLoginPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.LoginBtn.setOnClickListener {
            Login(binding.EmailId.text.toString(), binding.Password.text.toString())

        }

        binding.Admin.setOnClickListener {
            AdminLoginPage()
        }

        hidePassword()

    }

    private fun AdminLoginPage() {
        var intent = Intent(this, AdminLogin::class.java)
        startActivity(intent)
    }

    private fun hidePassword() {

        binding.PasswordShow.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(button: CompoundButton?, boolean: Boolean) {
                if (boolean) {

                    binding.Password.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()

                } else {

                    binding.Password.transformationMethod =
                        PasswordTransformationMethod.getInstance()

                }
            }
        })

    }

    private fun Login(Email: String, Password: String) {
        var firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(Email, Password)
            .addOnSuccessListener { res ->
                var intent = Intent(this, UserHomeActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener { error ->
                Log.e("TAG", "Login: $error")
            }
    }


}