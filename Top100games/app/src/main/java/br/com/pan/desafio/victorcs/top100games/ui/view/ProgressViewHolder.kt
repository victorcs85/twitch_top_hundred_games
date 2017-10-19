package br.com.pan.desafio.victorcs.top100games.ui.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ProgressBar
import butterknife.ButterKnife

/**
 * Created by Victor Santiago on 16/10/2017.
 */
class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var pbProgress: ProgressBar? = null

    init {

        ButterKnife.bind(this, itemView)

    }

}