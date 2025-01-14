package com.example.baseproject

fun String.getLastAsInt(): Int {
    return this.last().toString().toInt()
}