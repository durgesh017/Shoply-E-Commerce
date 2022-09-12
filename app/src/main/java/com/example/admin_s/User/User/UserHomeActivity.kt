package com.example.admin_s.User.User

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.admin_s.R
import com.example.admin_s.User.User.Fragment.AccountFragment
import com.example.admin_s.User.User.Fragment.CategoryFragment
import com.example.admin_s.User.User.Fragment.HomeFragment
import com.example.admin_s.databinding.ActivityUserHomeBinding

class UserHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadfragment(HomeFragment())

        val homeFragment = HomeFragment()
        val categoryFragment = CategoryFragment()
        val accountFragment = AccountFragment()

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.HomeBottom -> loadfragment(homeFragment)
                R.id.CategoryBottom -> loadfragment(categoryFragment)
                R.id.UserBottom -> loadfragment(accountFragment)
                R.id.SearchBottom -> AddActivity(SearchActivity())
                R.id.CartBottom -> AddActivity(CartActivity())

            }
            true
        }


    }

    private fun AddActivity(Activity: Activity) {
        var intent = Intent(this, Activity::class.java)
        startActivity(intent)
    }

    fun loadfragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.Viewpager, fragment)
            commit()
        }
    }

}