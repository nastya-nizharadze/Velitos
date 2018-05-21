package com.example.naniti.velitos.rooms.chat

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.naniti.velitos.R
import kotlinx.android.synthetic.main.my_message.view.*
import kotlinx.android.synthetic.main.other_message.view.*


open class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message: Message) {}
}


class MessageAdapter(val context: Context) : RecyclerView.Adapter<MessageViewHolder>() {

    val username = (context as ChatActivity).username

    companion object {
        const val VIEW_TYPE_MY_MESSAGE = 1
        const val VIEW_TYPE_OTHER_MESSAGE = 2
    }

    var messages: ArrayList<Message> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MessageViewHolder {
        val view: MessageViewHolder = when (viewType) {
            VIEW_TYPE_MY_MESSAGE ->
                MyMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.my_message, parent, false))
            VIEW_TYPE_OTHER_MESSAGE ->
                OtherMessageViewHolder(LayoutInflater.from(context).inflate(R.layout.other_message, parent, false))
            else -> throw  NotImplementedError()
        }
        return view
    }

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder?, position: Int) {
        val message = messages.get(position)
        holder?.bind(message)
    }


    inner class MyMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtMyMessage
        private var timeText: TextView = view.txtMyMessageTime

        override fun bind(message: Message) {
            messageText.text = message.message
            timeText.text = message.time
        }
    }

    inner class OtherMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtOtherMessage
        private var userText: TextView = view.txtOtherUser
        private var timeText: TextView = view.txtOtherMessageTime

        override fun bind(message: Message) {
            messageText.text = message.message
            userText.text = message.username
            timeText.text = message.time
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages.get(position)
        return if (message.username == username) VIEW_TYPE_MY_MESSAGE//change on smth else
        else VIEW_TYPE_OTHER_MESSAGE
    }

    fun addMessage(message: Message) {
        messages.add(message)
        notifyDataSetChanged()
    }
}