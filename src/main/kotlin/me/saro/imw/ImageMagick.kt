package me.saro.imw

import me.saro.imw.ImageMagick.Companion.manual
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

    private val options get(): List<String> = optionMap.entries.flatMap {
        when (it.key) {
            "-resize" -> {
                listOf("-coalesce", it.key, it.value, "-layers", "Optimize")
            }
            "webpCompression" -> {
                listOf(
                    "-define", "webp:compression-level=9",
                    "-define", "webp:method=6",
                )
            }
            else -> {
                listOf(it.key, it.value)
            }
        }
    }.filter { it.isNotBlank() }

    fun resize(width: Int, height: Int): ImageMagick {
        if (width <= 0 || height <= 0) throw ImageMagickException("width and height must be greater than 0")
        optionMap["-resize"] = "${width}x${height}"
        return this
    }

    fun quality(quality: Int): ImageMagick {
        if (quality < 0 || quality > 100) throw ImageMagickException("quality must be between 0 and 100")
        optionMap["-quality"] = quality.toString()
        return this
    }

    fun webpCompression(): ImageMagick {
        optionMap["webpCompression"] = ""
        return this
    }

    fun convert(input: File, output: File): ImageMagickResult<String> {
        println("옵션")
        println(listOf(input.absolutePath, *options.toTypedArray(), output.absolutePath))
        return manual(binPath, listOf(input.absolutePath, *options.toTypedArray(), output.absolutePath))
    }

    

    fun getImageInfo(input: File): ImageMagickResult<List<ImageInfo>> =
        manual(binPath, listOf("identify", "-format", "%p %m %P\\n", input.absolutePath))
            .map { ImageInfo.create(it, listOf("frameIndex", "format", "size")) }
}