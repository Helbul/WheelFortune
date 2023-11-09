package com.astontraineeship.wheelfortune

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates

class CustomTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paintText = Paint().apply {
        textAlign = Paint.Align.CENTER
        color = Color.BLACK
        textSize = 80f
    }

    private var text = "Hello world"
    private var startX by Delegates.notNull<Float>()
    private var startY by Delegates.notNull<Float>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        startX = width/2f
        startY = height/2f
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(text, startX, startY, paintText)
    }

    fun setText (newText: String, newColor: Int) {
        paintText.color = newColor
        text = newText
        invalidate()
    }
}