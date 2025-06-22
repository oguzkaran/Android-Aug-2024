package org.csystem.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OpenCVUtilEqualsTest {
    @Test
    public void givenValues_whenGrayScaleImagesPath_ThenReturnsTrue() throws Exception
    {
        var path1 = "images/red-kit1.jpeg";
        var path2 = "images/red-kit2.jpeg";

        Assertions.assertTrue(OpenCVUtil.equals(path1, path2));
    }

    @Test
    public void givenValues_whenGrayScaleImagesPath_ThenReturnsFalse() throws Exception
    {
        var path1 = "images/red-kit1.jpeg";
        var path2 = "images/red-kit-gs.jpeg";

        Assertions.assertFalse(OpenCVUtil.equals(path1, path2));
    }
}
