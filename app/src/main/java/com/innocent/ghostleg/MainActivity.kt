package com.innocent.ghostleg

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.innocent.ghostleg.databinding.ActivityMainBinding
import com.innocent.ghostleg.drawlots.DrawLotsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * ViewBinding
         * findViewById 대신 레이아웃(xml)을 inflate 후 만들어진 ActivityMainBinding (activity_main 결합클래스) 객체로 접근 가능
         * @link https://developer.android.com/topic/libraries/view-binding?hl=ko
         */
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.hrgwon.setOnClickListener {
            val intent = Intent(this, HrgwonActivity::class.java)
            startActivity(intent)
        }

        binding.shjang.setOnClickListener {
            val intent = Intent(this, GhostLegActivity::class.java)
            startActivity(intent)
        }

        binding.drawLotsButton.setOnClickListener {
            /**
             * 다른 activity 실행
             * @link https://developer.android.com/training/basics/firstapp/starting-activity?hl=ko
             */
            startActivity(Intent(this, DrawLotsActivity::class.java))
        }

        setContentView(binding.root)
    }
}