package br.com.movieapp.framework.util

sealed class ResultData<out T> {
    object Loading : ResultData<Nothing>()
    data class Success<out T>(val data: T?) : ResultData<T>()
    data class Failure(val data: Exception?) : ResultData<Nothing>()
}