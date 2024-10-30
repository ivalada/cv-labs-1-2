package ru.sfedu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import ru.sfedu.api.ImageAPI;
import ru.sfedu.util.ConfigUtils;

import static ru.sfedu.constant.Constants.*;

@DisplayName("Lab #6")
class Lab6Test {

    private final String srcPath = ConfigUtils.getConfigProperty(LAB6_SRC_PATH);
    private final String resultPath = ConfigUtils.getConfigProperty(LAB6_RESULT_PATH);
    private final String image1Name = ConfigUtils.getConfigProperty(LAB6_IMAGE1_NAME);

    private final ImageAPI imageAPI = new ImageAPI();
    private final Mat srcImage1 = imageAPI.loadImage(srcPath + image1Name);

    @Test
    void cannyBordersTest() {
        Mat resultImage = imageAPI.cannyBorders(srcImage1);
        imageAPI.saveImage(resultImage, resultPath + "canny_" + image1Name);
    }
}
