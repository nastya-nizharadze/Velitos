package com.example.naniti.velitos.rooms.chat.Response

import com.google.gson.annotations.SerializedName

data class responseMessageRefresh(
        @SerializedName("action") val action: String?,
        @SerializedName("msg_type") val msgType: Int?,
        @SerializedName("detail") val detail: DetailRefresh?
)

data class DetailRefresh(
        @SerializedName("label") val label: String?,
        @SerializedName("username") val username: String?,
        @SerializedName("message") val message: List<Message?>?
)


data class Message(
        @SerializedName("id") val id: Int?,
        @SerializedName("message") val message: String?,
        @SerializedName("created") val created: String?,
        @SerializedName("is_read") val isRead: Boolean?,
        @SerializedName("room") val room: Int?,
        @SerializedName("user") val user: String?
)


data class responseMessage(
        @SerializedName("action") val action: String?,
        @SerializedName("msg_type") val msgType: Int?,
        @SerializedName("detail") val detail: DetailMessage?
)

data class DetailMessage(
        @SerializedName("label") val label: String?,
        @SerializedName("username") val username: String?,
        @SerializedName("message") val message: String?
)


data class responseTrait(
        @SerializedName("action") val action: String?,
        @SerializedName("detail") val detail: DetailTrait?
)

data class DetailTrait(
        @SerializedName("label") val label: String?
)