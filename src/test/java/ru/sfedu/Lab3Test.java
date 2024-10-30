package ru.sfedu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import ru.sfedu.api.ImageAPI;
import ru.sfedu.util.ConfigUtils;

import javax.swing.*;

import static ru.sfedu.constant.Constants.*;

@DisplayName("Lab #3")
class Lab3Test {

    private final String srcPath = ConfigUtils.getConfigProperty(LAB3_SRC_PATH);
    private final String resultPath = ConfigUtils.getConfigProperty(LAB3_RESULT_PATH);
    private final String image1Name = ConfigUtils.getConfigProperty(LAB3_IMAGE1_NAME);
    private final String image2Name = ConfigUtils.getConfigProperty(LAB3_IMAGE2_NAME);
    private final String image3Name = ConfigUtils.getConfigProperty(LAB3_IMAGE3_NAME);

    private final ImageAPI imageAPI = new ImageAPI();
    private final Mat srcImage1 = imageAPI.loadImage(srcPath + image1Name);
    private final Mat srcImage2 = imageAPI.loadImage(srcPath + image2Name);
    private final Mat srcImage3 = imageAPI.loadImage(srcPath + image3Name);

    @Test
    void sobelTransformTest() {
        Mat resultX = new Mat();
        Mat resultY = new Mat();

        imageAPI.sobelTransform(srcImage2, resultX, resultY);
        imageAPI.saveImage(resultX, resultPath + "sobel_x_" + image2Name);
        imageAPI.saveImage(resultY, resultPath + "sobel_y_" + image2Name);

        imageAPI.sobelTransform(srcImage3, resultX, resultY);
        imageAPI.saveImage(resultX, resultPath + "sobel_x_" + image3Name);
        imageAPI.saveImage(resultY, resultPath + "sobel_y_" + image3Name);
    }

    @Test
    void laplaceTransform() {
        Mat resultImage;

        resultImage = imageAPI.laplaceTransform(srcImage2);
        imageAPI.saveImage(resultImage, resultPath + "laplace_" + image2Name);

        resultImage = imageAPI.laplaceTransform(srcImage3);
        imageAPI.saveImage(resultImage, resultPath + "laplace_" + image3Name);
    }

    @Test
    void imageFlipTest() {
        //JFrame beforeImageFrame = imageAPI.getImageFrame(srcImage1, "before");

        Mat resultImage;

        resultImage = imageAPI.flipH(srcImage1);
        imageAPI.saveImage(resultImage, resultPath + "flipH_" + image1Name);

        resultImage = imageAPI.flipV(srcImage1);
        imageAPI.saveImage(resultImage, resultPath + "flipV_" + image1Name);

        resultImage = imageAPI.flipHV(srcImage1);
        imageAPI.saveImage(resultImage, resultPath + "flipHV_" + image1Name);

        //JFrame afterImageFrame = imageAPI.getImageFrame(resultImage, "after");
    }

    @Test
    void imageConcatTest() {
        //JFrame beforeImageFrame = imageAPI.getImageFrame(srcImage1, "before");

        Mat resultImage;

        resultImage = imageAPI.concatH(srcImage1, srcImage1, srcImage1);
        imageAPI.saveImage(resultImage, resultPath + "concatH_" + image1Name);

        resultImage = imageAPI.concatV(srcImage1, srcImage1, srcImage1);
        imageAPI.saveImage(resultImage, resultPath + "concatV_" + image1Name);

        //JFrame afterImageFrame = imageAPI.getImageFrame(resultImage, "after");
    }

    @Test
    void imageRepeatTest() {
        //JFrame beforeImageFrame = imageAPI.getImageFrame(srcImage1, "before");

        Mat resultImage;

        resultImage = imageAPI.repeatH(srcImage1, 3);
        imageAPI.saveImage(resultImage, resultPath + "repeatH_" + image1Name);

        resultImage = imageAPI.repeatV(srcImage1, 3);
        imageAPI.saveImage(resultImage, resultPath + "repeatV_" + image1Name);

        resultImage = imageAPI.repeatHV(srcImage1, 3, 3);
        imageAPI.saveImage(resultImage, resultPath + "repeatHV_" + image1Name);

        //JFrame afterImageFrame = imageAPI.getImageFrame(resultImage, "after");
    }

    @Test
    void imageResizeTest() {
        //JFrame beforeImageFrame = imageAPI.getImageFrame(srcImage1, "before");

        Mat resultImage;

        resultImage = imageAPI.resize(srcImage1, 100, 100);
        imageAPI.saveImage(resultImage, resultPath + "resize_" + image1Name);

        //JFrame afterImageFrame = imageAPI.getImageFrame(resultImage, "after");
    }

    @Test
    void imageRotateTest() {
        //JFrame beforeImageFrame = imageAPI.getImageFrame(srcImage1, "before");

        Mat resultImage;

        resultImage = imageAPI.rotate(srcImage1, 45, true);
        imageAPI.saveImage(resultImage, resultPath + "rotate_crop_" + image1Name);

        resultImage = imageAPI.rotate(srcImage1, -45, false);
        imageAPI.saveImage(resultImage, resultPath + "rotate_no_crop_" + image1Name);

        //JFrame afterImageFrame = imageAPI.getImageFrame(resultImage, "after");
    }

    @Test
    void imageShiftTest() {
        //JFrame beforeImageFrame = imageAPI.getImageFrame(srcImage1, "before");

        Mat resultImage;

        resultImage = imageAPI.shiftH(srcImage1, 100);
        imageAPI.saveImage(resultImage, resultPath + "shiftH_" + image1Name);

        resultImage = imageAPI.shiftV(srcImage1, -100);
        imageAPI.saveImage(resultImage, resultPath + "shiftV_" + image1Name);

        resultImage = imageAPI.shiftHV(srcImage1, 100, 100);
        imageAPI.saveImage(resultImage, resultPath + "shiftHV_" + image1Name);

        //JFrame afterImageFrame = imageAPI.getImageFrame(resultImage, "after");
    }

    @Test
    void warpPerspectiveTest() {
        //JFrame beforeImageFrame = imageAPI.getImageFrame(srcImage1, "before");

        Mat resultImage;

        resultImage = imageAPI.warpPerspective(srcImage1);
        imageAPI.saveImage(resultImage, resultPath + "warp_perspective" + image1Name);

        //JFrame afterImageFrame = imageAPI.getImageFrame(resultImage, "after");
    }
}
