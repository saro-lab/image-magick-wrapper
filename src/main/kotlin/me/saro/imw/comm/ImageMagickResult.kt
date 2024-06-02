package me.saro.imw.comm

data class ImageMagickResult<T>(
    val exitCode: Int,
    val data: T? = null,
) {
    companion object {
        fun <String> create(exitCode: Int, data: String?): ImageMagickResult<String> =
            ImageMagickResult(exitCode, data)
    }

    val isSuccess: Boolean = exitCode == 0

    fun <R> map(mapper: (T) -> R): ImageMagickResult<R> =
        ImageMagickResult(exitCode, data?.let(mapper))
}
