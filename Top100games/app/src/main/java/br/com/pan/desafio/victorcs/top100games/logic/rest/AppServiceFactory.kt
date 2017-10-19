package br.com.pan.desafio.victorcs.top100games.logic.rest

import br.com.pan.desafio.victorcs.top100games.application.AppConstants
import br.com.pan.desafio.victorcs.top100games.utils.TimberHelper
import com.google.gson.GsonBuilder
import me.jessyan.progressmanager.ProgressManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Victor Santiago on 16/10/2017.
 */
object AppServiceFactory {

    fun createMvpService(): AppApi {

        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger
        { message -> TimberHelper.i("OkHttp", message) })

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val appInterceptor = AppHeaderInterceptor()

        val build = ProgressManager.getInstance().with(OkHttpClient.Builder())
        build.connectTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(appInterceptor)

        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retroBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(AppConstants.API_TWITCH_URL)
                .client(build.build())

        return retroBuilder.build().create(AppApi::class.java)

    }

}
