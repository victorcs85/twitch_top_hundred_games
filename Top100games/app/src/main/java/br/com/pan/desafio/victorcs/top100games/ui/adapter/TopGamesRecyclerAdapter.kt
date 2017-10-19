package br.com.pan.desafio.victorcs.top100games.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.pan.desafio.victorcs.top100games.R
import br.com.pan.desafio.victorcs.top100games.logic.model.Top
import br.com.pan.desafio.victorcs.top100games.ui.view.TopGamesViewHolder

/**
 * Created by Victor Santiago on 16/10/2017.
 */
class TopGamesRecyclerAdapter(private val context: Context,
                              items: List<Top>) :
        RecyclerView.Adapter<TopGamesViewHolder>() {

    private var topGamesList: MutableList<Top> = mutableListOf()

    init {

        topGamesList = items.toMutableList()

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TopGamesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_top_games, parent, false)
        return TopGamesViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: TopGamesViewHolder?, position: Int) {

        val top = topGamesList[position]

        holder?.let {

            holder.setGameImage(top.game.logo.large)
            holder.setGameName(top.game.name)

        }

    }

    fun getItemByPosition(position: Int): Top = topGamesList.get(position)

    override fun getItemCount(): Int = topGamesList.size

    fun addItems(topList: List<Top>) {

        topGamesList.addAll(topList)
        notifyDataSetChanged()

    }

}