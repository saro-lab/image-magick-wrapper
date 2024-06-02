package me.saro.imw

import me.saro.imw.comm.ImageMagickException
import me.saro.imw.comm.ImageMagickResult
import java.io.File

class ImageMagick private constructor(
    private val binPath: String,
    private val input: File,
) {
    companion object {
        @JvmStatic
        fun create(binPath: String, input: File): ImageMagick {
            return ImageMagick(binPath, input)
        }

        @JvmStatic
        fun create(input: File): ImageMagick = create("magick", input)

        @JvmStatic
        fun manual(binPath: String, options: List<String>): ImageMagickResult =
            try {
                val process = ProcessBuilder(binPath, *options.toTypedArray())
                    .redirectErrorStream(true)
                    .start()
                val message = process.inputStream.use { String(it.readAllBytes()) }.trim()
                ImageMagickResult(process.waitFor(), message)
            } catch (e: Exception) {
                throw ImageMagickException(e)
            }
    }
}