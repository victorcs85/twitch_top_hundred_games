package br.com.pan.desafio.victorcs.top100games.logic.interactor

import br.com.pan.desafio.victorcs.top100games.application.AppApplication
import br.com.pan.desafio.victorcs.top100games.application.AppConstants
import br.com.pan.desafio.victorcs.top100games.logic.listener.OnTwitchTopGamesListener
import br.com.pan.desafio.victorcs.top100games.logic.model.TwitchTopGamesData
import br.com.pan.desafio.victorcs.top100games.utils.TimberHelper
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Victor Santiago on 16/10/2017.
 */
class TwitchInteractor {

    fun getTopGames(limit: Int, offset: Int, listener: OnTwitchTopGamesListener) {

        val appApi = AppApplication.appApiInstance

        appApi.getTopGames(limit, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<TwitchTopGamesData> {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        try {
                            listener.onTwitchTopGamesError(e.message!!)
                        } catch (f: Throwable) {
                            TimberHelper.e(AppConstants.ERROR, f.message!!)
                        }

                    }

                    override fun onNext(response: TwitchTopGamesData) {
                        listener.onTwitchTopGamesSuccess(response)
                    }
                })

    }

}
