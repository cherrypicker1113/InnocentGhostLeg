package com.innocent.ghostleg

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.innocent.ghostleg.databinding.ActivityGhostLegResultBinding

class GhostLegResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGhostLegResultBinding
    private lateinit var adapter: GhostLegAdapter
    private var results: Array<GhostLegResult> = arrayOf(
        GhostLegResult(0, "소영"),
        GhostLegResult(1, "수홍"),
        GhostLegResult(2, "아영"),
        GhostLegResult(3, "정현"),
        GhostLegResult(4, "지나"),
        GhostLegResult(5, "주현"),
        GhostLegResult(6, "아인"),
        GhostLegResult(7, "민경"),
        GhostLegResult(8, "지혜"),
        GhostLegResult(9, "소라"),
        GhostLegResult(10, "예림"),
        GhostLegResult(11, "빛나")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGhostLegResultBinding.inflate(layoutInflater)

        binding.btnGoToHome.setOnClickListener {
            finish()
        }

        adapter = GhostLegAdapter(this, arrayListOf());
        binding.gridResultGhostLeg.adapter = adapter

        val num = 12;
        for(i in 0 until num) {
            adapter.addItem(GhostLegResult(1, "슈홍"))
        }

        binding.btnRefresh.setOnClickListener {
            refresh()
        }

        setContentView(binding.root)
    }

    fun refresh() {
        for (i in 0 until adapter.getCount()) {
            adapter.removeItem()
        }

        results.shuffle();

        for (i in 0 until results.size) {
            adapter.addItem(results[i])
        }
    }
}