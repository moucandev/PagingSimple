package com.moucan.common.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.round


class TimelineView : View {

    private val mPaint = Paint()

    private val totalCell = 60f

    private val cellSize = 1f

    private var cellWidth = 1f

    private var controlX = 0f

    private var controlY = 300f

    private var max = 200 //最大刻度
    private var mCountScale = 0 //滑动的总刻度

    private var screenWidth = 720 //默认屏幕分辨率

    private var mScaleMargin = 15 //刻度间距
    private var mScaleHeight = 20 //刻度线的高度
    private var mScaleMaxHeight = mScaleHeight * 2 //整刻度线高度

    private var mRectWidth = max * mScaleMargin //总宽度
    private var mRectHeight = 150 //高度

    private lateinit var scroller: Scroller

    private var mScrollLastX: Int = 0
    private var mTempScale = screenWidth / mScaleMargin / 2; //判断滑动方向
    private var mScreenMidCountScale = screenWidth / mScaleMargin / 2; //中间刻度

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        init(context, attributes)
    }

    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributes,
        defStyleAttr
    ) {
        init(context, attributes)
    }

    fun init(context: Context, attributes: AttributeSet?) {
        screenWidth = getPhoneWidth(context)
        mTempScale = screenWidth / mScaleMargin / 2 //判断滑动方向
        mScreenMidCountScale = screenWidth / mScaleMargin / 2 //中间刻度
        scroller = Scroller(context)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val lp = ConstraintLayout.LayoutParams(mRectWidth, mRectHeight)
        this.layoutParams = lp
        mPaint.color = Color.parseColor("#FFAFEEEE")
        mPaint.strokeWidth = 2.0f
        mPaint.style = Paint.Style.FILL
        canvas!!.drawRect(0f, 0f, mRectWidth.toFloat(), mRectHeight.toFloat(), mPaint)
//        mPaint.color = Color.parseColor("#FF00BFFF")
//        canvas.drawLine(50f, 10f, width.toFloat() - 50f, 10f, mPaint)
        cellWidth = width / (totalCell / cellSize)
        mPaint.textSize = 30f
//        drawTime(canvas)
        drawScale(canvas)
        drawPointer(canvas)
//        drawController(canvas)
    }

    override fun computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, 0)
            postInvalidate()
        }
        super.computeScroll()
    }

    private fun smoothScrollBy(dx: Int, dy: Int) {
        scroller.startScroll(scroller.finalX, scroller.finalY, dx, dy)
    }

    fun smoothScrollTo(fx: Int, fy: Int) {
        val dx = fx - scroller.finalX
        val dy = fy - scroller.finalY
        smoothScrollBy(dx, dy)
    }

    fun drawTime(canvas: Canvas?) {
        var startX = 50f
        while (startX < width - 50f) {
            for (index in 0..4) {
                val temX = startX + index * cellWidth
                if (temX >= width - 50f) {
                    startX = temX
                    break
                }
                if (index % 5 == 0) {
                    if (((startX - 50f) / cellWidth).toInt() % 2 == 0) {
                        canvas?.drawLine(temX, 10f, temX, 60f, mPaint)
                        val textWidth =
                            mPaint.measureText("${((startX - 50f) / cellWidth).toInt() / 10}")
                        canvas?.drawText(
                            "${((startX - 50f) / cellWidth).toInt() / 10}",
                            temX - textWidth / 2,
                            90f,
                            mPaint
                        )
                    } else {
                        canvas?.drawLine(temX, 10f, temX, 45f, mPaint)
                    }

                } else {
                    canvas?.drawLine(temX, 10f, temX, 30f, mPaint)
                }
            }
            startX += cellWidth * 5
        }
    }

    private fun drawScale(canvas: Canvas?) {
        mPaint.color = Color.GRAY
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.textSize = 20f
        for (i in 0 until max) {
            if (i != 0 && i != max) {
                if (i % 10 == 0) {
                    canvas?.drawLine(
                        i * mScaleMargin.toFloat(),
                        mRectHeight.toFloat(),
                        i * mScaleMargin.toFloat(),
                        mRectHeight - mScaleMaxHeight.toFloat(),
                        mPaint
                    )
                    canvas?.drawText(
                        i.toString(),
                        i * mScaleMargin.toFloat(),
                        mRectHeight - mScaleMaxHeight.toFloat() - 10,
                        mPaint
                    )
                } else {
                    canvas?.drawLine(
                        i * mScaleMargin.toFloat(),
                        mRectHeight.toFloat(),
                        i * mScaleMargin.toFloat(),
                        mRectHeight - mScaleHeight.toFloat(),
                        mPaint
                    )
                }
            }
        }
    }

    private fun drawPointer(canvas: Canvas?) {
        mPaint.color = Color.RED
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.textSize = 20f
        val countScale = screenWidth / mScaleMargin / 2
        val finalX = scroller.finalX
        val tmpCountScale = round(finalX.toDouble() / mScaleMargin.toDouble())
        mCountScale = tmpCountScale.toInt() + countScale
        canvas!!.drawLine(
            countScale * mScaleMargin + finalX.toFloat(),
            mRectHeight.toFloat(),
            countScale * mScaleMargin + finalX.toFloat(),
            mRectHeight - mScaleMaxHeight.toFloat(),
            mPaint
        )
        canvas.drawText(
            mCountScale.toString(),
            countScale * mScaleMargin + finalX.toFloat(),
            mRectHeight - mScaleMaxHeight - 10.toFloat(),
            mPaint
        )
    }

    private fun drawController(canvas: Canvas?) {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 10.0f
        mPaint.color = Color.parseColor("#FFB5B5B5")
        canvas?.drawCircle(width.toFloat() / 2, 500f, 200f, mPaint)
        mPaint.color = Color.parseColor("#FF363636")
        canvas?.drawCircle(width.toFloat() / 2, 500f, 180f, mPaint)
        mPaint.color = Color.parseColor("#FF1C1C1C")
        mPaint.style = Paint.Style.FILL
        canvas?.drawCircle(controlX, controlY, 30f, mPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
//        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, 750)
        } else {
            setMeasuredDimension(widthSize, heightSize)
        }
        controlX = measuredWidth.toFloat() / 2
    }

    private var lastX = 0
    private var lastY = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (scroller.isFinished) {
                    scroller.abortAnimation()
                }
                mScrollLastX = x
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val dataX = mScrollLastX - x
                if (mCountScale - mTempScale < 0) {
                    if (mCountScale < 0) {
                        if (dataX < 0)
                            return super.onTouchEvent(event)
                    }
                } else if (mCountScale - mTempScale > 0) {
                    //可以让直尺一直滑动 但需要注意超出屏幕的部分需要回收
                    if (max - mCountScale < 100) {
                        max += 100
                        mRectWidth = max * mScaleMargin
                    }
                    if (mCountScale > max) {
                        if (dataX > 0)
                            return super.onTouchEvent(event)
                    }
                }
                smoothScrollBy(dataX, 0)
                mScrollLastX = x
                postInvalidate()
                mTempScale = mCountScale
                return true
            }
            MotionEvent.ACTION_UP -> {
                if (mCountScale < 0) mCountScale = 0
                if (mCountScale > max) mCountScale = max
                val finalX = (mCountScale - mScreenMidCountScale) * mScaleMargin
                scroller.finalX = finalX
                postInvalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
//        val x = event!!.rawX.toInt()
//        val y = event.rawY.toInt()
//        Log.d("onTouchEvent", "x: $x")
//        Log.d("onTouchEvent", "y: $y")
//        when(event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                lastX = x
//                lastY = y
//            }
//            MotionEvent.ACTION_MOVE -> {
//                val offsetX = x - lastX
//                val offsetY = y - lastY
//
//                val k = (event.rawY - 500f) / (event.rawX - measuredWidth / 2)
//                Log.d("onTouchEvent", "screenWidth: $measuredWidth")
//                Log.d("onTouchEvent", "k: $k")
//                val y1 = 200f * sin(atan(abs(k.toDouble())))
//                val x1 = abs(y1 / k)
//                controlX = if (event.rawX - measuredWidth / 2 > 0) {
//                    measuredWidth / 2 + x1.toFloat()
//                } else {
//                    measuredWidth / 2 - x1.toFloat()
//                }
//                controlY = if (event.rawY - 500f > 0) {
//                    500f + y1.toFloat()
//                } else {
//                    500f - y1.toFloat()
//                }
//                invalidate()
//
//                scrollBy(-offsetX, -offsetY)
//                layout(left+ offsetX, top + offsetY, right + offsetX, bottom + offsetY)
//                lastX = x
//                lastY = y
//            }

//        }
//        return true
    }

    /**
     * 获取手机分辨率--W
     * */
    fun getPhoneWidth(context: Context): Int {
        val dm = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }

}