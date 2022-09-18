package com.aaron.spique.core.extensions

fun <T> MutableList<T>.swap(from: Int, to: Int) {
    val tmp = this[from]
    this[from] = this[to]
    this[to] = tmp
}