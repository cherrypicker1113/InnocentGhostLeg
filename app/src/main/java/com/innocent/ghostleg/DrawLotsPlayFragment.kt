package com.innocent.ghostleg

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.innocent.ghostleg.databinding.FragmentDrawLotsPlayBinding

class DrawLotsPlayFragment: Fragment() {
    private lateinit var binding: FragmentDrawLotsPlayBinding
    private lateinit var drawLotsList: Array<DrawLots>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrawLotsPlayBinding.inflate(inflater, container, false)
        drawLotsList = makeDrawLots(arguments!!.getInt(TOTAL_COUNT_KEY), arguments!!.getInt(SELECT_COUNT_KEY))
        binding.shakeButton.setOnClickListener { shakeAndRender() }
        shakeAndRender()
        return binding.root
    }

    private fun makeDrawLots(totalCount: Int, selectCount: Int): Array<DrawLots> {
        return Array(totalCount) { i -> DrawLots(i < selectCount, COLOR_SET[i]) }
    }

    private fun shakeAndRender() {
        drawLotsList.shuffle()
        binding.drawLotsList.removeAllViews()
        drawLotsList.forEach{ renderDrawLots(it) }
    }

    private fun renderDrawLots(drawLots: DrawLots) {
        setDrawLotsPosition(drawLots)
        val view = makeDrawLotsView(drawLots)
        binding.drawLotsList.addView(view)
        animateView(view, drawLots)
    }

    private fun setDrawLotsPosition(drawLots: DrawLots) {
        drawLots.translateX = (Math.random() * 40 - 20).toFloat()
        drawLots.rotate = (Math.random() * 40 - 20).toFloat()
    }

    private fun makeDrawLotsView(drawLots: DrawLots): View {
        return Button(activity).apply {
            width = 50
            height = 500
            setBackgroundColor(drawLots.color)
            setOnClickListener {
                val resultFragment = DrawLotsResultFragment()
                val bundle = Bundle()
                bundle.putBoolean(DrawLotsResultFragment.RESULT_KEY, drawLots.selected)
                resultFragment.arguments = bundle
                val transaction = fragmentManager!!.beginTransaction()
                val prevDialog = fragmentManager!!.findFragmentByTag("dialog")
                if (prevDialog != null) {
                    transaction.remove(resultFragment)
                }
                resultFragment.show(transaction, "dialog")
            }
        }
    }

    private fun animateView(view: View, drawLots: DrawLots) {
        val translateX: PropertyValuesHolder = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, drawLots.translateX)
        val rotation: PropertyValuesHolder = PropertyValuesHolder.ofFloat(View.ROTATION, drawLots.rotate)
        ObjectAnimator.ofPropertyValuesHolder(view, translateX, rotation).apply {
            duration = 300
            start()
        }
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
        const val TOTAL_COUNT_KEY: String = "total_count"
        const val SELECT_COUNT_KEY: String = "select_count"
        val COLOR_SET: Array<Int> = arrayOf(Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.RED, Color.WHITE, Color.YELLOW)
    }
}

data class DrawLots(
    val selected: Boolean,
    val color: Int,
    var translateX: Float = 0f,
    var rotate: Float = 0f
)