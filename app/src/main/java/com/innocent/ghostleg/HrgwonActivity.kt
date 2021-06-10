package com.innocent.ghostleg
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.innocent.ghostleg.databinding.ActivityHrgwonBinding

class HrgwonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHrgwonBinding
    private val maxNumber: Int = 20
    private var num: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHrgwonBinding.inflate(layoutInflater)

        binding.btnPlay.setOnClickListener {
            finish()
        }

        binding.teamCounter.tvTitle.text = "팀 수"
        binding.teamCounter.tvUnit.text = "팀"
        binding.teamCounter.tvNum.text = "2"

        val adapter = HrgwonAdapter(this, arrayListOf());
        binding.gridView.adapter = adapter

        num = Integer.parseInt(binding.memberCounter.tvNum.text.toString())
        for(i in 0 until num) {
            adapter.addItem(Data(adapter.getCount() + 1, ""))
        }

        // 푸시가 잘 되는지 확인하기 위한 주석
        binding.memberCounter.btnDecrease.setOnClickListener {
            if (checkValid(num - 1)) {
                updateNumber(num - 1)
                adapter.removeItem()
            }
        }

        binding.memberCounter.btnIncrease.setOnClickListener {
            if (checkValid(num + 1)) {
                updateNumber(num + 1)
                adapter.addItem(Data(adapter.getCount() + 1, ""))
            }
        }

        setContentView(binding.root)
    }

    private fun updateNumber(value: Int) {
        num = value
        binding.memberCounter.tvNum.text = num.toString()
    }

    private fun checkValid(value: Int): Boolean {
        return value in 1 until maxNumber + 1;
    }
}