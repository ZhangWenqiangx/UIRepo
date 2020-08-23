package com.example.ui.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.ui.R

/**
 * @describe :
 *
 * @author zwq 2020/8/23
 */
class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var mPaint: Paint? = null
    var mRectf: RectF? = null
    var valueAnimator: ValueAnimator? = null

    var borderWidth = 0f
    val maxRotate = 720f
    var duration = 0
    var maxAngle = 0
    var borderColor = Color.BLACK

    //偏移角度
    var startAngle: Float = 0f

    //转动时角度
    var sweepAngle: Float = 0f

    init {
        mRectf = RectF()
        initProperties(context, attrs)
        createPaint()
    }

    private fun initProperties(context: Context, attrs: AttributeSet?) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.LoadingView)
        borderWidth = typedArray.getDimensionPixelOffset(
            R.styleable.LoadingView_borderWidth,
            context.resources.getDimensionPixelOffset(R.dimen.default_loading_border_width)
        ).toFloat()
        borderColor = typedArray.getColor(
            R.styleable.LoadingView_borderColor,
            Color.GRAY
        )
        maxAngle = typedArray.getInt(R.styleable.LoadingView_maxAngle, 100)
        duration = typedArray.getInt(R.styleable.LoadingView_duration, 2000)
        typedArray.recycle()
    }

    private fun createPaint() {
        mPaint = Paint()
        mPaint!!.apply {
            isAntiAlias = true
            isDither = true
            color = borderColor
            style = Paint.Style.STROKE
            strokeWidth = borderWidth
            strokeCap = Paint.Cap.ROUND
        }
    }

    private fun initValueAnimator() {
        if (valueAnimator != null) return

        valueAnimator = ValueAnimator.ofFloat(0f, maxRotate)
        valueAnimator!!.apply {
            duration = this@LoadingView.duration.toLong()
            addUpdateListener {
                val value = it.animatedValue as Float
                startAngle = -90 + value
                // 计算进度条的宽度变化
                sweepAngle = if (value <= maxRotate / 2) {
                    (maxAngle * (valueAnimator!!.animatedFraction * 2))
                } else {
                    (maxAngle * (2 - valueAnimator!!.animatedFraction * 2))
                }
                invalidate()
            }
            repeatCount = ValueAnimator.INFINITE
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = measuredHeight

        val radius = if (width > height) height / 2 else width / 2

        mRectf!!.set(
            width / 2 - radius + borderWidth / 2.toFloat(),
            height / 2 - radius + borderWidth / 2.toFloat(),
            width / 2 + radius - borderWidth / 2.toFloat(),
            height / 2 + radius - borderWidth / 2.toFloat()
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawArc(mRectf!!, startAngle, sweepAngle, false, mPaint!!)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        initValueAnimator()
        if (!valueAnimator!!.isStarted) {
            valueAnimator!!.start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (valueAnimator!!.isStarted) {
            valueAnimator!!.cancel()
            valueAnimator = null
        }
    }
}