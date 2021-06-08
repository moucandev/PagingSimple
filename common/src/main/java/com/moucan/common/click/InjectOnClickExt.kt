package com.moucan.common.click

import android.app.Activity
import android.view.View
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * @Classname: InjectOnClickExt
 * @Description:
 * @Since: 2021/6/8 10:12 上午
 * @Author: moucan
 */

fun Activity.injectOnClick(activity: Activity) {
    val clazz = activity.javaClass
    val fields = clazz.declaredMethods
    if (fields.isEmpty()) {
        return
    }
    fields.forEach {
        if (it.isAnnotationPresent(OnClick::class.java)) {
            val onClick = it.getAnnotation(OnClick::class.java)
            val ids = onClick.value
            val o = Proxy.newProxyInstance(
                View.OnClickListener::class.java.classLoader,
                arrayOf(View.OnClickListener::class.java), object : InvocationHandler{
                    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
                        return it.invoke(activity, args)
                    }

                }
            )
            ids.forEach { id ->
                val view = activity.findViewById<View>(id)
                val onClickListener =
                    view.javaClass.getMethod("setOnClickListener",
                        View.OnClickListener::class.java)
                onClickListener.invoke(view, o)
            }
        }
    }
}