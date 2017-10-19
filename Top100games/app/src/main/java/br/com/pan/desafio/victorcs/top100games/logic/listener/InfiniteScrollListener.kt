package br.com.pan.desafio.victorcs.top100games.logic.listener

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import br.com.pan.desafio.victorcs.top100games.utils.TimberHelper

/**
 * Created by VictorCS on 17/10/2017.
 */
class InfiniteScrollListener(

        val func: () -> Unit,
        val layoutManager: GridLayoutManager) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 8
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {

            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if (loading) {

                if (totalItemCount > previousTotal) {

                    loading = false
                    previousTotal = totalItemCount

                }

            }

            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {

                // End has been reached
                TimberHelper.i("InfiniteScrollListener", "End reached")
                func()
                loading = true

            }

        }

    }

}