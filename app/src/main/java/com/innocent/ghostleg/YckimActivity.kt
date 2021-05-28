package com.innocent.ghostleg

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.innocent.ghostleg.databinding.ActivityYckimBinding

class YckimActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYckimBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYckimBinding.inflate(layoutInflater)

        val name = intent.getStringExtra(NAME_KEY)
        if (name?.isNotBlank() == true) {
            binding.hello.text = "Hello " + intent.getStringExtra(NAME_KEY) + "!"
        }

        binding.submitButton.setOnClickListener {
            setResult(RESULT_OK, Intent().putExtra(RESULT_KEY, binding.input.text.toString()))
            finish()
        }

        setContentView(binding.root)
    }

    /**
     * 코틀린에는 static이 없다.
     * 대체 방법 중 하나인 companion object 방식.
     * @link https://velog.io/@cchloe2311/Kotlin-Kotlin%EC%97%94-static-%ED%82%A4%EC%9B%8C%EB%93%9C%EA%B0%80-%EC%97%86%EB%8B%A4
     */
    companion object {
        /**
         * 'var' vs 'val' vs 'const val'
         * @link https://kumgo1d.tistory.com/60
         */
        const val NAME_KEY: String = "name"
        const val RESULT_KEY: String = "result"
    }
}