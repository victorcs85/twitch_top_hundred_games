package br.com.pan.desafio.victorcs.top100games.utils

import br.com.pan.desafio.victorcs.top100games.application.AppConstants
import timber.log.Timber

/**
 * Created by Victor Santiago on 16/10/2017.
 */
object TimberHelper {

    fun v(identifier: String, message: String) {

        Timber.tag(AppConstants.INFO).v(identifier + ": " + message)

    }

    fun i(identifier: String, message: String) {

        Timber.tag(AppConstants.INFO).i(identifier + ": " + message)

    }

    fun e(identifier: String, message: String) {

        Timber.tag(AppConstants.ERROR).e(identifier + ": " + message)

    }

}
