package br.com.pan.desafio.victorcs.top100games.logic.db

import com.orm.SugarRecord

/**
 * Created by Victor Santiago on 18/10/2017.
 */
class TopGames() : SugarRecord() {

    var json: String? = null

    init {

        this.json = json

    }

    constructor(json: String?) : this() {

        this.json = json

    }

}