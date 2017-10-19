package br.com.pan.desafio.victorcs.top100games.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Created by Victor Santiago on 16/10/2017.
 */
object AppUtils {

    /**
     * Open URL in browser
     *
     * @param context - current context
     * @param url     - url to open
     */
    fun openUrl(context: Context, url: String) {

        var urlTemp = url

        if (!url.contains("http://") && !url.contains("https://")) {

            urlTemp = "http://" + url

        }

        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(urlTemp)
        context.startActivity(i)

    }

}
