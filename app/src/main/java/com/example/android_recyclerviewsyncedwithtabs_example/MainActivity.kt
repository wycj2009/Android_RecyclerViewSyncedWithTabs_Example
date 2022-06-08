package com.example.android_recyclerviewsyncedwithtabs_example

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.android_recyclerviewsyncedwithtabs_example.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var shouldSelectTab = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val personItemList = listOf(
                PersonItem("AAA"),
                PersonItem("BBB"),
                PersonItem("CCC"),
                PersonItem("DDD"),
                PersonItem("EEE"),
                PersonItem("FFF"),
                PersonItem("GGG"),
                PersonItem("HHH"),
                PersonItem("III"),
                PersonItem("JJJ")
        )

        binding.run {
            tabLayout.run {
                personItemList.forEach {
                    addTab(tabLayout.newTab().setText(it.name))
                }
                (getChildAt(0) as LinearLayout).children.forEachIndexed { tabPosition: Int, tab: View ->
                    tab.setOnClickListener {
                        shouldSelectTab = false
                        recyclerView.smoothScrollToPosition(tabPosition)
                    }
                }
            }

            recyclerView.run {
                layoutManager = object : LinearLayoutManager(context) {
                    private val smoothScroller = object : LinearSmoothScroller(context) {
                        override fun calculateDtToFit(viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int): Int {
                            return boxStart - viewStart
                        }
                    }

                    override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
                        smoothScroller.let {
                            it.targetPosition = position
                            startSmoothScroll(it)
                        }
                    }
                }
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    private val linearLayoutManager = layoutManager as LinearLayoutManager

                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                            shouldSelectTab = true
                        }
                    }

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        if (shouldSelectTab) {
                            linearLayoutManager.findFirstVisibleItemPosition().let { firstVisibleItemPosition: Int ->
                                if (firstVisibleItemPosition != tabLayout.selectedTabPosition) {
                                    tabLayout.getTabAt(firstVisibleItemPosition)?.select()
                                }
                            }
                        }
                    }
                })
                adapter = PersonAdapter().apply {
                    submitList(personItemList)
                }
            }
        }
    }

}