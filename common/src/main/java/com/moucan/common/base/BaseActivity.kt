package com.moucan.common.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.moucan.common.ext.getVmClazz
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class BaseActivity<VM : BaseViewModel>: AppCompatActivity() {
    lateinit var mViewModel: VM
    protected abstract fun layoutId(): Int
    protected abstract fun initView(savedInstanceState: Bundle?)
    protected abstract fun registerObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
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
    protected open fun notifyData() {}
}