package me.saro.imw.kt

import me.saro.imw.ImageMagick.Companion.create
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File

@DisplayName("[Koltin] ImageMagick Image Info Test")
class ImageInfoTest {
    var root: File? = null

    @Test
    @DisplayName("image info: example.gif")
    @Throws(Exception::class)
    fun imageInfoGif() {
        val info = create().getImageInfo(File(root, "example.gif"))
        println(info)
        Assertions.assertTrue(info.isSuccess)
        val data = info.data!!
        Assertions.assertEquals(data.size, 31)
        val first = data[0]
        Assertions.assertEquals(first.width, 600)
        Assertions.assertEquals(first.height, 600)
        Assertions.assertEquals(first.format, "GIF")
    }

    @Test
    @DisplayName("image info: example.jpg")
    @Throws(Exception::class)
    fun imageInfoJpg() {
        val info = create().getImageInfo(File(root, "example.jpg"))
        println(info)
        Assertions.assertTrue(info.isSuccess)
        val first = info.data!![0]
        Assertions.assertEquals(first.width, 763)
        Assertions.assertEquals(first.height, 600)
        Assertions.assertEquals(first.format, "JPEG")
    }

    @Test
    @DisplayName("image info: example.png")
    @Throws(Exception::class)
    fun imageInfoPng() {
        val info = create().getImageInfo(File(root, "example.png"))
        println(info)
        Assertions.assertTrue(info.isSuccess)
        val first = info.data!![0]
        Assertions.assertEquals(first.width, 800)
        Assertions.assertEquals(first.height, 600)
        Assertions.assertEquals(first.format, "PNG")
    }

    @BeforeEach
    fun bindPath() {
        try {
            root = File(ImageInfoTest::class.java.classLoader.getResource("images").toURI())
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
