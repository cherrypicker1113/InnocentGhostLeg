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

    override fun onCompleteDrawLotsInput(totalCount: Int, selectCount: Int) {
        val bundle = Bundle(1)
        bundle.putInt(DrawLotsPlayFragment.TOTAL_COUNT_KEY, totalCount)
        bundle.putInt(DrawLotsPlayFragment.SELECT_COUNT_KEY, selectCount)
        val fragment = DrawLotsPlayFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, fragment).commit()
    }
}