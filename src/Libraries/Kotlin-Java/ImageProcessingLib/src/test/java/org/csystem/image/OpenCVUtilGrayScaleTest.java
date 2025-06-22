package org.csystem.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

public class OpenCVUtilGrayScaleTest {
    @Test
    public void givenValues_whenSourceAndDestinationPaths_ThenMakeGrayScale() throws Exception
    {
        var path = "images/x.jpeg";
        var destPath = "images/test/red-kit-gs.jpeg";

        Files.createDirectories(Path.of("images/test"));
        OpenCVUtil.grayScale(path, destPath);
        Assertions.assertTrue(OpenCVUtil.isGrayScale(destPath));
        //Files.deleteIfExists(Path.of(destPath));
    }
}
