package me.saro.imw

import me.saro.imw.comm.ImageMagickException
import me.saro.imw.comm.ImageMagickResult
import me.saro.imw.model.ImageInfo
import java.io.File

class ImageMagick private constructor(
    private val binPath: String,
    private val optionMap: MutableMap<String, String> = mutableMapOf(),
) {
    companion object {
        @JvmStatic
        fun create(binPath: String): ImageMagick {
            return ImageMagick(binPath)
        }

        @JvmStatic
        fun create(): ImageMagick = create("magick")

        @JvmStatic
        fun manual(binPath: String, options: List<String>): ImageMagickResult<String> =
            try {
                val process = ProcessBuilder(binPath, *options.toTypedArray())
                    .redirectErrorStream(true)
                    .start()
                val output = process.inputStream.bufferedReader().readText();
                ImageMagickResult.create(process.waitFor(), output)
            } catch (e: Exception) {
                throw ImageMagickException(e)
            }
    }

    private val options: List<String> = optionMap.entries.flatMap { listOf(it.key, it.value) }

    fun resize(width: Int, height: Int): ImageMagick {
        if (width <= 0 || height <= 0) throw ImageMagickException("width and height must be greater than 0")
        optionMap["-resize"] = "${width}x${height}";
        return this
    }

    fun execute(input: File, output: File) {
        manual(binPath, listOf(input.absolutePath, *options.toTypedArray(), output.absolutePath))
    }

    fun getImageInfo(input: File): ImageMagickResult<List<ImageInfo>> =
        manual(binPath, listOf("identify", "-format", "%p %m %P %z\\n", input.absolutePath))
            .map { ImageInfo.create(it, listOf("frameIndex", "format", "size", "depth")) }
}