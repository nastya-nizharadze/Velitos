package  com.example.naniti.velitos.internet

import com.google.gson.annotations.SerializedName
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

// api endpoint: http://hserver.leningradskaya105.ru:6379/w1.0/users_search/
data class UsersSearch(
        @SerializedName("username") val username: String?,
        @SerializedName("email") val email: String?,
        @SerializedName("last_name") val lastName: String?,
        @SerializedName("first_name") val firstName: String?,
        @SerializedName("url") val url: String?
) {
    class Deserializer : ResponseDeserializable<Array<UsersSearch>> {
        override fun deserialize(content: String): Array<UsersSearch> =
                Gson().fromJson(content, Array<UsersSearch>::class.java)
    }
}

// api endpoint: http://hserver.leningradskaya105.ru:6379/w1.0/users/id/
data class UsersInstance(
        @SerializedName("email") val email: String?,
        @SerializedName("username") val username: String?,
        @SerializedName("url") val url: String?,
        @SerializedName("userprofile") val userprofile: String?
) {
    class Deserializer : ResponseDeserializable<UsersInstance> {
        override fun deserialize(content: String): UsersInstance =
                Gson().fromJson(content, UsersInstance::class.java)
    }
}


//api endpoint http://hserver.leningradskaya105.ru:6379/w1.0/user_profiles/4/
data class UserProfileInstance(
        @SerializedName("url") val url: String?,
        @SerializedName("birth_date") val birthDate: Any?,
        @SerializedName("image") val image: Any?,
        @SerializedName("gender") val gender: String?,
        @SerializedName("bio") val bio: String?,
        @SerializedName("user") val user: User?,
        @SerializedName("global_rating") val globalRating: Int?,
        @SerializedName("popularity") val popularity: Int?
) {
    class Deserializer : ResponseDeserializable<UserProfileInstance> {
        override fun deserialize(content: String): UserProfileInstance =
                Gson().fromJson(content, UserProfileInstance::class.java)
    }
}


//api endpoint http://hserver.leningradskaya105.ru:6379/w1.0/user_profiles/4/ - nested class
data class User(
        @SerializedName("username") val username: String?,
        @SerializedName("email") val email: String?,
        @SerializedName("last_name") val lastName: String?,
        @SerializedName("first_name") val firstName: String?
) {
    class Deserializer : ResponseDeserializable<User> {
        override fun deserialize(content: String): User =
                Gson().fromJson(content, User::class.java)
    }
}


//api endpoint http://hserver.leningradskaya105.ru:6379/w1.0/rooms_search/
data class RoomsSearch(
        @SerializedName("name") val name: String?,
        @SerializedName("category") val category: Any?,
        @SerializedName("url") val url: String?
) {
    class Deserializer : ResponseDeserializable<Array<RoomsSearch>> {
        override fun deserialize(content: String): Array<RoomsSearch> =
                Gson().fromJson(content, Array<RoomsSearch>::class.java)
    }
}

//api endpoint http://hserver.leningradskaya105.ru:6379/w1.0/rooms/1/
data class RoomsInstance(
        @SerializedName("name") val name: String?,
        @SerializedName("label") val label: String?,
        @SerializedName("url") val url: String?,
        @SerializedName("size") val size: Int?,
        @SerializedName("users") val users: List<UserProfileInstance?>?,
        @SerializedName("expiry") val expiry: String?,
        @SerializedName("category") val category: String?
) {
    class Deserializer : ResponseDeserializable<RoomsInstance> {
        override fun deserialize(content: String): RoomsInstance =
                Gson().fromJson(content, RoomsInstance::class.java)
    }

    class DeserializerArray : ResponseDeserializable<Array<RoomsInstance>> {
        override fun deserialize(content: String): Array<RoomsInstance> =
                Gson().fromJson(content, Array<RoomsInstance>::class.java)
    }
}

//api endpoint http://hserver.leningradskaya105.ru:6379/w1.0/category/2/
data class RoomCategoryInstance(
        @SerializedName("name") val name: String?,
        @SerializedName("rooms") val rooms: List<RoomsInstance?>?,
        @SerializedName("url") val url: String?,
        @SerializedName("description") val description: String?
) {
    class DeserializerInstance : ResponseDeserializable<RoomCategoryInstance> {
        override fun deserialize(content: String): RoomCategoryInstance =
                Gson().fromJson(content, RoomCategoryInstance::class.java)
    }

    class DeserializerArray : ResponseDeserializable<Array<RoomCategoryInstance>> {
        override fun deserialize(content: String): Array<RoomCategoryInstance> =
                Gson().fromJson(content, Array<RoomCategoryInstance>::class.java)
    }
}


//api endpoint http://hserver.leningradskaya105.ru:6379/w1.0/challenges_search/
//and instance
data class ChallengesSearch(
        @SerializedName("url") val url: String?,
        @SerializedName("category_challenge") val categoryChallenge: List<Any?>?, //aka list of strings
        @SerializedName("name") val name: String?,
        @SerializedName("pub_date") val pubDate: String?,
        @SerializedName("edit_date") val editDate: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("popularity") val popularity: Int?,
        @SerializedName("cost") val cost: Int?,
        @SerializedName("difficulty") val difficulty: Int?,
        @SerializedName("challenge_creator") val challengeCreator: String?,
        @SerializedName("plot_challenge") val plotChallenge: List<Any?>?
) {
    class DeserializerInstance : ResponseDeserializable<ChallengesSearch> {
        override fun deserialize(content: String): ChallengesSearch =
                Gson().fromJson(content, ChallengesSearch::class.java)
    }

    class DeserializerArray : ResponseDeserializable<Array<ChallengesSearch>> {
        override fun deserialize(content: String): Array<ChallengesSearch> =
                Gson().fromJson(content, Array<ChallengesSearch>::class.java)
    }
}


data class Token(
        @SerializedName("token") val token: String?
) {

    class Deserializer : ResponseDeserializable<Token> {
        override fun deserialize(content: String): Token? = Gson().fromJson(content, Token::class.java)
    }
}


data class MessageSend(
        @SerializedName("command") val command: String?,
        @SerializedName("message") val message: String?,
        @SerializedName("room_label") val roomLabel: String?
) {
    class Deserializer : ResponseDeserializable<MessageSend> {
        override fun deserialize(content: String): MessageSend? = Gson().fromJson(content, MessageSend::class.java)
    }

}


//for my rooms

data class UserProfile(
        @SerializedName("url") val url: String?,
        @SerializedName("image") val image: Any?,
        @SerializedName("user") val user: User?
) {
    class Deserializer : ResponseDeserializable<UserProfile> {
        override fun deserialize(content: String): UserProfile? = Gson().fromJson(content, UserProfile::class.java)
    }

    class DeserializerArray : ResponseDeserializable<Array<UserProfile>> {
        override fun deserialize(content: String): Array<UserProfile> = Gson().fromJson(content, Array<UserProfile>::class.java)
    }
}


data class Rooms(
        @SerializedName("name") val name: String?,
        @SerializedName("label") val label: String?,
        @SerializedName("url") val url: String?,
        @SerializedName("size") val size: Int?,
        @SerializedName("users") val users: List<UserProfile?>?,
        @SerializedName("expiry") val expiry: String?,
        @SerializedName("category") val category: String?,
        @SerializedName("messages") val messages: List<String?>?
) {
    class Deserializer : ResponseDeserializable<Array<Rooms>> {
        override fun deserialize(content: String): Array<Rooms>? = Gson().fromJson(content, Array<Rooms>::class.java)
    }
}

