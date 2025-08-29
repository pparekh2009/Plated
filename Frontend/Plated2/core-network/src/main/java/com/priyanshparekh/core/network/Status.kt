package com.priyanshparekh.core.network

sealed class Status<T> {

    class SUCCESS<T>(val data: T): Status<T>()
    class ERROR<T>(val message: String): Status<T>()
    class LOADING<T>(): Status<T>()

}