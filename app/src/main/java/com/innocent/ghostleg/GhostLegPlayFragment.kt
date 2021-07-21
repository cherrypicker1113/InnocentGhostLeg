package com.innocent.ghostleg

import android.graphics.*
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.innocent.ghostleg.databinding.FragmentGhostLegPlayBinding
import com.innocent.ghostleg.drawlots.DrawLotsPlayFragment
import com.innocent.ghostleg.ladder.Ladder
import com.innocent.ghostleg.ladder.Line

class GhostLegPlayFragment: Fragment(), GhostLegResultListener {
    private lateinit var binding: FragmentGhostLegPlayBinding
    private var listener: GhostLegPlayFragmentListener? = null

    private val CANVAS_SIZE: Float = 1000f
    private val BITMAP_MARGIN: Float = 120f
    private val BITMAP_SIZE: Int = (CANVAS_SIZE + BITMAP_MARGIN * 2).toInt()

    private val STROKE_WIDTH: Float = 20f
    private val COLOR_PRESET: Array<Int> = arrayOf(Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.WHITE, Color.YELLOW, Color.DKGRAY)

    private val ladder: Ladder = Ladder()
    private var bitmap = Bitmap.createBitmap(BITMAP_SIZE, BITMAP_SIZE, Bitmap.Config.ARGB_8888)
    private var canvas = Canvas(bitmap)
    private var count = 0

    fun drawLadder(players: Array<String>, results: Array<String>) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = 50f;
        paint.setColor(Color.BLACK)
        paint.strokeWidth = STROKE_WIDTH

        drawLines(ladder.getVerticalLines(), paint)
        drawLines(ladder.getHorizontalLines(), paint)

        players.forEachIndexed { index, s -> canvas.drawText(s, index * getWidthRatio(count) - 50f, -10f, paint) }
        results.forEachIndexed { index, s -> canvas.drawText(s, index * getWidthRatio(count) - 50f, 1050f, paint) }
    }

    fun drawLines(lines: ArrayList<Line>, paint: Paint) {
        lines.forEach {
            canvas.drawLine(
                it.pt1.x.toFloat() * getWidthRatio(count),
                it.pt1.y.toFloat() * getHeightRatio(),
                it.pt2.x.toFloat() * getWidthRatio(count),
                it.pt2.y.toFloat() * getHeightRatio()
                , paint)
        }
    }

    fun drawPoints() {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val alpha = 150
        paint.strokeWidth = STROKE_WIDTH

        fun a(num: Int) {
            paint.setColor(COLOR_PRESET[num % 7])
            paint.alpha = alpha
            drawPP(ladder.getPoints(num), paint)
        }
        for (i in 0 until count) {
            a(i)
        }
    }

    fun drawPP(points: ArrayList<Point>, paint: Paint) {
        val floatPoints = points.map{PointF(it.x.toFloat() * getWidthRatio(count), it.y.toFloat() * getHeightRatio())}
        val floatArray = FloatArray((points.size - 1) * 4)

        for (i in 0 until points.size - 1) {
            floatArray[i * 4] = floatPoints[i].x
            floatArray[i * 4 + 1] = floatPoints[i].y
            floatArray[i * 4 + 2] = floatPoints[i + 1].x
            floatArray[i * 4 + 3] = floatPoints[i + 1].y
        }

        canvas.drawLines(floatArray, paint)
    }

    fun getWidthRatio(count: Int): Float {
        return CANVAS_SIZE / (count - 1)
    }

    fun getHeightRatio(): Float {
        return CANVAS_SIZE / 20
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGhostLegPlayBinding.inflate(inflater, container, false)

        count = arguments!!.getInt(GhostLegPlayFragment.TOTAL_COUNT_KEY)
        val players = arguments!!.getStringArray(GhostLegPlayFragment.PLAYER_LIST_KEY) as Array<String>
        val results = arguments!!.getStringArray(GhostLegPlayFragment.RESULT_LIST_KEY) as Array<String>
        ladder.initializeLadder(count)
        canvas.translate(BITMAP_MARGIN, BITMAP_MARGIN)
        binding.canvas.setImageBitmap(bitmap)
        drawLadder(players, results)

        binding.buttonDraw.setOnClickListener {
            drawPoints();
            binding.buttonDraw.text = "다시하기"
            binding.buttonDraw.setOnClickListener {  }
        }

        return binding.root
    }

//    private fun showResult(result: String) {
//        val resultFragment = GhostLegResultFragment().setListener(this)
//        val bundle = Bundle()
//        bundle.putString(GhostLegResultFragment.RESULT_KEY, result)
//        resultFragment.arguments = bundle
//        val transaction = fragmentManager!!.beginTransaction()
//        val prevDialog = fragmentManager!!.findFragmentByTag("dialog")
//        if (prevDialog != null) {
//            transaction.remove(resultFragment)
//        }
//        resultFragment.show(transaction, "dialog")
//    }

    override fun onCloseGhostLegResult() {
        binding.restartButton.visibility = View.VISIBLE

    }

    fun setListener(listener: GhostLegPlayFragmentListener): GhostLegPlayFragment {
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
        const val PLAYER_LIST_KEY: String = "player_list"
        const val RESULT_LIST_KEY: String = "result_list"
    }
}

interface GhostLegPlayFragmentListener {
    fun onRestartGhostLeg()
}