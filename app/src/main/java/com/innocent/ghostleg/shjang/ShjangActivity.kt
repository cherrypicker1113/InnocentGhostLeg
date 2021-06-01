package com.innocent.ghostleg

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.innocent.ghostleg.databinding.ActivityShjangBinding
import com.innocent.ghostleg.shjang.TeamSpliteController

class ShjangActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShjangBinding
    private var countOfPeople = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShjangBinding.inflate(layoutInflater)

        binding.btnGoToHome.setOnClickListener {
            finish()
        }

        binding.btnMinusCountOfPeople.setOnClickListener {
            if (countOfPeople === 0) return@setOnClickListener
            this.countOfPeople -= 1
            binding.textViewCountOfPeople.text = countOfPeople.toString()
        }

        binding.btnPlusCountOfPeople.setOnClickListener {
            countOfPeople += 1
            binding.textViewCountOfPeople.text = countOfPeople.toString()
        }

        binding.btnSplit.setOnClickListener {
            val members = Array<String>(countOfPeople, {i -> ""})
            for (i in 1..countOfPeople) {
                members[i - 1] = ("장수홍" + i.toString())
            }
            val team = TeamSpliteController.splitTeam(members)
            binding.textViewTeam1.text = team[0].joinToString("\n")
            binding.textViewTeam2.text = team[1].joinToString("\n")
        }

        setContentView(binding.root)
    }
}