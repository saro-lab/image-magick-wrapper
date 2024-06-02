package me.saro.imw.kt

import me.saro.imw.ImageMagick.Companion.create
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.io.File

@DisplayName("[Kotlin] ImageMagick Image Info Test")
class ImageInfoTest {
    var root: File = File("")

    @Test
    @DisplayName("image info: example.gif")
    @Throws(Exception::class)
    fun imageInfoGif() {
        val info = create().getImageInfo(File(root, "example.gif"))
        println(info)
        Assertions.assertTrue(info.isSuccess)
    }

    @Test
    @DisplayName("image info: example.jpg")
    @Throws(Exception::class)
    fun imageInfoJpg() {
        val info = create().getImageInfo(File(root, "example.jpg"))
        println(info)
        Assertions.assertTrue(info.isSuccess)
    }

    @Test
    @DisplayName("image info: example.png")
    @Throws(Exception::class)
    fun imageInfoPng() {
        val info = create().getImageInfo(File(root, "example.png"))
        println(info)
        Assertions.assertTrue(info.isSuccess)
    }

    @BeforeEach
    fun bindPath(): Unit {
        try {
            root = File(ImageInfoTest::class.java.classLoader.getResource("images").toURI())
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
