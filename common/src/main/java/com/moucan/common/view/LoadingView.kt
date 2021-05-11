package com.moucan.common.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class LoadingView : View {

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributes: AttributeSet) : super(
        context,
        attributes
    ) {
        init()
    }

    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributes,
        defStyleAttr
    ) {
        init()
    }


    val paint = Paint()

    fun init() {
        paint.color = Color.YELLOW
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300,300)
        } else if(widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300,heightSize)
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize,300)
        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom
        val width = width - paddingLeft - paddingRight
        val height = height - paddingTop - paddingBottom
        canvas!!.translate(50f, 50f)
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), 150f, paint )
    }
}