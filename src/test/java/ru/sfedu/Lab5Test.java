package ru.sfedu;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import ru.sfedu.api.ImageAPI;
import ru.sfedu.util.ConfigUtils;

import static ru.sfedu.constant.Constants.*;

@DisplayName("Lab #5")
@Slf4j
class Lab5Test {

    private final String srcPath = ConfigUtils.getConfigProperty(LAB5_SRC_PATH);
    private final String resultPath = ConfigUtils.getConfigProperty(LAB5_RESULT_PATH);
    private final String image1Name = ConfigUtils.getConfigProperty(LAB5_IMAGE1_NAME);
    private final String image2Name = ConfigUtils.getConfigProperty(LAB5_IMAGE2_NAME);

    private final ImageAPI imageAPI = new ImageAPI();
    private final Mat srcImage1 = imageAPI.loadImage(srcPath + image1Name);
    private final Mat srcImage2 = imageAPI.loadImage(srcPath + image2Name);

    @Test
    void floodFillingSegmentationTest() {
        imageAPI.floodFill(srcImage1, 0, 0, 80, 100, null);
        imageAPI.saveImage(srcImage1, resultPath + "flood_fill_" + image1Name);
    }

    @Test
    void pyramidSegmentationTest() {
        Mat resultMask;

        resultMask = imageAPI.pyramidUpSegmentation(srcImage1, 10000);
        imageAPI.saveImage(resultMask, resultPath + "pyramid_up_10_" + image1Name);

        resultMask = imageAPI.pyramidDownSegmentation(srcImage1, 10000);
        imageAPI.saveImage(resultMask, resultPath + "pyramid_down_10_" + image1Name);

        Core.subtract(srcImage1, srcImage1, resultMask);
        imageAPI.saveImage(srcImage1, resultPath + "extract_" + image1Name);
    }

    @Test
    void countRectanglesTest() {
        int numberOfRectangles = imageAPI.countRectangleShapes(srcImage2, 193, 371);
        log.info("number of rectangles = {}", numberOfRectangles);
    }
}
