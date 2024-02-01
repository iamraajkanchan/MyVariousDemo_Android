package com.example.filesystemdemo.views.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/***
 * You can't use DI on Custom Views
 * */
class DynamicGraphView(
    context: Context,
    attributeSet: AttributeSet
) :
    View(context, attributeSet) {

    private val dataPoints = mutableListOf<Float>()
    private var maxValue = 100f
    private val paint = Paint()

    init {
        paint.color = Color.BLUE
        paint.strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Calculate graph dimensions and spacing
        val width = width - paddingLeft - paddingRight
        val height = height - paddingTop - paddingBottom
        val spacing = width / dataPoints.size

        // Draw graph lines
        for (i in 0 until dataPoints.size - 1) {
            val x1 = paddingLeft + i * spacing
            val y1 = height - (dataPoints[i] / maxValue * height).toInt() - paddingTop
            val x2 = paddingLeft + (i + 1) * spacing
            val y2 = height - (dataPoints[i + 1] / maxValue * height).toInt() - paddingTop
            canvas?.drawLine(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), paint)
        }
    }

    fun addDataPoint(value: Float) {
        dataPoints.add(value)
        invalidate()  // Trigger redraw
        maxValue = maxOf(maxValue, value)  // Adjust maximum value if needed
    }

    fun updateData(newData: List<Float>) {
        dataPoints.clear()
        dataPoints.addAll(newData)
        invalidate()
    }


}