package com.moucan.pagingsimple.nested

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.moucan.common.base.BaseActivity
import com.moucan.pagingsimple.databinding.ActivityNestedScrollBinding

class NestedScrollActivity : BaseActivity<NestedScrollViewModel>() {
    private lateinit var binding: ActivityNestedScrollBinding

    override fun layout() = binding.root

    override fun bindingView() {
        binding = ActivityNestedScrollBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding.viewpagerView.adapter = NestedViewPagerAdapter(this)
        binding.viewpagerView.offscreenPageLimit = 3
        binding.comboTopView.layoutManager = LinearLayoutManager(this)
        TabLayoutMediator(binding.tablayout, binding.viewpagerView
        ) { tab, position ->
            tab.text = NestedViewPagerAdapter.titles[position]
        }.attach()
        binding.comboTopView.adapter = NormalItemAdapter(
            arrayListOf(
                "item   1",
                "item   2",
                "item   3",
                "item   4",
                "item   5"
            )
        )

    }

    override fun registerObserver() {

    }
}