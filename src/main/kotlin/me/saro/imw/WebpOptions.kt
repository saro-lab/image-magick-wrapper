package me.saro.imw

import me.saro.imw.comm.ImageMagickException

class WebpOptions(
    val fromPath: String,
    val toPath: String,
) {
    private val options = mutableMapOf<String, String>()
    private var format: String = ""
    private var useCWebpOption = false

    val gif: Boolean get() {
        if (format.isBlank()) {
            format(fromPath.substring(fromPath.lastIndexOf(".") + 1))
        }
        return format == "gif"
    }

    val useCWebpOptionInGif: Boolean get() = gif && useCWebpOption

    fun format(format: String): WebpOptions {
        if (format.lowercase() !in setOf("gif", "png", "jpg", "jpeg", "tiff", "webp")) {
            throw ImageMagickException("not supported format: $format\nhttps://developers.google.com/speed/webp/docs/cwebp")
        }
        this.format = format.lowercase()
        return this
    }

    fun compression(compression: Int): WebpOptions {
        compression.takeIf { it in 0..6 } ?: throw ImageMagickException("compression must be 0 to 6 (0=fast, 6=slowest)")
        options["-m"] = compression.toString()
        return this
    }

    fun quality(quality: Int): WebpOptions {
        quality.takeIf { it in 0..100 } ?: throw ImageMagickException("quality must be 0 to 100")
        options["-q"] = quality.toString()
        return this
    }

    fun resize(width: Int, height: Int): WebpOptions {
        useCWebpOption = true
        options["-resize"] = "$width $height"
        return this
    }

    fun crop(x: Int, y: Int, width: Int, height: Int): WebpOptions {
        useCWebpOption = true
        options["-crop"] = "$x $y $width $height"
        return this
    }

    fun toCommand(): List<String> {
        val list = mutableListOf<String>()
        options.forEach { (k, v) -> list.add(k); list.add(v) }
        list.add(fromPath)
        list.add("-o")
        list.add(toPath)
        return list
    }

    // gif -> webp -(resize, crop...)-> webp
    fun getTwoPassOptions(): List<WebpOptions> {
        if (!useCWebpOptionInGif) {
            throw ImageMagickException("not supported options: $this")
        }
        val midPath = "$toPath.me.saro.webp.2pass.temp.webp"
        return listOf(
            WebpOptions(fromPath, midPath).format("webp"),
            WebpOptions(midPath, toPath).format("webp").also { it.options.putAll(options) }
        )
    }
}
