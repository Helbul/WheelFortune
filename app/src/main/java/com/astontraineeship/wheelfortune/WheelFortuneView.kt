package com.astontraineeship.wheelfortune

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import java.util.Random
import kotlin.properties.Delegates

public class WheelFortuneView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint().apply {
        style = Paint.Style.FILL
    }

    private val segment = 360F/7
    private var lastAngle = -1
    private val random = Random()
    private var centerX by Delegates.notNull<Float>()
    private var centerY by Delegates.notNull<Float>()
    private var radius by Delegates.notNull<Float>()
    private lateinit var listener: AngleListener
    private var scale: Int = 100


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        centerX = width/2f
        centerY = height/2f
        radius = width.coerceAtMost(height) / 2f * scale / 100
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val oval = RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        paint.color = resources.getColor(R.color.red)
        canvas.drawArc(oval, 0F, segment, true, paint)
        paint.color = resources.getColor(R.color.orange)
        canvas.drawArc(oval, segment, segment, true, paint)
        paint.color = resources.getColor(R.color.yellow)
        canvas.drawArc(oval, segment*2, segment, true, paint)
        paint.color = resources.getColor(R.color.green)
        canvas.drawArc(oval, segment*3, segment, true, paint)
        paint.color = resources.getColor(R.color.light_blue)
        canvas.drawArc(oval, segment*4, segment, true, paint)
        paint.color = resources.getColor(R.color.blue)
        canvas.drawArc(oval, segment*5, segment, true, paint)
        paint.color = resources.getColor(R.color.purple)
        canvas.drawArc(oval, segment*6, segment, true, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                val angle = random.nextInt(3600 - 360) + 360

                val animation: Animation = RotateAnimation(
                    (if (lastAngle == -1) 0 else lastAngle).toFloat(),
                    angle.toFloat(),
                    centerX,
                    centerY
                )

                lastAngle = angle;
                animation.duration = 2500
                animation.fillAfter = true

                animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {
                        //Nothing
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        listener.angle(lastAngle)
                    }

                    override fun onAnimationRepeat(p0: Animation?) {
                        //Nothing
                    }

                })

                this.startAnimation(animation)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    fun setChangeAngleListener (angleListener: AngleListener) {
        listener = angleListener
    }

    fun reset() {
        val animation: Animation =
            RotateAnimation((if (lastAngle == -1) 0 else lastAngle).toFloat(), 0f, centerX, centerY)
        lastAngle = -1
        animation.duration = 500
        animation.fillAfter = true
        this.startAnimation(animation)
    }

    fun setScale (newScale: Int) {
        scale = newScale;
        radius = width.coerceAtMost(height) / 2f * scale / 100;
        invalidate()
    }

    fun getScale(): Int {
        return scale
    }
}