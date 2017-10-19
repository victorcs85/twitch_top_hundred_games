package br.com.pan.desafio.victorcs.top100games.ui.activity

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import br.com.pan.desafio.victorcs.top100games.R
import br.com.pan.desafio.victorcs.top100games.ui.view.IBaseView
import br.com.pan.desafio.victorcs.top100games.utils.NetworkStatsUtil
import br.com.pan.desafio.victorcs.top100games.utils.TimberHelper
import com.tapadoo.alerter.Alerter

/**
 * Created by Victor Santiago on 16/10/2017.
 */
open class BaseActivity : AppCompatActivity(), IBaseView {

    override fun hideSoftKeyboard() {

        if (currentFocus != null) {

            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

        }

    }

    override fun showToastMessage(message: String) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }

    override fun showSnackMessage(message: String) {

        val snackbar = Snackbar.make(findViewById(R.id.containerMain), message, Snackbar.LENGTH_LONG)
        snackbar.show()

    }

    override fun showAlerterMessage(title: String, message: String, iconResId: Int, backgroundColor: Int) {

        try {

            if (iconResId != 0) {

                Alerter.create(this)
                        .setTitle(title)
                        .setText(message)
                        .setIcon(iconResId)
                        .setBackgroundColorRes(backgroundColor)
                        .setDuration(3000)
                        .show()

            } else {

                Alerter.create(this)
                        .setTitle(title)
                        .setText(message)
                        .setBackgroundColorRes(backgroundColor)
                        .setDuration(3000)
                        .show()

            }

        } catch (e: Exception) {

            TimberHelper.e("BaseActivity", "showAlerterMessage: " + e.toString())

        }

    }

    override fun showNoConnectionSnackMessage() {

        val snackbar = Snackbar.make(findViewById(R.id.containerMain), getText(R.string.whithout_internet),
                Snackbar.LENGTH_LONG)
        val txtMessage = snackbar.view.findViewById<View>(android.support.design.R.id.snackbar_text) as TextView
        txtMessage.setTextColor(ContextCompat.getColor(this, R.color.orangeApp))
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        snackbar.show()

    }

    override fun showNoConnectionAlerterMessage() {

        try {

            Alerter.create(this)
                    .setTitle(getString(R.string.warning))
                    .setText(getString(R.string.whithout_internet))
                    .setIcon(R.drawable.ic_warning_white)
                    .setBackgroundColorRes(R.color.red)
                    .setDuration(3000)
                    .show()

        } catch (e: Exception) {

            TimberHelper.e("BaseActivity", "showNoConnectionAlerterMessage: " + e.toString())

        }

    }

    override fun showActionErrorAlerterMessage() {

        try {

            Alerter.create(this)
                    .setTitle(getString(R.string.warning))
                    .setText(getString(R.string.action_error))
                    .setIcon(R.drawable.ic_warning_white)
                    .setBackgroundColorRes(R.color.red)
                    .setDuration(3000)
                    .show()

        } catch (e: Exception) {

            TimberHelper.e("BaseActivity", "showActionErrorAlerterMessage: " + e.toString())

        }

    }

    override fun showDialogOkMessage(context: Context, idString: Int) {

        val builder = AlertDialog.Builder(context)
        val builderAlert = builder.setMessage(idString)
                .setPositiveButton(android.R.string.ok) { dialog, id -> dialog.dismiss() }
        builder.create()
        builderAlert.show()

    }

    override fun isOnline(): Boolean = NetworkStatsUtil.isConnected(this)
    //endregion
}
