package com.innocent.ghostleg
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.innocent.ghostleg.databinding.ActivityHrgwonBinding

class HrgwonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHrgwonBinding
    private val maxNumber: Int = 20
    private var num: Int = 5
    private val textList: ArrayList<TextView> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHrgwonBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener {
            finish()
        }

        num = Integer.parseInt(binding.tvNum.text.toString())
        for(i in 0 until num) {
            addTextView()
        }

        // 푸시가 잘 되는지 확인하기 위한 주석
        binding.btnDecrease.setOnClickListener {
            println("num은 $num , textList size는 $textList.size")
            if (checkValid(num - 1)) {
                updateNumber(num - 1)
                binding.textListLinearLayout.removeView(textList.last());
                textList.remove(textList.last())
            }
        }

        binding.btnIncrease.setOnClickListener {
            println("num은 $num , textList size는 $textList.size")
            if (checkValid(num + 1)) {
                updateNumber(num + 1)
                addTextView()
            }
        }

        setContentView(binding.root)
    }

    private fun addTextView() {
        val textView = TextView(this)
        textList.add(textView);
        textView.text = "${textList.lastIndex + 1}. TEXT_VIEW"
        binding.textListLinearLayout.addView(textView)

    }

    private fun updateNumber(value: Int) {
        num = value
        binding.tvNum.text = num.toString()
    }

    private fun checkValid(value: Int): Boolean {
        return value in 1 until maxNumber + 1;
    }
}