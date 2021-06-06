package com.innocent.ghostleg

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innocent.ghostleg.databinding.FragmentDrawLotsPlayBinding

class DrawLotsPlayFragment: Fragment() {
    private lateinit var binding: FragmentDrawLotsPlayBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrawLotsPlayBinding.inflate(inflater, container, false)
        binding.play.text = arguments!!.getInt(LOTS_COUNT_KEY).toString()
        binding.test.setOnClickListener {
            val resultFragment = DrawLotsResultFragment()
            val bundle = Bundle()
            bundle.putBoolean(DrawLotsResultFragment.RESULT_KEY, Math.random() > 0.5)
            resultFragment.arguments = bundle
            val transaction = fragmentManager!!.beginTransaction()
            val prevDialog = fragmentManager!!.findFragmentByTag("dialog")
            if (prevDialog != null) {
                transaction.remove(resultFragment)
            }
            resultFragment.show(transaction, "dialog")
        }
        return binding.root
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
        const val LOTS_COUNT_KEY: String = "lots_count"
    }
}