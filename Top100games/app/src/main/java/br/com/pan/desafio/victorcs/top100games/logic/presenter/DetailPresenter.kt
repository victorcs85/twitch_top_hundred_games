package br.com.pan.desafio.victorcs.top100games.logic.presenter

import android.content.Intent
import br.com.pan.desafio.victorcs.top100games.application.AppConstants
import br.com.pan.desafio.victorcs.top100games.logic.model.Top
import br.com.pan.desafio.victorcs.top100games.ui.view.IBaseView
import br.com.pan.desafio.victorcs.top100games.utils.TimberHelper

/**
 * Created by Victor Santiago on 16/10/2017.
 */
class DetailPresenter(private val view: IDetailView, intent: Intent) : IBasePresenter {

    private val selectedGame: Top? =
            intent.getParcelableExtra(AppConstants.SELECTED_GAME)

    init {
        this.view.setPresenter(this)

    }

    override fun start() {

        if (selectedGame != null) {

            try {

                view.setupToolbar(selectedGame.game.box.large, selectedGame.game.name)
                view.setupChannelCounter(selectedGame.channels)
                view.setupNumberOfViews(selectedGame.viewers)

            } catch (ex: Exception) {

                view.showActionErrorAlerterMessage()
                TimberHelper.e("start", "Error during get selected game values $ex")

            }

        } else {

            view.showActionErrorAlerterMessage()
            TimberHelper.e("start", "Selected game is null.")

        }

    }

    interface IDetailView : IBaseView {

        fun setPresenter(presenter: DetailPresenter)

        fun setupToolbar(image: String, title: String)

        fun setupChannelCounter(value: Int)

        fun setupNumberOfViews(value: Int)

    }

}
