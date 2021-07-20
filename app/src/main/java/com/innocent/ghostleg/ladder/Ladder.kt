package com.innocent.ghostleg.ladder

import android.graphics.Point
import android.util.Log
import java.io.Serializable

class Ladder: Serializable {
    public val MAX_HEIGHT = 20
    public val HEIGHT_RANGE = 7.5f
    public val MARGIN_HEIGHT = (MAX_HEIGHT - HEIGHT_RANGE * 2 - 1).toInt() / 2

    public var pointGrid: ArrayList<ArrayList<Int>> = arrayListOf()

    public fun getPoints(startPosition: Int): ArrayList<Point> {
        val firstPoint = Point(startPosition, pointGrid[startPosition][0])
        val points = arrayListOf<Point>(Point(startPosition, pointGrid[startPosition][0]))

        var currentPoint = firstPoint
        var isFindX = true

        while (true) {
            val xIndex = currentPoint.x
            val yIndex = currentPoint.y
            val yValue = pointGrid[xIndex][yIndex]

            if (yValue === MAX_HEIGHT) break

            if (isFindX) {
                currentPoint = Point(xIndex, yIndex + 1)
            } else {
                val nextXIndex = if (xIndex >= 1 && pointGrid[xIndex - 1].contains(yValue) && (xIndex - 1) % 2 === yValue % 2) xIndex - 1 else xIndex + 1
                val newYIndex = pointGrid[nextXIndex].indexOf(yValue)
                currentPoint = Point(nextXIndex, newYIndex)
            }
            points.add(Point(currentPoint.x, pointGrid[currentPoint.x][currentPoint.y]))
            isFindX = !isFindX
        }

        return points
    }

    public fun getVerticalLines(): ArrayList<Line> {
        val lines = arrayListOf<Line>()
        for (i in 0 until pointGrid.size) {
            lines.add(Line(Point(i, 0), Point(i, MAX_HEIGHT)))
        }
        return lines
    }

    public fun getHorizontalLines(): ArrayList<Line> {
        val lines = arrayListOf<Line>()

        for (i in 0 until pointGrid.size - 1) {
            val verticalLine = pointGrid[i]

            for (j in 1 until verticalLine.size - 1) {
                val y = verticalLine[j]
                //나머지로 비교
                if (y % 2 !== i % 2) continue

                lines.add(
                    Line(
                        Point(i, y),
                        Point(i + 1, y)
                    )
                )
            }
        }

        return lines
    }

    public fun initializeLadder(count: Int) {
        pointGrid.clear()
        initializeVerticalLines(count)
        initializeHorizontalLines(count)
    }

    fun initializeVerticalLines(count: Int) {
        for (i in 0 until count) {
            val newLine = arrayListOf(0, MAX_HEIGHT)
            pointGrid.add(newLine)
        }
    }

    fun initializeHorizontalLines(count: Int) {
        var ladderCount = getLadderCount(count)

        // 일단 1개씩은 다 넣어줌
        for (i in 0 until count - 1) {
            val rest = i % 2
            val y = (Math.random() * HEIGHT_RANGE).toInt() * 2 + MARGIN_HEIGHT + rest
            pointGrid[i].add(y)
            pointGrid[i + 1].add(y)
            ladderCount--

        }

        while (ladderCount > 0) {
            val idx = (Math.random() * (count - 1)).toInt()

            if (idx === count - 1) continue

            val rest = idx % 2
            val y = (Math.random() * HEIGHT_RANGE).toInt() * 2 + MARGIN_HEIGHT + rest
            if (pointGrid[idx].contains(y)) continue

            pointGrid[idx].add(y)
            pointGrid[idx + 1].add(y)

            ladderCount--
        }

        for (i in 0 until count) {
            pointGrid[i].sort()
        }
    }

    fun getLadderCount(count: Int): Int {
        return Math.max(count * 2 - 3, 6)
    }
}