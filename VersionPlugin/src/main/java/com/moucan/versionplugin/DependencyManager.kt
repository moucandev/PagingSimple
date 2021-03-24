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

object DependencyVersion {
    //AndroidX
    const val appcompat = "androidx.appcompat:appcompat:1.2.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val coreKtx = "androidx.core:core-ktx:1.3.2"
    const val material = "com.google.android.material:material:1.3.0"
    const val junit = "junit:junit:4.13.2"
    const val junitTest = "androidx.test.ext:junit:1.1.2"
    const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:1.4.31"
    //Paging
    const val paging = "androidx.paging:paging-runtime:3.0.0-beta01"
    //Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val convertGson = "com.squareup.retrofit2:converter-gson:2.9.0"
    //OkHttp
    const val okHttp = "com.squareup.okhttp3:okhttp:4.9.0"
    //LiveData
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
    //ViewModel
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0"
}