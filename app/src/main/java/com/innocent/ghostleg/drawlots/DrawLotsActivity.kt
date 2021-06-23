package com.innocent.ghostleg.drawlots

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.innocent.ghostleg.databinding.ActivityDrawLotsBinding

class DrawLotsActivity : AppCompatActivity(), DrawLotsInputListener, DrawLotsPlayFragmentListener {
    private lateinit var binding: ActivityDrawLotsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawLotsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showInputFragment()
    }

    fun showInputFragment() {
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, DrawLotsInputFragment()).commit()

    }

    override fun onCompleteDrawLotsInput(totalCount: Int, winNameList: Array<String>) {
        val bundle = Bundle(1)
        bundle.putInt(DrawLotsPlayFragment.TOTAL_COUNT_KEY, totalCount)
        bundle.putStringArray(DrawLotsPlayFragment.WIN_NAME_LIST_KEY, winNameList)
        val fragment = DrawLotsPlayFragment().setListener(this)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, fragment).commit()
    }

    override fun onRestartDrawLots() {
        showInputFragment()
    }
}