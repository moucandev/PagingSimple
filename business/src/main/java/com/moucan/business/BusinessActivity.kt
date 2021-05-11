package com.moucan.business

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.moucan.business.databinding.ActivityBusinessBinding
import com.moucan.common.PathConstant
import com.moucan.common.base.BaseActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Route(path = PathConstant.BUSINESS_PATH)
class BusinessActivity : BaseActivity<BusinessViewModel>() {
    private lateinit var binding: ActivityBusinessBinding

    override fun initView(savedInstanceState: Bundle?) {
        binding.btWindowUp.setOnClickListener {
            it.visibility = View.GONE
            showViewPage(it)
        }
//        anim()
    }

    override fun registerObserver() {

    }

    override fun bindingView() {
        binding = ActivityBusinessBinding.inflate(layoutInflater)
    }

    override fun layout() = binding.root

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

    @SuppressLint("Recycle")
    private fun anim() {
        val anim1 = ObjectAnimator.ofFloat(binding.mapView, "degreeY", 0f, -45f)
        anim1.duration = 500
        anim1.startDelay = 500

        val anim2 = ObjectAnimator.ofFloat(binding.mapView, "degreeZ", 0f, 360f)
        anim2.duration = 1000
        anim2.startDelay = 500

        val anim3 = ObjectAnimator.ofFloat(binding.mapView, "DegreeY", -45f, 0f);
        anim3.duration = 500
        anim3.startDelay = 500

        val animSet = AnimatorSet()
        animSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                lifecycleScope.launch(Dispatchers.Main) {
                    delay(500)
                    binding.mapView.reset()
                    animSet.start()
                }
            }
        })
        animSet.playSequentially(anim1, anim2, anim3)
        animSet.start()
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