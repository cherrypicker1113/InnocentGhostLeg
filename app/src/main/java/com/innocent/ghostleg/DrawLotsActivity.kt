package com.innocent.ghostleg

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.innocent.ghostleg.databinding.ActivityDrawLotsBinding

class DrawLotsActivity : AppCompatActivity(), DrawLotsInputListener {
    private lateinit var binding: ActivityDrawLotsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawLotsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, DrawLotsInputFragment()).commit()
    }

    override fun onEnter(num: Int) {
        val bundle = Bundle(1)
        bundle.putInt(DrawLotsPlayFragment.LOTS_COUNT_KEY, num)
        val fragment = DrawLotsPlayFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, fragment).commit()
    }
}