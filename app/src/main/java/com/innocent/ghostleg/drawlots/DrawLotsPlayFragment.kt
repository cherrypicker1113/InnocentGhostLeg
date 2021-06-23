package com.innocent.ghostleg.drawlots

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import android.widget.LinearLayout
import com.innocent.ghostleg.databinding.FragmentDrawLotsPlayBinding

class DrawLotsPlayFragment: Fragment(), DrawLotsResultListener {
    private lateinit var binding: FragmentDrawLotsPlayBinding
    private var listener: DrawLotsPlayFragmentListener? = null
    private lateinit var lots: MutableList<Lot>
    private val resultLots: MutableList<Lot> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDrawLotsPlayBinding.inflate(inflater, container, false)

        lots = makeLots(arguments!!.getInt(TOTAL_COUNT_KEY), arguments!!.getStringArray(WIN_NAME_LIST_KEY) as Array<String>)

        binding.shakeCup.setOnClickListener { shakeAndRender() }
        binding.restartButton.setOnClickListener { this.listener?.onRestartDrawLots() }

        renderTopBoard()
        shakeAndRender()

        return binding.root
    }

    private fun renderTopBoard() {
        binding.lotsCount.text = lots.size.toString()
        binding.winCount.text = resultLots.filter { it.win }.size.toString()
    }

    private fun makeLots(totalCount: Int, winnerNameList: Array<String>): MutableList<Lot> {
        /**
         * MutableList
         * @link https://zladnrms.tistory.com/140
         */
        val list = mutableListOf<Lot>()
        for (i in 0 until totalCount) {
            val win = i < winnerNameList.size
            list.add(Lot(win, if (win) winnerNameList[i] else ""))
        }
        list.shuffle()
        list.forEachIndexed { idx, lot -> lot.color = COLOR_SET[idx] }
        return list
    }

    private fun shakeAndRender() {
        lots.shuffle()
        binding.lots.removeAllViews()
        lots.forEach { renderLot(it) }
    }

    private fun renderLot(lot: Lot) {
        val view = makeLotView(lot)
        binding.lots.addView(view)
        animateLotView(view)
    }

    private fun makeLotView(lot: Lot): View {
        val button = Button(activity).apply {
            setBackgroundColor(lot.color)
            setOnClickListener {
                lots.remove(lot)
                resultLots.add(lot)
                hideLotView(it)
                renderTopBoard()
                showResult(if (lot.win) lot.name else "꽝")
            }
        }
        button.layoutParams = LinearLayout.LayoutParams(0, 500, 1f)
        return button
    }

    private fun hideLotView(view: View) {
        ObjectAnimator.ofFloat(view, View.ALPHA, 0f).apply {
            duration = 700
            interpolator = AccelerateInterpolator()
            start()
        }
    }

    private fun animateLotView(view: View) {
        view.translationY = 100f
        val translationXAnimation: PropertyValuesHolder = PropertyValuesHolder.ofFloat(
            View.TRANSLATION_X, (Math.random() * 40 - 20).toFloat())
        val translationYAnimation: PropertyValuesHolder = PropertyValuesHolder.ofFloat(
            View.TRANSLATION_Y, (Math.random() * 50 + 50).toFloat())
        val rotationAnimation: PropertyValuesHolder = PropertyValuesHolder.ofFloat(
            View.ROTATION, (Math.random() * 20 - 10).toFloat())
        ObjectAnimator.ofPropertyValuesHolder(view, translationXAnimation, translationYAnimation, rotationAnimation).apply {
            duration = 300
            start()
        }
    }

    private fun showResult(result: String) {
        val resultFragment = DrawLotsResultFragment().setListener(this)
        val bundle = Bundle()
        bundle.putString(DrawLotsResultFragment.RESULT_KEY, result)
        resultFragment.arguments = bundle
        val transaction = fragmentManager!!.beginTransaction()
        val prevDialog = fragmentManager!!.findFragmentByTag("dialog")
        if (prevDialog != null) {
            transaction.remove(resultFragment)
        }
        resultFragment.show(transaction, "dialog")
    }

    override fun onCloseDrawLotsResult() {
        if (lots.find { it.win } == null) {
//            showResult("끝")
//            resultLots.clear()
            binding.lots.removeAllViews()
            binding.restartButton.visibility = View.VISIBLE
        }
    }

    fun setListener(listener: DrawLotsPlayFragmentListener): DrawLotsPlayFragment {
        this.listener = listener
        return this
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
        const val WIN_NAME_LIST_KEY: String = "win_name_list"
        val COLOR_SET: Array<Int> = arrayOf(
            Color.parseColor("#F1F0E7"),
            Color.parseColor("#B7C9F0"),
            Color.parseColor("#F4DB73"),
            Color.parseColor("#FFD9B5"),
            Color.parseColor("#A6B083"),
            Color.parseColor("#DAC4B7"),
            Color.parseColor("#F3F0FF"),
            Color.parseColor("#17CE5F"),
            Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.RED, Color.WHITE, Color.YELLOW, Color.BLACK
        )
    }
}

interface DrawLotsPlayFragmentListener {
    fun onRestartDrawLots()
}

data class Lot(
    val win: Boolean,
    val name: String,
    var color: Int = Color.BLACK
)