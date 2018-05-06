package com.example.naniti.velitos.fragments.ui.helper

import com.example.naniti.velitos.R
import com.example.naniti.velitos.challenges.ChallengeFragment
import com.example.naniti.velitos.home.HomeFragment
import com.example.naniti.velitos.rooms.RoomsFragment

enum class BottomNavigationPosition(val position: Int, val id: Int) {
    HOME(0, R.id.home),
    CHALLENGE(1, R.id.challenges),
    ROOMS(2, R.id.rooms)

}

fun findNavigationPositionById(id: Int): BottomNavigationPosition = when (id) {
    BottomNavigationPosition.HOME.id -> BottomNavigationPosition.HOME
    BottomNavigationPosition.CHALLENGE.id -> BottomNavigationPosition.CHALLENGE
    BottomNavigationPosition.ROOMS.id -> BottomNavigationPosition.ROOMS
    else -> BottomNavigationPosition.CHALLENGE
}

fun BottomNavigationPosition.createFragment() = when (this) {
    BottomNavigationPosition.HOME -> HomeFragment.newInstance()
    BottomNavigationPosition.CHALLENGE -> ChallengeFragment.newInstance()
    BottomNavigationPosition.ROOMS -> RoomsFragment.newInstance()

}
fun BottomNavigationPosition.getTag(): String = when (this) {
    BottomNavigationPosition.HOME -> HomeFragment.Tag
    BottomNavigationPosition.CHALLENGE -> ChallengeFragment.Tag
    BottomNavigationPosition.ROOMS -> RoomsFragment.Tag

}