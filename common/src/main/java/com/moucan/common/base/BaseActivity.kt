package com.moucan.common.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.moucan.common.ext.getVmClazz

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {
    lateinit var mViewModel: VM
    protected abstract fun layout(): View
    protected abstract fun initView(savedInstanceState: Bundle?)
    protected abstract fun registerObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView()
        setContentView(layout())
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
        initView(savedInstanceState)
        registerObserver()
        notifyData()
    }

    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    protected open fun bindingView(){}

    protected open fun notifyData() {}
}