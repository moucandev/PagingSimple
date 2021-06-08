package com.moucan.business.inject

/**
 * @Classname: InjectExtra
 * @Description:
 * @Since: 2021/6/7 10:32 上午
 * @Author: moucan
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class InjectExtra(val value: String = "")
