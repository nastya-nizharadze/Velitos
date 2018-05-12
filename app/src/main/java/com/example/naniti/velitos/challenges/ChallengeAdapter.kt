package com.example.naniti.velitos.challenges

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.naniti.velitos.R
import com.example.naniti.velitos.internet.ChallengesSearch
import kotlinx.android.synthetic.main.challenge_list_item.view.*


class ChallengeAdapter(val items: ArrayList<ChallengesSearch>,
                       val context: Context, val clickListener: (ChallengesSearch) -> Unit)
    : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        (holder as ViewHolder).bind(items[position], clickListener)
        //   holder?.tvChallengeNameType?.text = items.get(position)

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.challenge_list_item, parent, false))
    }
}


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(challenge: ChallengesSearch, clickListener: (ChallengesSearch) -> Unit) {
        itemView.tv_challengeName_type.text = challenge.name!!
        itemView.tv_challengeName_description.text = challenge.description!!
        itemView.setOnClickListener { clickListener(challenge) }
    }

//    val tvChallengeNameType = view.tv_challengeName_type
}