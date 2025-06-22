package org.csystem.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class OpenCVUtilIsGrayScaleTest {
    @Test
    public void givenPath_whenGrayScaleImage_ThenReturnsTrue() throws Exception
    {
        var path = "images/red-kit-gs.jpeg";

        Assertions.assertTrue(OpenCVUtil.isGrayScale(path));
    }

    @Test
    public void givenPath_whenGrayScaleImage_ThenReturnsFalse() throws Exception
    {
        var path = "images/red-kit.jpeg";

        Assertions.assertFalse(OpenCVUtil.isGrayScale(path));
    }
}
