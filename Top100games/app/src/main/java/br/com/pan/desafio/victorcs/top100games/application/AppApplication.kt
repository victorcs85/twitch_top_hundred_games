package br.com.pan.desafio.victorcs.top100games.application

import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication

import br.com.pan.desafio.victorcs.top100games.BuildConfig
import br.com.pan.desafio.victorcs.top100games.logic.rest.AppApi
import br.com.pan.desafio.victorcs.top100games.logic.rest.AppServiceFactory
import br.com.pan.desafio.victorcs.top100games.utils.TimberHelper
import com.orm.SugarApp
import timber.log.Timber
import com.orm.SugarDb



/**
 * Created by VictorCS on 16/10/2017.
 */
class AppApplication : SugarApp() {

    init {

        instance = this

    }

    override fun onCreate() {

        super.onCreate()
        MultiDex.install(this)
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        initAppApiService()

    }

    companion object {

        lateinit var instance: AppApplication
            private set
        lateinit var appApiInstance: AppApi
            private set

        private fun initAppApiService() {

            try {

                appApiInstance = AppServiceFactory.createMvpService()

            } catch (e: Exception) {

                TimberHelper.e("createMvpService", e.toString())

            }

        }

    }

}
