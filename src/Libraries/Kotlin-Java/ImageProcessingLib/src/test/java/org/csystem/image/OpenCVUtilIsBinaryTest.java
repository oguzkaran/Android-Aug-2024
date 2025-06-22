package org.csystem.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class OpenCVUtilIsBinaryTest {
    @Test
    public void givenPath_whenImage_ThenReturnsTrue() throws Exception
    {
        var path = "images/red-kit-binary.jpeg";

        Assertions.assertTrue(OpenCVUtil.isBinary(path));
    }

    @Test
    public void givenPath_whenImage_ThenReturnsFalse() throws Exception
    {
        var path = "images/red-kit.jpeg";

        Assertions.assertFalse(OpenCVUtil.isBinary(path));
    }
}
