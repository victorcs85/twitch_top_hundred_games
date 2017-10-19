package br.com.pan.desafio.victorcs.top100games.ui.view

import android.content.Context

/**
 * Created by Victor Santiago on 16/10/2017.
 */

interface IBaseView {

    fun isOnline(): Boolean

    fun showToastMessage(message: String)

    fun showSnackMessage(message: String)

    fun showAlerterMessage(title: String, message: String, iconResId: Int, backgroundColor: Int)

    fun showActionErrorAlerterMessage()

    fun showNoConnectionAlerterMessage()

    fun showNoConnectionSnackMessage()

    fun hideSoftKeyboard()

    fun showDialogOkMessage(context: Context, idString: Int)

}
