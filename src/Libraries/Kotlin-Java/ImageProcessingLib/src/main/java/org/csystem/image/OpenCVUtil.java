package org.csystem.image;

import nu.pattern.OpenCV;
import org.csystem.image.exception.OpenCVException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility class for image processing operations using OpenCV.
 * Provides methods for grayscale conversion, binary thresholding,
 * and checks for grayscale/binary images.
 * @author Oğuz Karan
 */
public final class OpenCVUtil {
    /**
     * The maximum pixel value for 8-bit images.
     */
    public static final int STANDARD_MAX_PIXEL_VALUE = 255;

    // Loads the OpenCV native library locally when the class is loaded.
    static {
        OpenCV.loadLocally();
    }

    /**
     * Checks if the file at the given path exists.
     *
     * @param path the file path to check
     * @throws OpenCVException if the file does not exist
     */
    private static void checkFileIfExistsThrowException(String path)
    {
        if (!Files.exists(Path.of(path)))
            throw new OpenCVException("\"%s\" not found".formatted(path));
    }

    // Private constructor to prevent instantiation.
    private OpenCVUtil()
    {
    }

    /**
     * Converts the source grayscale image to a binary image using the given threshold and max value,
     * and writes the result to the destination path.
     *
     * @param srcPath   the path to the source image
     * @param destPath  the path to save the binary image
     * @param threshold the threshold value
     * @param maxValue  the maximum value to use with the THRESH_BINARY operation
     * @throws OpenCVException if the source file does not exist
     */
    public static void binary(String srcPath, String destPath, int threshold, int maxValue)
    {
        checkFileIfExistsThrowException(srcPath);
        var srcMat = Imgcodecs.imread(srcPath, Imgcodecs.IMREAD_GRAYSCALE);
        var destMat = new Mat();

        Imgproc.threshold(srcMat, destMat, threshold, maxValue, Imgproc.THRESH_BINARY);
        Imgcodecs.imwrite(destPath, destMat);
    }

    /**
     * Converts the source grayscale image to a binary image using the given threshold and the default max value,
     * and writes the result to the destination path.
     *
     * @param srcPath   the path to the source image
     * @param destPath  the path to save the binary image
     * @param threshold the threshold value
     * @throws OpenCVException if the source file does not exist
     */
    public static void binary(String srcPath, String destPath, int threshold)
    {
        binary(srcPath, destPath, threshold, STANDARD_MAX_PIXEL_VALUE);
    }


    /**
     * Compares two images given by their file paths for equality.
     * The images are read from the specified paths and compared pixel by pixel.
     * Returns true if the images have the same size, type, and pixel values; false otherwise.
     *
     * @param src1 the path to the first image file
     * @param src2 the path to the second image file
     * @return true if the images are equal, false otherwise
     * @throws OpenCVException if the first file does not exist
     */
    public static boolean equals(String src1, String src2)
    {
        checkFileIfExistsThrowException(src1);
        var srcMat =  Imgcodecs.imread(src1);
        var destMat =  Imgcodecs.imread(src2);

        return equals(srcMat, destMat);
    }

    /**
     * Compares two OpenCV Mat images for equality.
     * The images must have the same size and type. The method computes the absolute difference
     * between the two images, converts the result to grayscale, and checks if all pixel values are zero.
     * Returns true if the images are identical; false otherwise.
     *
     * @param src1 the first image as a Mat
     * @param src2 the second image as a Mat
     * @return true if the images are equal, false otherwise
     */
    public static boolean equals(Mat src1, Mat src2)
    {
        if (!src1.size().equals(src2.size()) || src1.type() != src2.type())
            return false;

        var diff = new Mat();

        Core.absdiff(src1, src2, diff);
        var gray = new Mat();

        Imgproc.cvtColor(diff, gray, Imgproc.COLOR_BGR2GRAY);

        return Core.countNonZero(gray) == 0;
    }

