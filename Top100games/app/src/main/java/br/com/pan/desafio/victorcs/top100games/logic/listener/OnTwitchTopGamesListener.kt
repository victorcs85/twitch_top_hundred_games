package br.com.pan.desafio.victorcs.top100games.logic.listener

import br.com.pan.desafio.victorcs.top100games.logic.model.TwitchTopGamesData

/**
 * Created by Victor Santiago on 16/10/2017.
 */
interface OnTwitchTopGamesListener {

    fun onTwitchTopGamesSuccess(response: TwitchTopGamesData)

    fun onTwitchTopGamesError(error: String)

}