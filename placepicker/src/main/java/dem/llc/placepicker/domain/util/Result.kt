package dem.llc.placepicker.domain.util

sealed class Result {
    data class Success<T> (val data: T) : Result()
    data class Error(val msg: String) : Result()
}