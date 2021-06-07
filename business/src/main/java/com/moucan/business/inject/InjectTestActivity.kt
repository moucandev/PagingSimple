package com.moucan.business.inject

import android.os.Bundle
import com.moucan.business.databinding.ActivityInjectTestBinding
import com.moucan.common.base.BaseActivity

class InjectTestActivity : BaseActivity<InjectTestViewModel>() {
    lateinit var binding: ActivityInjectTestBinding

    @InjectExtra("TextContent")
    lateinit var content: String

    override fun bindingView() {
        binding = ActivityInjectTestBinding.inflate(layoutInflater)
    }

    override fun layout() = binding.root


    override fun initView(savedInstanceState: Bundle?) {
        injectString(this)
        binding.tvContent.text = content

    }

    override fun registerObserver() {

    }
}