package me.saro.webp.jt;

import me.saro.webp.Webp;
import me.saro.webp.WebpOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class ToWebpTest {
    Webp webp = new Webp();

    @Test
    public void gif() {
        var from = "src/test/resources/other/example.gif";

        Assertions.assertDoesNotThrow(() -> webp.toWebp(new WebpOptions(from, "tmp/out/example-normal.webp")));
        Assertions.assertDoesNotThrow(() -> webp.toWebp(new WebpOptions(from, "tmp/out/example-min.webp").compression(6)));
        //Assertions.assertDoesNotThrow(() -> webp.toWebp(new WebpOptions(from, "tmp/out/example-small.webp").resize(100, 100)));
    }
}
