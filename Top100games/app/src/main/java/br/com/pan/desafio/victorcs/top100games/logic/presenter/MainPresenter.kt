package br.com.pan.desafio.victorcs.top100games.logic.presenter

import android.text.TextUtils
import br.com.pan.desafio.victorcs.top100games.application.AppConstants
import br.com.pan.desafio.victorcs.top100games.logic.db.TopGames
import br.com.pan.desafio.victorcs.top100games.logic.interactor.TwitchInteractor
import br.com.pan.desafio.victorcs.top100games.logic.listener.OnTwitchTopGamesListener
import br.com.pan.desafio.victorcs.top100games.logic.model.Top
import br.com.pan.desafio.victorcs.top100games.logic.model.TwitchTopGamesData
import br.com.pan.desafio.victorcs.top100games.ui.view.IBaseView
import br.com.pan.desafio.victorcs.top100games.utils.TimberHelper
import com.google.gson.Gson
import com.orm.SugarRecord


/**
 * Created by Victor Santiago on 16/10/2017.
 */
class MainPresenter(private val view: IMainView) : IBasePresenter, OnTwitchTopGamesListener {

    private val interactor: TwitchInteractor = TwitchInteractor()
    private val limit = 15
    var offset = 0
    var isFirstLoad: Boolean = false
    var refreshMode: Boolean = false
    var isShowedInOffline: Boolean = false
    private var indexDb: Int = 0

    //region Presenter
    init {

        this.view.setPresenter(this)

    }

    override fun start() {

        view.setupToolbar()
        isFirstLoad = true
        attemptGetGames(true, false)

    }

    fun attemptCallDetailActivity(gameSelected: Top) {

        view.callDetailActivity(gameSelected)

    }

    fun pullToRefresh() {

        attemptGetGames(true, false)

    }
    //endregion

    //region WebServices
    fun attemptGetGames(refreshMode: Boolean, updateOffset: Boolean) {

        this.refreshMode = refreshMode

        if (view.isOnline()) {

            if (refreshMode && !updateOffset) {

                offset = 0
                deleteAllDataFromDb()

            }

            if (updateOffset && !refreshMode) {

                offset += limit

            }

            try {

                interactor.getTopGames(limit, offset, this)

            } catch (e: Exception) {

                TimberHelper.e(AppConstants.ERROR, "getRepoLists: " + e.toString())
                view.showActionErrorAlerterMessage()

            }

        } else {

            view.finishRefreshList()

            if (!isShowedInOffline) {

                view.showNoConnectionAlerterMessage()

            }

            if (refreshMode && !updateOffset) {

                indexDb = 0
                offset = 0

            } else {

                indexDb++
                offset += limit

            }

            verifyAndLoadInitialLocalJsonData()

        }

    }

    override fun onTwitchTopGamesSuccess(response: TwitchTopGamesData) {

        view.finishRefreshList()

        if (!response.top.isEmpty()) {

            if (refreshMode) {

                setDataListToRecycler(response)
                refreshMode = false

            } else {

                view.updateRecyclerView(response.top)
                refreshMode = false

            }

            if (isFirstLoad) {

                isFirstLoad = false

            }
            saveTwitchTopGamesDataDb(response)

        } else {

            view.showEmptyLayout()

        }

    }

    private fun setDataListToRecycler(twitchTopGamesData: TwitchTopGamesData) {

        view.setupRecyclerView(twitchTopGamesData.top)
        view.setupPullToRefresh()

    }

    override fun onTwitchTopGamesError(error: String) {

        view.finishRefreshList()
        if (isFirstLoad) {

            view.showEmptyLayout()
            view.showActionErrorAlerterMessage()

        }
        TimberHelper.e("onTwitchTopGamesError", error)

    }
    //endregion

    //region Db
    private fun verifyAndLoadInitialLocalJsonData() {

        try {

            val topGames = SugarRecord.listAll(TopGames::class.java)
            if (topGames != null && !topGames.isEmpty()) {

                if (indexDb < topGames.size) {

                    val gamesModel = topGames[indexDb]
                    if (gamesModel != null && !TextUtils.isEmpty(gamesModel.json)) {

                        val gson = Gson()
                        val twitchTopGamesData = gson.fromJson(gamesModel.json, TwitchTopGamesData::class.java)
                        isShowedInOffline = true

                        if (indexDb == 0) {

                            setDataListToRecycler(twitchTopGamesData)

                        } else {

                            view.updateRecyclerView(twitchTopGamesData.top)
                            refreshMode = false

                        }

                    } else {

                        view.showNoLocalData()
                        if (indexDb == 0) {

                            view.showEmptyLayout()

                        }

                    }

                } else {

                    view.showNoLocalData()

                }

            } else {

                view.showEmptyLayout()

            }

            TimberHelper.i(AppConstants.INFO, "verifyAndLoadInitialLocalJsonData")

        } catch (ex: Exception) {

            TimberHelper.e(AppConstants.ERROR, "verifyAndLoadInitialLocalJsonData: " + ex.toString())

        }

    }

    fun saveTwitchTopGamesDataDb(twitchTopGamesData: TwitchTopGamesData) {

        try {

            val gson = Gson()
            val json = gson.toJson(twitchTopGamesData)
            val topGames = TopGames(json)
            topGames.save()
            TimberHelper.i(AppConstants.INFO, "saveTwitchTopGamesDataDb")

        } catch (ex: Exception) {

            TimberHelper.e(AppConstants.ERROR, "saveTwitchTopGamesDataDb: " + ex.toString())

        }

    }

    fun deleteAllDataFromDb() {

        try {

            SugarRecord.deleteAll(TopGames::class.java)
            TimberHelper.i(AppConstants.INFO, "deleteAllDataFromDb")

        } catch (ex: Exception) {

            TimberHelper.e(AppConstants.ERROR, "deleteAllDataFromDb: " + ex.toString())

        }

    }
    //endregion

    interface IMainView : IBaseView {

        fun setupToolbar()

        fun setupPullToRefresh()

        fun setPresenter(presenter: MainPresenter)

        fun setupRecyclerView(items: List<Top>)

        fun updateRecyclerView(items: List<Top>)

        fun showEmptyLayout()

        fun callDetailActivity(gameSelected: Top)

        fun onRefresh()

        fun finishRefreshList()

        fun showNoLocalData()

    }

}
