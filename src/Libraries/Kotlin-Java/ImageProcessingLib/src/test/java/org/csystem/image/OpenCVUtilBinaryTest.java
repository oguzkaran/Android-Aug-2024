package org.csystem.image;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

@Disabled
public class OpenCVUtilBinaryTest {
    @Test
    public void givenValues_whenSourceAndDestinationPaths_ThenMakeBinary() throws Exception
    {
        var path = "images/red-kit-gs.jpeg";
        var destPath = "images/test/red-kit-binary.jpeg";

        Files.createDirectories(Path.of("images/test"));
        OpenCVUtil.binary(path, destPath, 128);

        //Files.deleteIfExists(Path.of(destPath));
    }
}
