package me.saro.webp

import java.io.File

class Webp(
    private val installBinPath: String,
    val gif2webpBinPath: String = File(installBinPath,"gif2webp").absolutePath,
    val cWebpBinPath: String = File(installBinPath,"cwebp").absolutePath
) {
    constructor() : this("./tmp")

    init {
        WebpInstaller.install(installBinPath)
    }

    fun toWebp(options: WebpOptions) {
        if (options.useCWebpOptionInGif) {
            val twoPassOptions = options.getTwoPassOptions()
            toWebp(gif2webpBinPath, twoPassOptions[0].toCommand())
            toWebp(cWebpBinPath, twoPassOptions[1].toCommand())
            File(twoPassOptions[0].toPath).delete()
        } else {
            toWebp(if (options.gif) gif2webpBinPath else cWebpBinPath, options.toCommand())
        }
    }

    fun toWebp(binPath: String, options: List<String>) {

        println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        println("convert webp: $binPath ${options.joinToString(" ")}")
        val process = ProcessBuilder(binPath, *options.toTypedArray())
            .redirectErrorStream(true)
            .start()
        val message = process.inputStream.use { String(it.readAllBytes()) }.trim()
        if (process.waitFor() != 0) {
            throw WebpException("failed to convert webp: \n$message")
        }
    }
}
