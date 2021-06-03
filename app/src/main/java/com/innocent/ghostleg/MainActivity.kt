package com.innocent.ghostleg

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.innocent.ghostleg.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val yckimActivityRequestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * ViewBinding
         * findViewById 대신 레이아웃(xml)을 inflate 후 만들어진 ActivityMainBinding (activity_main 결합클래스) 객체로 접근 가능
         * @link https://developer.android.com/topic/libraries/view-binding?hl=ko
         */
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.toggleButton.setOnClickListener {
            val view: Button = it as Button
            view.text = view.text.reversed()
            binding.toggleText.text = binding.toggleText.text.reversed()
        }

        binding.hrgwon.setOnClickListener {
            val intent = Intent(this, HrgwonActivity::class.java)
            startActivity(intent)
        }

        binding.shjang.setOnClickListener {

        }

        binding.yckim.setOnClickListener {
            /**
             * 다른 activity 실행
             * @link https://developer.android.com/training/basics/firstapp/starting-activity?hl=ko
             */
            val intent = Intent(this, YckimActivity::class.java).apply {
                putExtra(YckimActivity.NAME_KEY, "yckim")
            }
            startActivityForResult(intent, yckimActivityRequestId)
        }

        setContentView(binding.root)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK)
            return
        /**
         * switch case
         * @link https://kotlinlang.org/docs/control-flow.html#when-expression
         */
        when (requestCode) {
            yckimActivityRequestId -> {
                val receiveMsg = data?.getStringExtra(YckimActivity.RESULT_KEY).toString()
                if (receiveMsg.isNotBlank()) {
                    Toast.makeText(this, "버튼 이름 변경됨.\n" + binding.yckim.text + " -> " + receiveMsg, Toast.LENGTH_SHORT).show()
                    binding.yckim.text = receiveMsg
                }
            }
        }
    }
}