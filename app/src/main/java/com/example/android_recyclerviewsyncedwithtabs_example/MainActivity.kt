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

        val personHorizontalItemList = listOf(
                PersonItem(PersonItem.ViewType.Horizontal, "AAA"),
                PersonItem(PersonItem.ViewType.Horizontal, "BBB"),
                PersonItem(PersonItem.ViewType.Horizontal, "CCC"),
                PersonItem(PersonItem.ViewType.Horizontal, "DDD"),
                PersonItem(PersonItem.ViewType.Horizontal, "EEE"),
                PersonItem(PersonItem.ViewType.Horizontal, "FFF"),
                PersonItem(PersonItem.ViewType.Horizontal, "GGG"),
                PersonItem(PersonItem.ViewType.Horizontal, "HHH"),
                PersonItem(PersonItem.ViewType.Horizontal, "III"),
                PersonItem(PersonItem.ViewType.Horizontal, "JJJ")
        )

        val personVerticalItemList = listOf(
                PersonItem(PersonItem.ViewType.Vertical, "KKK"),
                PersonItem(PersonItem.ViewType.Vertical, "LLL"),
                PersonItem(PersonItem.ViewType.Vertical, "MMM"),
                PersonItem(PersonItem.ViewType.Vertical, "NNN"),
                PersonItem(PersonItem.ViewType.Vertical, "OOO"),
                PersonItem(PersonItem.ViewType.Vertical, "PPP"),
                PersonItem(PersonItem.ViewType.Vertical, "QQQ"),
                PersonItem(PersonItem.ViewType.Vertical, "RRR"),
                PersonItem(PersonItem.ViewType.Vertical, "SSS"),
                PersonItem(PersonItem.ViewType.Vertical, "TTT")
        )

        binding.run {
            recyclerViewHorizontal.run {
                adapter = PersonAdapter().apply {
                    submitList(personHorizontalItemList)
                }
            }

            tabLayout.run {
                personVerticalItemList.forEach {
                    addTab(tabLayout.newTab().setText(it.name))
                }
                (getChildAt(0) as LinearLayout).children.forEachIndexed { tabPosition: Int, tab: View ->
                    tab.setOnClickListener {
                        shouldSelectTab = false
                        recyclerViewVertical.smoothScrollToPosition(tabPosition)
                    }
                }
            }

            recyclerViewVertical.run {
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
                    submitList(personVerticalItemList)
                }
            }
        }
    }

}