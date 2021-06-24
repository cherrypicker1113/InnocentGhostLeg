package com.innocent.ghostleg

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.innocent.ghostleg.databinding.ActivityShjangBinding
import com.innocent.ghostleg.ladder.LadderDrawingActivity

class ShjangActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShjangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShjangBinding.inflate(layoutInflater)

        binding.btnGoToHome.setOnClickListener {
            finish()
        }

        binding.btnDoGhostLeg.setOnClickListener {
            val intent = Intent(this, LadderDrawingActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }
}