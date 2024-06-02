package me.saro.imw

import me.saro.imw.comm.ImageMagickException
import me.saro.imw.comm.ImageMagickResult

class ImageMagick {
    companion object {
        @JvmStatic
        fun execute(binPath: String, options: List<String>): ImageMagickResult {
            try {
                val process = ProcessBuilder(binPath, *options.toTypedArray())
                    .redirectErrorStream(true)
                    .start()
                val message = process.inputStream.use { String(it.readAllBytes()) }.trim()
                return ImageMagickResult(process.waitFor(), message)
            } catch (e: Exception) {
                throw ImageMagickException(e)
            }
        }
    }
}