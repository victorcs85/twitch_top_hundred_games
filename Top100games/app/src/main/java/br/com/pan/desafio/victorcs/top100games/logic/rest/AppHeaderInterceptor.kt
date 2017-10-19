package br.com.pan.desafio.victorcs.top100games.logic.rest

import br.com.pan.desafio.victorcs.top100games.application.AppConstants
import br.com.pan.desafio.victorcs.top100games.utils.TimberHelper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Created by Victor Santiago on 16/10/2017.
 */
class AppHeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
                .addHeader(AppConstants.ACCEPT, AppConstants.APPLICATION_JSON)
                .addHeader(AppConstants.CLIENT_ID, AppConstants.CLIENT_VALUE)

        var request: Request? = null
        try {
            request = requestBuilder.build()
        } catch (e: Exception) {
            TimberHelper.e("requestBuilder.build()", e.toString())
        }

        return chain.proceed(request!!)

    }

}
