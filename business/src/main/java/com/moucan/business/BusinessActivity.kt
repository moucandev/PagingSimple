package com.moucan.business

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.moucan.common.PathConstant
import com.moucan.common.base.BaseActivity

@Route(path = PathConstant.BUSINESS_PATH)
class BusinessActivity : BaseActivity<BusinessViewModel>() {

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun registerObserver() {

    }

    override fun layoutId() = R.layout.activity_business
}