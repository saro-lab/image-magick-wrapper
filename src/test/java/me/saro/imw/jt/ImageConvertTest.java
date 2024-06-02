package me.saro.imw.jt;

import me.saro.imw.ImageMagick;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("[Java] ImageMagick Image Convert Test")
public class ImageConvertTest {

    File root;
    File tmp;

    @Test
    @DisplayName("image info: example.gif")
    public void imageInfoGif() throws Exception {
        var info = ImageMagick.create().convert(new File(root,"example.gif"), new File(tmp, "example-gif.webp"));
        System.out.println(info);
        assertTrue(info.isSuccess());

        info = ImageMagick.create().resize(120, 120).webpCompression().convert(new File(root,"example.gif"), new File(tmp, "example-resize-gif.webp"));
        System.out.println(info);
        assertTrue(info.isSuccess());
    }

    @Test
    @DisplayName("image info: example.jpg")
    public void imageInfoJpg() throws Exception {
        var info = ImageMagick.create().convert(new File(root,"example.jpg"), new File(tmp, "example-jpg.webp"));
        System.out.println(info);
        assertTrue(info.isSuccess());

        info = ImageMagick.create().resize(120, 120).webpCompression().convert(new File(root,"example.jpg"), new File(tmp, "example-resize-jpg.webp"));
        System.out.println(info);
        assertTrue(info.isSuccess());
    }

    @Test
    @DisplayName("image info: example.png")
    public void imageInfoPng() throws Exception {
        var info = ImageMagick.create().convert(new File(root,"example.png"), new File(tmp, "example-png.webp"));
        System.out.println(info);
        assertTrue(info.isSuccess());

        info = ImageMagick.create().resize(120, 120).webpCompression().convert(new File(root,"example.png"), new File(tmp, "example-resize-png.webp"));
        System.out.println(info);
        assertTrue(info.isSuccess());
    }

    @BeforeEach
    public void bindPath() {
        try {
            root = new File(ImageConvertTest.class.getClassLoader().getResource("images").toURI());
            (tmp = new File("tmp")).mkdirs();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
