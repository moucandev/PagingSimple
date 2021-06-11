package com.moucan.pagingsimple.nested

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class NestedLogUnTouchRecyclerView : RecyclerView{
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        return false
    }
}