package com.innocent.ghostleg
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.innocent.ghostleg.databinding.ActivityHrgwonBinding

class HrgwonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHrgwonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHrgwonBinding.inflate(layoutInflater)

        binding.btn.setOnClickListener {
            finish()
        }

        setContentView(binding.root)
    }
}