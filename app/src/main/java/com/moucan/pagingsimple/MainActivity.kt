package com.moucan.pagingsimple

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.moucan.common.PathConstant
import com.moucan.common.base.BaseActivity
import com.moucan.common.click.InjectClick
import com.moucan.common.click.OnClick
import com.moucan.common.http.CustomNetApi
import com.moucan.common.retrofit.method.CustomRetrofit
import com.moucan.pagingsimple.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

@Route(path = PathConstant.MAIN_PAGE_PATH)
class MainActivity : BaseActivity<MainViewModel>() {

    private val adapter = RepoAdapter()
    private lateinit var customNetApi: CustomNetApi

    private lateinit var binding: ActivityMainBinding

//    @OnClick(R.id.tv_content_detail)
//    fun onClick(view: View) {
//        if (view.id == R.id.tv_content_detail) {
//            binding.tvContentDetail.visibility = View.GONE
//        }
//    }

    override fun initView(savedInstanceState: Bundle?) {
//        InjectClick.injectOnClick(this)
        val retrofit = CustomRetrofit.Builder().baseUrl("https://restapi.amap.com").build()
        customNetApi = retrofit.create(CustomNetApi::class.java)
//        binding.tvContentDetail.setOnClickListener{
//            val weather = customNetApi.getWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b")
////            val weather2 = customNetApi.postWeather("110101", "ae6c53e2186f33bbf240a12d80672d1b")
//            weather?.enqueue(responseCallback = object : Callback{
//                override fun onFailure(call: Call, e: IOException) {
//                    Log.d("MainActivity", e.message.toString())
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//        //                    if (response.isSuccessful) {
//                    Log.d("MainActivity", response.body!!.string())
//        //                    }
//                }
//
//            })
//        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        lifecycleScope.launch {
            mViewModel.getData().collect {
                adapter.submitData(it)
            }
        }
        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                }
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                }
                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, "Load Error: ${state.error.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        adapter.setOnClickListener(object : RepoAdapter.OnItemClickListener {
            override fun onClick(view: View) {
                ARouter.getInstance().build(PathConstant.BUSINESS_PATH).navigation()
            }
        }
        )
    }

    override fun bindingView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
    }

    override fun registerObserver() {

    }

    override fun layout() = binding.root

}