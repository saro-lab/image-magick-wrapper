package me.saro.imw.jt;

import me.saro.imw.ImageMagick;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("[Java] ImageMagick Image Info Test")
public class ImageInfoTest {

    File root;

    @Test
    @DisplayName("image info: example.gif")
    public void imageInfoGif() throws Exception {
        var info = ImageMagick.create()
                .getImageInfo(new File(root,"example.gif"));
        System.out.println(info);
        assertTrue(info.isSuccess());
        var data = info.getData();
        assertEquals(data.size(), 31);
        var first = data.get(0);
        assertEquals(first.getWidth(), 600);
        assertEquals(first.getHeight(), 600);
        assertEquals(first.getFormat(), "GIF");
    }

    @Test
    @DisplayName("image info: example.jpg")
    public void imageInfoJpg() throws Exception {
        var info = ImageMagick.create()
                .getImageInfo(new File(root,"example.jpg"));
        System.out.println(info);
        assertTrue(info.isSuccess());
        var first = info.getData().get(0);
        assertEquals(first.getWidth(), 763);
        assertEquals(first.getHeight(), 600);
        assertEquals(first.getFormat(), "JPEG");
    }

    @Test
    @DisplayName("image info: example.png")
    public void imageInfoPng() throws Exception {
        var info = ImageMagick.create()
                .getImageInfo(new File(root,"example.png"));
        System.out.println(info);
        assertTrue(info.isSuccess());
        var first = info.getData().get(0);
        assertEquals(first.getWidth(), 800);
        assertEquals(first.getHeight(), 600);
        assertEquals(first.getFormat(), "PNG");
    }

    @BeforeEach
    public void bindPath() {
        try {
            root = new File(ImageInfoTest.class.getClassLoader().getResource("images").toURI());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
