package com.example.naniti.velitos.rooms.myRooms

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.naniti.velitos.R
import kotlinx.android.synthetic.main.my_rooms.view.*

class MyRoomsAdapter(val context: Context, val clickListener: (Room) -> Unit) : RecyclerView.Adapter<MyRoomsViewHolder>() {

    val rooms: ArrayList<Room> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyRoomsViewHolder {
        return MyRoomsViewHolder(LayoutInflater.from(context).inflate(R.layout.my_rooms, parent, false))
    }

    override fun getItemCount(): Int = rooms.size

    fun updateByLabel(label: String, message: String) {
        var index = -1
        lateinit var item: Room
        for (room in rooms) {
            if (room.label == label) {
                index = rooms.indexOf(room)
                break
            }
        }
        if (index == -1) return
        rooms.set(index, Room(item.name, message, label))
        notifyItemChanged(index)
    }

    override fun onBindViewHolder(holder: MyRoomsViewHolder?, position: Int) {
        val room = rooms.get(position)
        holder?.bind(room, clickListener)
    }
}

class MyRoomsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var messageText: TextView = view.txtMyRoom
    private var roomName: TextView = view.myRoomName
    private var timeText: TextView = view.txtRoomTime

    fun bind(room: Room, clickListener: (Room) -> Unit) {
        roomName.text = room.name
        messageText.text = room.message
        itemView.setOnClickListener { clickListener(room) }
    }

}