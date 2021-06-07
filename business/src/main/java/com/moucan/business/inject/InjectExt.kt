package com.moucan.business.inject

import android.app.Activity

/**
 * @Classname: InjectUtils
 * @Description:
 * @Since: 2021/6/7 10:37 上午
 * @Author: moucan
 */


    fun Activity.injectBoolean(activity: Activity) {
        var clazz = activity.javaClass
        val fields = clazz.declaredFields

        fields.forEach {
            if (it.isAnnotationPresent(InjectExtra::class.java)){
                var injectExtra = it.getAnnotation(InjectExtra::class.java)
                val key = injectExtra.value
                val value = activity.intent.getBooleanExtra(key, false)
                it.isAccessible = true
                it.set(activity, value)
            }
        }
    }

    fun Activity.injectInt(activity: Activity) {
        var clazz = activity.javaClass
        val fields = clazz.declaredFields

        fields.forEach {
            if (it.isAnnotationPresent(InjectExtra::class.java)){
                var injectExtra = it.getAnnotation(InjectExtra::class.java)
                val key = injectExtra.value
                val value = activity.intent.getIntExtra(key, 0)
                it.isAccessible = true
                it.set(activity, value)
            }
        }
    }

    fun Activity.injectFloat(activity: Activity) {
        var clazz = activity.javaClass
        val fields = clazz.declaredFields

        fields.forEach {
            if (it.isAnnotationPresent(InjectExtra::class.java)){
                var injectExtra = it.getAnnotation(InjectExtra::class.java)
                val key = injectExtra.value
                val value = activity.intent.getFloatExtra(key, 0f)
                it.isAccessible = true
                it.set(activity, value)
            }
        }
    }

    fun Activity.injectString(activity: Activity) {
        var clazz = activity.javaClass
        val fields = clazz.declaredFields

        fields.forEach {
            if (it.isAnnotationPresent(InjectExtra::class.java)){
                var injectExtra = it.getAnnotation(InjectExtra::class.java)
                val key = injectExtra.value
                val value = activity.intent.getStringExtra(key)
                it.isAccessible = true
                it.set(activity, value)
            }
        }
    }

    fun Activity.injectChar(activity: Activity) {
        var clazz = activity.javaClass
        val fields = clazz.declaredFields

        fields.forEach {
            if (it.isAnnotationPresent(InjectExtra::class.java)){
                var injectExtra = it.getAnnotation(InjectExtra::class.java)
                val key = injectExtra.value
                val value = activity.intent.getCharExtra(key, 'a')
                it.isAccessible = true
                it.set(activity, value)
            }
        }
    }

    fun Activity.injectDouble(activity: Activity) {
        var clazz = activity.javaClass
        val fields = clazz.declaredFields

        fields.forEach {
            if (it.isAnnotationPresent(InjectExtra::class.java)){
                var injectExtra = it.getAnnotation(InjectExtra::class.java)
                val key = injectExtra.value
                val value = activity.intent.getDoubleExtra(key, 0.0)
                it.isAccessible = true
                it.set(activity, value)
            }
        }
    }