package com.moucan.versionplugin

object ProjectVersion {
    const val applicationId = "com.moucan.pagingsimple"
    const val compileSdkVersion = 30
    const val buildToolsVersion = "30.0.3"
    const val minSdkVersion = 21
    const val targetSdkVersion = 30
    const val versionCode = 1
    const val versionName = "1.0"
}

object SignConfig {
    const val storeFile = "simple.jks"
    const val keyAlias = "simple_key"
    const val storePassword = "moucan6229"
    const val keyPassWord = "moucan6229"
}

object LibVersion {
    //AndroidX
    const val appcompat = "1.2.0"
    const val constraintLayout = "2.0.4"
    const val coreKtx = "1.3.2"
    const val material = "1.3.0"
    const val junit = "4.13.2"
    const val junitTest = "1.1.2"
    const val espressoCore = "3.3.0"

    const val kotlin = "1.4.31"
    //Paging
    const val paging = "3.0.0-beta01"
    //Retrofit
    const val retrofit = "2.9.0"
    const val convertGson = "2.9.0"
    //OkHttp
    const val okHttp = "4.9.0"
    //LiveData
    const val liveData = "2.2.0"
    //ViewModel
    const val viewModel = "2.3.0"
    //ARouter
    const val aRouter = "1.5.0"
    const val aRouterCompiler = "1.2.2"
    const val multiDex = "1.0.3"
    //room
    const val room = "2.2.6"
    const val hilt = "2.28-alpha"
    const val viewPager2 = "1.1.0-alpha01"
    const val design = "28.0.0"
    const val leakCanary = "2.4"
}

object DependencyVersion {
    //AndroidX
    const val appcompat = "androidx.appcompat:appcompat:${LibVersion.appcompat}}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${LibVersion.constraintLayout}"
    const val coreKtx = "androidx.core:core-ktx:${LibVersion.coreKtx}"
    const val material = "com.google.android.material:material:${LibVersion.material}"
    const val junit = "junit:junit:${LibVersion.junit}"
    const val junitTest = "androidx.test.ext:junit:${LibVersion.junitTest}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${LibVersion.espressoCore}"

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${LibVersion.kotlin}"
    //Paging
    const val paging = "androidx.paging:paging-runtime:${LibVersion.paging}"
    //Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${LibVersion.retrofit}"
    const val convertGson = "com.squareup.retrofit2:converter-gson:${LibVersion.convertGson}"
    //OkHttp
    const val okHttp = "com.squareup.okhttp3:okhttp:${LibVersion.okHttp}"
    //LiveData
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${LibVersion.liveData}"
    //ViewModel
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${LibVersion.viewModel}"
    //ARouter
    const val aRouter = "com.alibaba:arouter-api:${LibVersion.aRouter}"
    const val aRouterCompiler = "com.alibaba:arouter-compiler:${LibVersion.aRouterCompiler}"
    //multiDex
    const val multiDex = "com.android.support:multidex:${LibVersion.multiDex}"
    //room
    const val roomKtx = "androidx.room:room-ktx:${LibVersion.room}"
    const val roomTesting = "androidx.room:room-testing:${LibVersion.room}"
    const val roomRuntime = "androidx.room:room-runtime:${LibVersion.room}"
    const val roomCompiler = "androidx.room:room-compiler:${LibVersion.room}"
    const val hilt = "com.google.dagger:hilt-android:${LibVersion.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${LibVersion.hilt}"
    const val viewPager2 = "androidx.viewpager2:viewpager2:${LibVersion.viewPager2}"
    const val design = "com.android.support:design:${LibVersion.design}"
    const val leakcanary = "com.squareup.leakcanary:leakcanary-android:${LibVersion.leakCanary}"
}