package me.saro.imw.kt

import me.saro.imw.ImageMagick
import me.saro.imw.comm.ImageMagickResult
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File

@DisplayName("[Koltin] ImageMagick Image Convert Test")
class ImageConvertTest {
    var root: File? = null
    var tmp: File? = null

    @Test
    @DisplayName("image info: example.gif")
    @Throws(Exception::class)
    fun imageInfoGif() {
        var info: ImageMagickResult<String> =
            ImageMagick.create().convert(File(root, "example.gif"), File(tmp, "example-gif.webp"))
        println(info)
        Assertions.assertTrue(info.isSuccess)

        info = ImageMagick.create().resize(120, 120).webpCompression()
            .convert(File(root, "example.gif"), File(tmp, "example-resize-gif.webp"))
        println(info)
        Assertions.assertTrue(info.isSuccess)
    }

    @Test
    @DisplayName("image info: example.jpg")
    @Throws(Exception::class)
    fun imageInfoJpg() {
        var info: ImageMagickResult<String> =
            ImageMagick.create().convert(File(root, "example.jpg"), File(tmp, "example-jpg.webp"))
        println(info)
        Assertions.assertTrue(info.isSuccess)

        info = ImageMagick.create().resize(120, 120).webpCompression()
            .convert(File(root, "example.jpg"), File(tmp, "example-resize-jpg.webp"))
        println(info)
        Assertions.assertTrue(info.isSuccess)
    }

    @Test
    @DisplayName("image info: example.png")
    @Throws(Exception::class)
    fun imageInfoPng() {
        var info: ImageMagickResult<String> =
            ImageMagick.create().convert(File(root, "example.png"), File(tmp, "example-png.webp"))
        println(info)
        Assertions.assertTrue(info.isSuccess)

        info = ImageMagick.create().resize(120, 120).webpCompression()
            .convert(File(root, "example.png"), File(tmp, "example-resize-png.webp"))
        println(info)
        Assertions.assertTrue(info.isSuccess)
    }

    @BeforeEach
    fun bindPath() {
        try {
            root = File(ImageConvertTest::class.java.classLoader.getResource("images").toURI())
            File("tmp").also { tmp = it }.mkdirs()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
