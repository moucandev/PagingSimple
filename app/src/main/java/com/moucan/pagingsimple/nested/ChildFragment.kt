package com.moucan.pagingsimple.nested

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moucan.pagingsimple.R

class ChildFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvContent = view.findViewById<RecyclerView>(R.id.rv_content)
        rvContent.layoutManager = LinearLayoutManager(context)
        val adapter = NormalItemAdapter(getData())
        rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private val THRESHOLD_LOAD_MORE = 3
            private var hasLoadMore = false
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    hasLoadMore = false
                }
                if (newState != RecyclerView.SCROLL_STATE_DRAGGING && !hasLoadMore) {
                    val lastPosition =
                        (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()
                    val offset = recyclerView.adapter!!.itemCount - lastPosition - 1
                    if (offset <= THRESHOLD_LOAD_MORE) {
                        hasLoadMore = true
                        adapter.addAll(getData())
                    }
                }
            }
        })
        rvContent.adapter = adapter
    }

    private fun getData(): ArrayList<String> {
        val list: ArrayList<String> = ArrayList()
        for (position in 0..30) {
            list.add("Item    $position")
        }
        return list
    }
}