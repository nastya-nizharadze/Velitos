package com.example.naniti.velitos.internet


import android.util.Log
import com.example.naniti.velitos.rooms.myRooms.Room
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result

import com.google.gson.*
import kotlinx.coroutines.experimental.Deferred
import org.jetbrains.anko.coroutines.experimental.bg

interface HttpClient

/****
 *
 *
 */
class LeningradskayaClient(path: String) : HttpClient {

    private var gson = Gson()
    private var versionApi = "/w1.0"

    var clientToken: String? = null

    //authentication path
    private val registerPath = "/auth/register/"
    private val tokenGetPath = "/auth/auth-jwt/"
    private val tokenRefreshPath = "/auth/auth-jwt-refresh/"
    private val tokenVerifyPath = "/auth/auth-jwt-verify/"
    private val authBySocialTokenPath = "/auth/auth-by-access-token/"
    private val authLogoutPath = "/auth/logout/"

    // users path
    private val usersSearchPath = "${versionApi}/users_search/"
    private val userInstancePath = "${versionApi}/users/"
    private val userProfileInstance = "${versionApi}/user_profiles/"
    private val getProfileInstancePath = "${versionApi}/get_profile/"

    // rooms path and category
    private val roomsSearchPath = "${versionApi}/rooms_search/"
    private val roomInstancePath = "${versionApi}/rooms/"
    private val roomCategoryInstancePath = "${versionApi}/category/"
    private val myRoomsPath = "${versionApi}/get_user_rooms/"

    //challange path and category

    private val challengesSearchPath = "${versionApi}/challenges_search/"


    init {

        FuelManager.instance.basePath = path
        FuelManager.instance.baseHeaders = mapOf(Pair("Content-Type", "application/json"))
    }

    private fun <T : Any> getResult(res: Result<T, FuelError>): T? = when (res) {
        is Result.Success -> res.value
        is Result.Failure -> null
    }

    private fun setAuthHeader(request: Request): Request {
        if (clientToken != null) request.headers += mutableMapOf(Pair(
                "Authorization",
                "JWT $clientToken!!"
        ))
        return request
    }


    fun registerClient(username: String, password: String) = bg {
        val data = mutableMapOf(
                Pair("username", username),
                Pair("password", password))
        val (_, _, res) = registerPath.httpPost().body(gson.toJson(data))
                .responseObject(Token.Deserializer())
        getResult(res)
    }

    fun getClientToken(username: String, password: String): Deferred<Token?> = bg {
        val data = mutableMapOf<String, String>(
                Pair("username", username),
                Pair("password", password))
        val (_, _, res) = tokenGetPath.httpPost().body(gson.toJson(data))
                .responseObject(Token.Deserializer())
        getResult(res)
    }

    fun refreshClientToken(token: String): Deferred<Token?> = bg {
        val json = gson.toJson(mapOf(Pair("token", token)))
        val (_, _, res) = tokenRefreshPath.httpPost().body(json).responseObject(Token.Deserializer())
        getResult(res)
    }


    fun verifyClientToken(token: String) = bg {
        val json = gson.toJson(mapOf(Pair("token", token)))
        val (_, _, res) = tokenVerifyPath.httpPost().body(json).responseObject(Token.Deserializer())
        getResult(res)
    }

    fun authClientBySocialToken(token: String, backend: String) = bg {
        val data = gson.toJson(mapOf(Pair("token", token)))
        val (_, _, res) = ("$authBySocialTokenPath$backend/").httpPost().body(data)
                .responseObject(Token.Deserializer())
        getResult(res)
    }

    fun logoutUser() {
        TODO()
    }

    fun getUsersSearch(list: List<Pair<String, String>>? = null) = bg {

        val (_, _, res) = setAuthHeader(usersSearchPath.httpGet(list))
                .responseObject(UsersSearch.Deserializer())
        getResult(res)
    }

    fun getUsersInstance(url: String) = bg {
        val (_, _, res) = setAuthHeader(url.httpGet())
                .responseObject(UsersInstance.Deserializer())
        getResult(res)
    }

    fun getUserProfileInstance(url: String) = bg {
        val (_, _, res) = setAuthHeader(url.httpGet())
                .responseObject(UserProfileInstance.Deserializer())
        getResult(res)
    }


    fun getRoomsSearch(list: List<Pair<String, String>>? = null) = bg {
        val (_, _, res) = setAuthHeader(roomsSearchPath.httpGet(list))
                .responseObject(RoomsSearch.Deserializer())
        getResult(res)
    }

    fun getRoomsInstance(url: String) = bg {
        val (_, _, res) = setAuthHeader(url.httpGet())
                .responseObject(RoomsInstance.Deserializer())
        getResult(res)
    }

    fun getRoomCategoryInstance(url: String) = bg {
        val (_, _, res) = setAuthHeader(url.httpGet())
                .responseObject(RoomCategoryInstance.DeserializerInstance())
        getResult(res)
    }

    fun getRoomCategories() = bg {
        val (_, _, res) = setAuthHeader(roomCategoryInstancePath.httpGet())
                .responseObject(RoomCategoryInstance.DeserializerArray())
        getResult(res)
    }


    fun getChallengesSearch(list: List<Pair<String, String>>? = null) = bg {
        val (_, _, res) = setAuthHeader(challengesSearchPath.httpGet(list))
                .responseObject(ChallengesSearch.DeserializerArray())
        getResult(res)
    }

    fun getChallengeInstance(url: String) = bg {
        val (_, _, res) = setAuthHeader(url.httpGet())
                .responseObject(ChallengesSearch.DeserializerInstance())
        getResult(res)
    }

    fun getProfileInstance(username: String) = bg {
        val path = setAuthHeader(("$getProfileInstancePath$username").httpGet())
                .responseJson().second.url.path!!
        val (_, _, res) = setAuthHeader(path.httpGet())
                .responseObject(UserProfileInstance.Deserializer())
        getResult(res)
    }


    fun getUserRooms() = bg {
        val (_, _, res) = setAuthHeader(myRoomsPath.httpGet()).responseObject(Rooms.Deserializer())
        getResult(res)
    }


}




