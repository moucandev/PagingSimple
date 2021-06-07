package com.moucan.pagingsimple

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.moucan.common.PathConstant
import com.moucan.common.base.BaseActivity
import com.moucan.pagingsimple.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Route(path = PathConstant.MAIN_PAGE_PATH)
class MainActivity : BaseActivity<MainViewModel>() {

    private val adapter = RepoAdapter()

    private lateinit var binding: ActivityMainBinding

    override fun initView(savedInstanceState: Bundle?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        ARouter.getInstance().build(PathConstant.BUSINESS_PATH).navigation()
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