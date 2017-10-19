package br.com.pan.desafio.victorcs.top100games.logic.rest

import br.com.pan.desafio.victorcs.top100games.logic.model.TwitchTopGamesData
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by Victor Santiago on 16/10/2017.
 */
interface AppApi {

    @GET("kraken/games/top")
    fun getTopGames(@Query("limit") limit: Int, @Query("offset") offset: Int): Observable<TwitchTopGamesData>

}
