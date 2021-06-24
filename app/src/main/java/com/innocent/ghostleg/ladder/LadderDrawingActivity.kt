package com.innocent.ghostleg.ladder

import android.graphics.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.innocent.ghostleg.databinding.ActivityLadderDrawingBinding


class LadderDrawingActivity : AppCompatActivity() {
    private val CANVAS_SIZE: Float = 1000f
    private val BITMAP_MARGIN: Float = 50f
    private val BITMAP_SIZE: Int = (CANVAS_SIZE + BITMAP_MARGIN * 2).toInt()

    private val STROKE_WIDTH: Float = 20f
    private val COLOR_PRESET: Array<Int> = arrayOf(Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.WHITE, Color.YELLOW)

    private lateinit var binding: ActivityLadderDrawingBinding
    private val ladder: Ladder = Ladder()
    private var bitmap = Bitmap.createBitmap(BITMAP_SIZE, BITMAP_SIZE, Bitmap.Config.ARGB_8888)
    private var canvas = Canvas(bitmap)
    private var count = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ladder.initializeLadder(count)
        binding = ActivityLadderDrawingBinding.inflate(layoutInflater)
        canvas.translate(BITMAP_MARGIN, BITMAP_MARGIN)
        binding.canvas.setImageBitmap(bitmap)

//        val intent = getIntent()
//        val obj = intent.getSerializableExtra("mm")
        drawLadder()
        binding.btnRefresh.setOnClickListener {
            onClickLadder()
        }
        setContentView(binding.root)
    }

    fun onClickLadder() {
        drawPoints()
        binding.btnRefresh.setOnClickListener {
            onClickRefresh()
        }
    }

    fun onClickRefresh() {
        ladder.initializeLadder(++count)

        bitmap = Bitmap.createBitmap(BITMAP_SIZE, BITMAP_SIZE, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
        canvas.translate(BITMAP_MARGIN, BITMAP_MARGIN)
        binding.canvas.setImageBitmap(bitmap)

        drawLadder()
        binding.btnRefresh.setOnClickListener {
            onClickLadder()
        }
    }

    fun drawLadder() {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.setColor(Color.BLACK)
        paint.strokeWidth = STROKE_WIDTH
        drawLines(ladder.getVerticalLines(), paint)
        drawLines(ladder.getHorizontalLines(), paint)
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
}