    /**
     * Converts the source image to grayscale and writes the result to the destination path.
     *
     * @param srcPath  the path to the source image
     * @param destPath the path to save the grayscale image
     * @throws OpenCVException if the source file does not exist
     */
    public static void grayScale(String srcPath, String destPath)
    {
        checkFileIfExistsThrowException(srcPath);
        var srcMat = Imgcodecs.imread(srcPath);
        var destMat = grayScale(srcMat);
        Imgcodecs.imwrite(destPath, destMat);
    }

    /**
     * Converts the source image to grayscale and returns the result as a Mat.
     *
     * @param srcPath the path to the source image
     * @return the grayscale image as a Mat
     * @throws OpenCVException if the source file does not exist
     */
    public static Mat grayScale(String srcPath)
    {
        return grayScale(Imgcodecs.imread(srcPath));
    }

    /**
     * Converts the given Mat image to grayscale.
     *
     * @param srcMat the source image as a Mat
     * @return the grayscale image as a Mat
     */
    public static Mat grayScale(Mat srcMat)
    {
        var destMat = new Mat();

        Imgproc.cvtColor(srcMat, destMat, Imgproc.COLOR_BGR2GRAY);

        return destMat;
    }

    /**
     * Checks if the given Mat image is a binary image (only contains 0 and STANDARD_MAX_PIXEL_VALUE).
     *
     * @param mat the image as a Mat
     * @return true if the image is binary, false otherwise
     */
    public static boolean isBinary(Mat mat)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Checks if the image at the given path is a binary image.
     *
     * @param srcPath the path to the image
     * @return true if the image is binary, false otherwise
     * @throws OpenCVException if the source file does not exist
     */
    public static boolean isBinary(String srcPath)
    {
        checkFileIfExistsThrowException(srcPath);
        return isBinary(Imgcodecs.imread(srcPath, Imgcodecs.IMREAD_UNCHANGED));
    }

    /**
     * Checks if the image at the given path is grayscale.
     *
     * @param srcPath the path to the image
     * @return true if the image is grayscale, false otherwise
     * @throws OpenCVException if the source file does not exist
     */
    public static boolean isGrayScale(String srcPath)
    {
        checkFileIfExistsThrowException(srcPath);
        return isGrayScale(Imgcodecs.imread(srcPath, Imgcodecs.IMREAD_UNCHANGED));
    }

    /**
     * Checks if the given Mat image is grayscale.
     *
     * @param mat the image as a Mat
     * @return true if the image is grayscale, false otherwise
     */
    public static boolean isGrayScale(Mat mat)
    {
        return mat.channels() == 1;
    }

    /**
     * Rotates the source image by the specified angle and saves the result to the destination path.
     * The method reads the image from the given source path, computes the rotation matrix,
     * adjusts the transformation to keep the image centered, applies the rotation, and writes
     * the rotated image to the destination path.
     *
     * @param srcPath  the path to the source image file
     * @param destPath the path to save the rotated image
     * @param angle    the rotation angle in degrees (positive values mean counter-clockwise rotation)
     * @throws OpenCVException if the source file does not exist
     */
    public static void rotate(String srcPath, String destPath, double angle)
    {
        checkFileIfExistsThrowException(srcPath);
        var srcMat = Imgcodecs.imread(srcPath);
        var center = new Point(srcMat.cols() / 2., srcMat.rows() / 2.);
        var rotated = new Mat();
        var rotationMatrix = Imgproc.getRotationMatrix2D(center, angle, 1.0);
        var bbox = new RotatedRect(center, srcMat.size(), angle).boundingRect();

        rotationMatrix.put(0, 2, rotationMatrix.get(0, 2)[0] + bbox.width / 2.0 - center.x);
        rotationMatrix.put(1, 2, rotationMatrix.get(1, 2)[0] + bbox.height / 2.0 - center.y);
        Imgproc.warpAffine(srcMat, rotated, rotationMatrix, bbox.size());
        Imgcodecs.imwrite(destPath, rotated);
    }

    //...
}