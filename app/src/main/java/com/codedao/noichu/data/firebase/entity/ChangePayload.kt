package com.codedao.noichu.data.firebase.entity

data class ChangePayload<out T>(var type: Int,
                                val model: T) {

    companion object {
        const val EMPTY = 0
        const val ADDED = 1
        const val REMOVED = 2
        const val MOVED = 3
        const val CHANGED = 4
    }
}