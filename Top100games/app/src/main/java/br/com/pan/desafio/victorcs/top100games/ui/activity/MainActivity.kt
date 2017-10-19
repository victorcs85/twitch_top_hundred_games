package br.com.pan.desafio.victorcs.top100games.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import android.view.View
import br.com.pan.desafio.victorcs.top100games.R
import br.com.pan.desafio.victorcs.top100games.application.AppConstants
import br.com.pan.desafio.victorcs.top100games.logic.listener.InfiniteScrollListener
import br.com.pan.desafio.victorcs.top100games.logic.listener.RecyclerItemClickListener
import br.com.pan.desafio.victorcs.top100games.logic.model.Top
import br.com.pan.desafio.victorcs.top100games.logic.presenter.MainPresenter
import br.com.pan.desafio.victorcs.top100games.ui.adapter.TopGamesRecyclerAdapter
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_empty_result.*
import com.orm.SugarDb




/**
 * Created by Victor Santiago on 16/10/2017.
 */
class MainActivity : BaseActivity(), MainPresenter.IMainView, SwipeRefreshLayout.OnRefreshListener {

    private var adapter: TopGamesRecyclerAdapter? = null
    private var mPresenter: MainPresenter? = null
    private var currentPage = 1

    //region Activity life cycle
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        val db = SugarDb(this)
        db.onCreate(db.writableDatabase)
        isCalledActivity = false
        MainPresenter(this)

    }

    override fun onRestart() {

        super.onRestart()
        isCalledActivity = false

    }
    //endregion

    //region IMainView
    override fun setupToolbar() {

        setSupportActionBar(toolbar)

    }

    override fun setupPullToRefresh() {

        swipeRefreshLayout.setOnRefreshListener(this@MainActivity)
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this@MainActivity, R.color.colorPrimaryDark))

    }

    override fun setPresenter(presenter: MainPresenter) {

        this.mPresenter = presenter
        this.mPresenter!!.start()
        this.currentPage = 1

    }

    override fun setupRecyclerView(items: List<Top>) {

        llEmptyResult.visibility = View.GONE
        rvGames.visibility = View.VISIBLE

        rvGames.setHasFixedSize(true)

        val animator = rvGames.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }

        adapter = TopGamesRecyclerAdapter(this, items)
        val layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {

            GridLayoutManager(applicationContext, 2)

        } else {

            GridLayoutManager(applicationContext, 4)

        }
        rvGames.layoutManager = layoutManager
        rvGames.adapter = adapter
        rvGames.isNestedScrollingEnabled = false
        rvGames.addOnItemTouchListener(RecyclerItemClickListener(this,
                rvGames, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {

                if (!isCalledActivity) {

                    isCalledActivity = true
                    val itemByPosition = adapter!!.getItemByPosition(position)
                    mPresenter!!.attemptCallDetailActivity(itemByPosition)

                }

            }

            override fun onItemLongClick(view: View?, position: Int) {

            }
        }))

        rvGames.addOnScrollListener(

                InfiniteScrollListener({ mPresenter!!.attemptGetGames(false, true) }, layoutManager)

        )

    }

    override fun updateRecyclerView(items: List<Top>) {

        adapter!!.addItems(items)

    }

    //TODO
    override fun showEmptyLayout() {

        rvGames.visibility = View.GONE
        llEmptyResult.visibility = View.VISIBLE

    }

    override fun callDetailActivity(gameSelected: Top) {

        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(AppConstants.SELECTED_GAME, gameSelected)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)

    }

    companion object {

        private var isCalledActivity = false
    }

    override fun showNoLocalData() {

        showAlerterMessage(getString(R.string.warning), getString(R.string.no_local_data),
                R.drawable.ic_warning_white, R.color.red)

    }
    //endregion

    //region Pull to refresh
    override fun onRefresh() {

        swipeRefreshLayout.isRefreshing = true
        mPresenter!!.pullToRefresh()

    }

    override fun finishRefreshList() {

        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing) {

            swipeRefreshLayout.isRefreshing = false

        }

    }
    //endregion
}
