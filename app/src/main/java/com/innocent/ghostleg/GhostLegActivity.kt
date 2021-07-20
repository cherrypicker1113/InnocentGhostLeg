package com.innocent.ghostleg

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.innocent.ghostleg.databinding.ActivityGhostLegBinding

class GhostLegActivity : AppCompatActivity(), GhostLegInputListener, GhostLegPlayFragmentListener {
    private lateinit var binding: ActivityGhostLegBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGhostLegBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showInputFragment()
    }

    fun showInputFragment() {
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, GhostLegInputFragment()).commit()
    }

    override fun onCompleteGhostLegInput(totalCount: Int, playerList: Array<String>, resultList: Array<String>) {
        val bundle = Bundle(1)
        bundle.putInt(GhostLegPlayFragment.TOTAL_COUNT_KEY, totalCount)
        bundle.putStringArray(GhostLegPlayFragment.PLAYER_LIST_KEY, playerList)
        bundle.putStringArray(GhostLegPlayFragment.RESULT_LIST_KEY, resultList)
        val fragment = GhostLegPlayFragment().setListener(this)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, fragment).commit()
    }

    override fun onRestartGhostLeg() {
        showInputFragment()
    }
}