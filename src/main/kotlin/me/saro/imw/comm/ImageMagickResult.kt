package me.saro.imw.comm

class ImageMagickResult(
    val exitCode: Int,
    val message: String,
) {
    val isSuccess: Boolean = exitCode == 0
}