package com.example.ui.widget

import android.animation.*
import android.annotation.SuppressLint
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
 * @author zwq 2020/8/24
 */
class RoundClickView @JvmOverloads constructor(private val mContext: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0)
: View(mContext, attrs, defStyleAttr) {

    init {
        init(attrs)
        initPaint()
        setClickEvent()
    }

    private fun setClickEvent() {
        setOnClickListener { v: View? ->
            mIsChecked = !mIsChecked
            reset()
        }
    }

    private var ringProgress = 0
    private var circleProgress = 0
    private var rightAlpha = 0

    private fun reset() {
        initPaint()
        ringProgress = 0
        circleProgress = 0
        rightAlpha = 0
        IsAnimeRuning = false
        ringAnimeEnd = false
        mRectF[centerX - radius, centerY - radius, centerX + radius] = centerY + radius
        invalidate()
    }

    lateinit var mPaintRing: Paint   //圆环画笔
    lateinit var mPaintTick: Paint   //对勾画笔
    lateinit var mPaintCircle: Paint

    private var centerX = 0f
    private var centerY = 0f
    private var tickRadius = 0f
    private var tickRadiusOffset = 0f

    private var unCheckBaseColor = 0
    private var checkBaseColor = 0
    private var checkTickColor = 0

    private val mPoints = FloatArray(8)

    val mRectF: RectF = RectF()
    var radius: Int = 0

    private var mIsChecked = false
    private var IsAnimeRuning = false
    private var ringAnimeEnd = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 计算半径
        radius = dp2px(mContext, 30f)
        val mWidth = getSize(radius * 2, widthMeasureSpec)
        val mHeight = getSize(radius * 2, heightMeasureSpec)
        val max = mWidth.coerceAtLeast(mHeight)
        setMeasuredDimension(max, max)

        centerX = measuredWidth / 2.toFloat()
        centerY = measuredHeight / 2.toFloat()
        mRectF[centerX - radius, centerY - radius, centerX + radius] = centerY + radius

        //设置打钩的几个点坐标
        mPoints[0] = centerX - tickRadius + tickRadiusOffset
        mPoints[1] = centerY
        mPoints[2] = centerX - tickRadius / 2 + tickRadiusOffset
        mPoints[3] = centerY + tickRadius / 2
        mPoints[4] = centerX - tickRadius / 2 + tickRadiusOffset
        mPoints[5] = centerY + tickRadius / 2
        mPoints[6] = centerX + tickRadius * 2 / 4 + tickRadiusOffset
        mPoints[7] = centerY - tickRadius * 2 / 4
    }

    @SuppressLint("ObjectAnimatorBinding")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (!mIsChecked) {
            canvas!!.drawArc(mRectF, 90f, 360f, false, mPaintRing)
            canvas.drawLines(mPoints, mPaintTick)
            return
        }
        if (ringAnimeEnd) {
            mPaintCircle.color = checkBaseColor
            //画满黄色的圆
            canvas!!.drawCircle(centerX, centerY, radius.toFloat(), mPaintCircle)
            mPaintCircle.color = checkTickColor
        }
        //画圆环
        canvas!!.drawArc(mRectF, 90f, ringProgress.toFloat(), false, mPaintRing)
        //画逐渐缩小的白圆
        canvas.drawCircle(centerX, centerY, radius - circleProgress.toFloat(), mPaintCircle)
        //画对号
        mPaintTick.alpha = rightAlpha
        canvas.drawLines(mPoints, mPaintTick)
        if (!IsAnimeRuning) {
            IsAnimeRuning = true
            val ringAnimator = ObjectAnimator.ofInt(this, "ringProgress", 0, 360)
            val circleAnimator = ObjectAnimator.ofInt(this, "circleProgress", 0, radius)
            val rightAnimator = ObjectAnimator.ofInt(this, "rightAlpha", 0, 255)
            ringAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    ringAnimeEnd = true
                }
            })
            val holder1 = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.2f, 1f)
            val holder2 = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.2f, 1f)
            val scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(this, holder1, holder2)
            val animatorSet = AnimatorSet()
            animatorSet.playSequentially(ringAnimator, circleAnimator, rightAnimator, scaleAnimator)
            animatorSet.start()
        }
    }
    //用于属性动画的getter和setter
    fun getRingProgress(): Int {
        return ringProgress
    }

    fun setRingProgress(ringProgress: Int) {
        this.ringProgress = ringProgress
        invalidate()
    }

    fun getCircleProgress(): Int {
        return circleProgress
    }

    fun setCircleProgress(circleProgress: Int) {
        this.circleProgress = circleProgress
        invalidate()
    }

    fun getRightAlpha(): Int {
        return rightAlpha
    }

    fun setRightAlpha(rightAlpha: Int) {
        this.rightAlpha = rightAlpha
        invalidate()
    }
    /**
     * @param def 圆直径 + 扩大动画值
     */
    private fun getSize(def: Int, measureSpec: Int): Int {
        val result: Int
        val mode = View.MeasureSpec.getMode(measureSpec)
        val size = View.MeasureSpec.getSize(measureSpec)
        //如果是 不确定 或者 wrap_content 就是用默认的值否则 就是测量出来的值
        result = if (mode == View.MeasureSpec.UNSPECIFIED || mode == View.MeasureSpec.AT_MOST) {
            def
        } else {
            size
        }
        return result
    }

    private fun initPaint() {
        mPaintRing = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaintRing.style = Paint.Style.STROKE
        mPaintRing.color = if (mIsChecked) checkBaseColor else unCheckBaseColor
        mPaintRing.strokeWidth = dp2px(mContext, 2.5f).toFloat()
        mPaintRing.strokeCap = Paint.Cap.ROUND

        mPaintCircle = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaintCircle.color = checkTickColor
        mPaintCircle.strokeWidth = dp2px(mContext, 1f).toFloat()

        mPaintTick = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaintTick.color = if (mIsChecked) checkTickColor else unCheckBaseColor
        mPaintTick.style = Paint.Style.STROKE
        mPaintTick.strokeCap = Paint.Cap.ROUND
        mPaintTick.strokeWidth = dp2px(mContext, 2.5f).toFloat()
    }

    private fun init(attrs: AttributeSet?) {
        val a = mContext.obtainStyledAttributes(attrs, R.styleable.RoundClickView)
        unCheckBaseColor = a.getColor(R.styleable.RoundClickView_uncheck_color, Color.GRAY)
        checkBaseColor = a.getColor(R.styleable.RoundClickView_check_color, Color.YELLOW)
        checkTickColor = a.getColor(R.styleable.RoundClickView_check_right_color, Color.WHITE)
        a.recycle()
        radius = dp2px(mContext, 30f)
        tickRadius = dp2px(mContext, 12f).toFloat()
        tickRadiusOffset = dp2px(mContext, 4f).toFloat()
    }

    private fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}