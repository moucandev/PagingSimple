package com.moucan.pagingsimple

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.moucan.common.PathConstant
import com.moucan.common.base.BaseActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Route(path = PathConstant.MAIN_PAGE_PATH)
class MainActivity : BaseActivity<MainViewModel>() {

    private val adapter = RepoAdapter()

    override fun initView(savedInstanceState: Bundle?) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        lifecycleScope.launch {
            mViewModel.getData().collect {
                adapter.submitData(it)
            }
        }
        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    progressBar.visibility = View.INVISIBLE
                    recyclerView.visibility = View.VISIBLE
                }
                is LoadState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.INVISIBLE
                }
                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    progressBar.visibility = View.INVISIBLE
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

    override fun registerObserver() {

    }

    override fun layoutId() = R.layout.activity_main

}