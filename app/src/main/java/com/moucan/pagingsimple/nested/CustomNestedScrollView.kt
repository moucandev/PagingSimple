package com.moucan.pagingsimple.nested

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView

class CustomNestedScrollView : NestedScrollView {
    companion object {
        const val TAG = "CustomNestedScrollView"
    }

    private lateinit var topView: View
    private lateinit var contentView: View

    constructor(context: Context) : this(context, null) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    ) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    private lateinit var mFlingHelper: FlingHelper

    private var totalDy = 0

    /**
     * 用于判断RecyclerView是否在fling
     */
    var isStartFling = false

    /**
     * 记录当前滑动的y轴加速度
     */
    private var velocityY = 0

    private fun init() {
        mFlingHelper = FlingHelper(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (isStartFling) {
                    totalDy = 0
                    isStartFling = false
                }
                if (scrollY == 0) {
                    Log.i(TAG, "TOP SCROLL")
                }
                if (scrollY == getChildAt(0).measuredHeight - v.measuredHeight) {
                    Log.i(TAG, "BOTTOM SCROLL")
                    dispatchChildFling()
                }
                //在RecyclerView fling情况下，记录当前RecyclerView在y轴的偏移
                totalDy += scrollY - oldScrollY
            }
        }
    }

    private fun dispatchChildFling() {
        if (velocityY != 0) {
            val splineFlingDistance: Double = mFlingHelper.getSplineFlingDistance(velocityY)
            if (splineFlingDistance > totalDy) {
                childFling(
                    mFlingHelper.getVelocityByDistance(
                        splineFlingDistance - java.lang.Double.valueOf(
                            totalDy.toDouble()
                        )
                    )
                )
            }
        }
        totalDy = 0
        velocityY = 0
    }

    private fun childFling(velY: Int) {
        val childRecyclerView: RecyclerView = getChildRecyclerView(contentView as ViewGroup)!!
        childRecyclerView.fling(0, velY)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        topView = (getChildAt(0) as ViewGroup).getChildAt(0)
        contentView = (getChildAt(0) as ViewGroup).getChildAt(1) as ViewGroup

    }

    override fun fling(velocityY: Int) {
        super.fling(velocityY)
        if (velocityY <= 0) {
            this.velocityY = 0
        } else {
            isStartFling = true
            this.velocityY = velocityY
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val lp = contentView.layoutParams
        lp.height = measuredHeight
        contentView.layoutParams = lp
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        Log.i(
            "NestedScrollLayout",
            scrollY.toString() + "::onNestedPreScroll::" + topView.measuredHeight
        )
        // 向上滑动。若当前topview可见，需要将topview滑动至不可见
        val hideTop = dy > 0 && scrollY < topView.measuredHeight
        if (hideTop) {
            scrollBy(0, dy)
            consumed[1] = dy
        }
    }

    private fun getChildRecyclerView(viewGroup: ViewGroup): RecyclerView? {
        for (i in 0 until viewGroup.childCount) {
            val view = viewGroup.getChildAt(i)
            if (view is RecyclerView && view.javaClass == RecyclerView::class.java) {
                return viewGroup.getChildAt(i) as RecyclerView
            } else if (viewGroup.getChildAt(i) is ViewGroup) {
                val childRecyclerView: ViewGroup? =
                    getChildRecyclerView(viewGroup.getChildAt(i) as ViewGroup)
                if (childRecyclerView is RecyclerView) {
                    return childRecyclerView
                }
            }
            continue
        }
        return null
    }
}