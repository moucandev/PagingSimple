package com.moucan.business

import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.moucan.common.PathConstant
import com.moucan.common.base.BaseActivity

@Route(path = PathConstant.BUSINESS_PATH)
class BusinessActivity : BaseActivity<BusinessViewModel>() {

    override fun initView(savedInstanceState: Bundle?) {
        val button = findViewById<Button>(R.id.bt_window_up)
        button.setOnClickListener {
            it.visibility = View.GONE
            showViewPage(it)
        }
    }

    override fun registerObserver() {

    }

    override fun layoutId() = R.layout.activity_business

    private fun showViewPage(view: View) {
        val contentView = LayoutInflater.from(this).inflate(R.layout.layout_pop_window, null)
        val viewPager = contentView.findViewById<ViewPager2>(R.id.view_pager)
        val list = arrayListOf<String>()
        for (index in 'a'..'e') {
            list.add(index.toString())
        }
        val viewPagerAdapter = ViewPagerAdapter(list)
        viewPager.apply {
            offscreenPageLimit = 3
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                val padding = dip2px(10f)
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }
            adapter = viewPagerAdapter
            setPageTransformer(AlphaTransformer())
        }
        val popupWindow = PopupWindow(
            contentView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        popupWindow.animationStyle = R.style.anim_pop_window
        popupWindow.isOutsideTouchable = true
        popupWindow.setOnDismissListener { view.visibility = View.VISIBLE }
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0)
    }

    private fun getScreenWidth(): Int {
        val point = Point()
        windowManager.defaultDisplay.getRealSize(point)
        return point.x
    }

    private fun dip2px(dpValue: Float): Int {
        val scale: Float = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}