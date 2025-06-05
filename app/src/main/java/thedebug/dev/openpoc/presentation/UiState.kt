package thedebug.dev.openpoc.presentation


sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String? = "Unexpected error") : UiState<Nothing>()

    inline fun <R> map(crossinline mapper: T.() -> R): UiState<R> {
        return when (this) {
            is Loading -> Loading
            is Success -> Success(mapper(this.data))
            is Error -> Error(message)
        }
    }
}