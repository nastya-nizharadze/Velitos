package com.example.naniti.velitos.rooms.availableRooms


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.naniti.velitos.R
import com.example.naniti.velitos.internet.RoomsSearch
import kotlinx.android.synthetic.main.room_list_item.view.*


class RoomAdapter(val items: ArrayList<RoomsSearch>,
                  val context: Context, val clickListener: (RoomsSearch) -> Unit)
    : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        (holder as ViewHolder).bind(items[position], clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.room_list_item, parent, false))
    }
}


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(challenge: RoomsSearch, clickListener: (RoomsSearch) -> Unit) {
        itemView.tv_roomName_type.text = challenge.name!!
        itemView.setOnClickListener {
            clickListener(challenge)
        }
    }

//    val tvChallengeNameType = view.tv_challengeName_type
}