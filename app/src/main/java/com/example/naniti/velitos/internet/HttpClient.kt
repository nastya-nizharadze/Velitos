package com.example.naniti.velitos.internet


import android.util.Log
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.gson.responseObject
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

    init {

        FuelManager.instance.basePath = path
        FuelManager.instance.baseHeaders = mapOf(Pair("Content-Type", "application/json"))
    }

    private fun <T : Any> getResult(res: Result<T, FuelError>): T? = when (res) {
        is Result.Success -> res.value
        is Result.Failure -> null
    }


    private fun setAuthToken(request: Request): Request {
        if (clientToken != null) request.headers += mutableMapOf(Pair(
                "Authorization",
                "JWT $clientToken!!"
        ))
        return request
    }

    //authentication path
    private val registerPath = "/auth/register/"
    private val tokenGetPath = "/auth/auth-jwt/"
    private val tokenRefreshPath = "/auth/auth-jwt-refresh/"
    private val tokenVerifyPath = "/auth/auth-jwt-verify/"
    private val authBySocialTokenPath = "/auth/auth-by-access-token/"
    private val authLogoutPath = "/auth/logout/"


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
        val (req, resp, res) = tokenGetPath.httpPost().body(gson.toJson(data))
                .responseObject(Token.Deserializer())
        getResult(res)
    }


    fun refreshClientToken(token: String): Deferred<Token?> = bg {
        val json = gson.toJson(mapOf(Pair("token", token)))
        val (_, _, res) = tokenRefreshPath.httpPost().responseObject(Token.Deserializer())
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

    private val usersSearchPath = "${versionApi}/users_search/"
    private val userInstancePath = "${versionApi}/users/"
    private val userProfileInstance = "${versionApi}/user_profiles/"

    fun usersSearch(list: List<Pair<String, String>>? = null) = bg {

        val (_, _, res) = setAuthToken(usersSearchPath.httpGet(list))
                .responseObject(UsersSearch.Deserializer())
        getResult(res)
    }

    fun getUsersInstance(url: String) = bg {
        val (_, _, res) = setAuthToken(url.httpGet())
                .responseObject(UsersInstance.Deserializer())
        getResult(res)
    }

    fun getUserProfileInstance(url: String) = bg {
        val (_, _, res) = setAuthToken(url.httpGet())
                .responseObject(UserProfileInstance.Deserializer())
        getResult(res)
    }




}




