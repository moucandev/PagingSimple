package com.moucan.common.view

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import kotlin.math.max

class FlowLayout : ViewGroup {

    private var allLines: ArrayList<List<View>> = ArrayList()
    private var allHeight: ArrayList<Int> = ArrayList()

    private val mHorizontalSpacing: Int = dp2px(16) //每个item横向间距
    private val mVerticalSpacing: Int = dp2px(8) //每个item横向间距

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private fun clearMeasureParams() {
        //使用clear不用new是为了防止内存抖动
        allLines.clear()
        allHeight.clear()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        clearMeasureParams()
        val count = childCount
        val paddingLeft = paddingLeft
        val paddingRight = paddingRight
        val paddingTop = paddingTop
        val paddingBottom = paddingBottom
        var parentNeedWidth = 0
        var parentNeedHeight = 0
        var lineViews = arrayListOf<View>()
        val selfWidth = MeasureSpec.getSize(widthMeasureSpec) //ViewGroup解析的父亲给我的宽度
        val selfHeight = MeasureSpec.getSize(heightMeasureSpec) // ViewGroup解析的父亲给我的高度
        var lineWidthUsed = 0
        var lineHeight = 0
        for (i in 0 until count) {
            val childView = getChildAt(i)
            if (childView.visibility != View.GONE) {
                val childLayoutParams = childView.layoutParams
                val childWidthMeasureSpec = getChildMeasureSpec(
                    widthMeasureSpec,
                    paddingLeft + paddingRight,
                    childLayoutParams.width
                )
                val childHeightMeasureSpec = getChildMeasureSpec(
                    heightMeasureSpec,
                    paddingTop + paddingBottom,
                    childLayoutParams.height
                )
                childView.measure(childWidthMeasureSpec, childHeightMeasureSpec)
                //获取子view的度量宽高
                val childMeasuredWidth: Int = childView.measuredWidth
                val childMeasuredHeight: Int = childView.measuredHeight
                //需要换行
                if (lineWidthUsed + childMeasuredWidth + mHorizontalSpacing > selfWidth) {

                    allLines.add(lineViews)
                    allHeight.add(lineHeight)

                    parentNeedHeight = lineHeight + childMeasuredHeight + mVerticalSpacing
                    parentNeedWidth = max(parentNeedWidth, lineWidthUsed + mHorizontalSpacing)

                    lineWidthUsed = 0
                    lineHeight = 0
                    lineViews = arrayListOf()
                }
                lineViews.add(childView)
                lineWidthUsed += childMeasuredWidth + mHorizontalSpacing
                lineHeight = max(lineHeight, childMeasuredHeight)
            }
            if (i == count - 1) {
                allHeight.add(lineHeight)
                allLines.add(lineViews)
                parentNeedHeight += lineHeight + mVerticalSpacing
                parentNeedWidth = max(parentNeedWidth, lineWidthUsed + mHorizontalSpacing)
            }
        }
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val realWidth = if (widthMode == MeasureSpec.EXACTLY) selfWidth else parentNeedWidth
        val realHeight = if (heightMode == MeasureSpec.EXACTLY) selfHeight else parentNeedHeight
        setMeasuredDimension(realWidth, realHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val lines = allLines.size
        var curL = paddingLeft
        var curT = paddingTop
        for (i in 0 until lines) {
            val views = allLines[i]
            val lineHeight = allHeight[i]
            for (j in views.indices) {
                val left = curL
                val right = curL + views[j].measuredWidth
                val top = curT
                val bottom = curT + views[j].measuredHeight
                views[j].layout(left, top, right, bottom)
                curL += views[j].measuredWidth + mHorizontalSpacing
            }
            curT += lineHeight + mVerticalSpacing
            curL = paddingTop
        }
    }

    private fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            Resources.getSystem().displayMetrics
        )
            .toInt()
    }
}