package com.example.admin_s.User.User.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin_s.R
import com.example.admin_s.databinding.FragmentWishlistBinding


class WishlistFragment : Fragment() {

    lateinit var binding: FragmentWishlistBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWishlistBinding.inflate(layoutInflater)

        wishlist()

        return binding.root
    }

    private fun wishlist() {




    }


}