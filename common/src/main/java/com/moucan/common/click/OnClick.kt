package com.moucan.common.click

/**
 * @Classname: OnClick2
 * @Description:
 * @Since: 2021/6/8 10:51 上午
 * @Author: moucan
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class OnClick(vararg val value: Int)