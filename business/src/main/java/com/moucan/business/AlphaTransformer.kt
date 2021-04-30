package com.moucan.business

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class AlphaTransformer : ViewPager2.PageTransformer {
    companion object {
        const val DEFAULT_MIN_ALPHA = 0.75f
        const val DEFAULT_CENTER = 0.5f
    }

    override fun transformPage(page: View, position: Float) {
        if (position < -1) {
            page.alpha = DEFAULT_CENTER
        } else if (position <= 1) {
            if (position < 0) {
                if (position > -0.25f) {
                    page.alpha = 1 - abs(position)
                } else {
                    page.alpha = DEFAULT_MIN_ALPHA
                }
            } else if (position == 0.0f){
                page.alpha = 1.0f
            }else {
                if (position < 0.25f) {
                    page.alpha = 1 - abs(position)
                } else {
                    page.alpha = DEFAULT_MIN_ALPHA
                }
            }
        } else {
            page.alpha = DEFAULT_CENTER
        }
    }
}