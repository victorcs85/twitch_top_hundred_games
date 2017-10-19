package br.com.pan.desafio.victorcs.top100games.ui.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import br.com.pan.desafio.victorcs.top100games.application.AppConstants
import br.com.pan.desafio.victorcs.top100games.utils.TimberHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.recyclerview_item_top_games.view.*
import java.lang.Exception

/**
 * Created by Victor Santiago on 16/10/2017.
 */
class TopGamesViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

    fun setGameName(name: String) {

        val nameTemp = if (TextUtils.isEmpty(name)) "" else name
        itemView.tvGameName.text = nameTemp

    }

    fun setGameImage(url: String) {

        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .listener(object : RequestListener<String, GlideDrawable> {

                    override fun onException(e: Exception?, model: String?,
                                             target: Target<GlideDrawable>?,
                                             isFirstResource: Boolean): Boolean {

                        TimberHelper.e(AppConstants.ERROR, "setGameImage - onException " + e.toString())

                        if (itemView.progressBar != null) {
                            itemView.progressBar!!.visibility = View.VISIBLE
                        }
                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable?,
                                                 model: String?,
                                                 target: Target<GlideDrawable>?,
                                                 isFromMemoryCache: Boolean,
                                                 isFirstResource: Boolean): Boolean {

                        if (itemView.progressBar != null) {
                            itemView.progressBar!!.visibility = View.GONE
                        }

                        return false

                    }

                })
                .into(itemView.ivGameImage)

    }

}