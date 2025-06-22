package org.csystem.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

@Disabled
public class OpenCVUtilRotateTest {
    @Test
    public void givenValues_whenSourceAndDestinationPaths_ThenRotate90Degrees() throws Exception
    {
        var path = "images/red-kit.jpeg";
        var destPath = "images/test/red-kit-rotate-90.jpeg";
        var degree = 90.;

        Files.createDirectories(Path.of("images/test"));
        OpenCVUtil.rotate(path, destPath, degree);
        Assertions.assertFalse(OpenCVUtil.isGrayScale(destPath));
        //Files.deleteIfExists(Path.of(destPath));
    }
}
