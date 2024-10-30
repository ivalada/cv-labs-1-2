package ru.sfedu;

import org.junit.jupiter.api.DisplayName;
import org.opencv.core.Mat;
import ru.sfedu.api.ImageAPI;
import ru.sfedu.util.ConfigUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static ru.sfedu.constant.Constants.*;

@DisplayName("Lab #4")
class Lab4Test {

    private final String srcPath = ConfigUtils.getConfigProperty(LAB4_SRC_PATH);
    private final String resultPath = ConfigUtils.getConfigProperty(LAB4_RESULT_PATH);
    private final String image1Name = ConfigUtils.getConfigProperty(LAB4_IMAGE1_NAME);
    private final String image2Name = ConfigUtils.getConfigProperty(LAB4_IMAGE2_NAME);
    private final String image3Name = ConfigUtils.getConfigProperty(LAB4_IMAGE3_NAME);
    private final String image4Name = ConfigUtils.getConfigProperty(LAB4_IMAGE4_NAME);

    private final ImageAPI imageAPI = new ImageAPI();
    private final Mat srcImage1 = imageAPI.loadImage(srcPath + image1Name);
    private final Mat srcImage2 = imageAPI.loadImage(srcPath + image2Name);
    private final Mat srcImage3 = imageAPI.loadImage(srcPath + image3Name);
    private final Mat srcImage4 = imageAPI.loadImage(srcPath + image4Name);

    @ParameterizedTest
    @ValueSource(ints = {3, 5, 7, 9, 13, 15})
    void filterTest(int kernelSize) {
        Mat resultImage;

        resultImage = imageAPI.avgBlur(srcImage1, kernelSize);
        imageAPI.saveImage(resultImage, resultPath + "blur_" + kernelSize + "_" + image1Name);
        resultImage = imageAPI.avgBlur(srcImage2, kernelSize);
        imageAPI.saveImage(resultImage, resultPath + "blur_" + kernelSize + "_" + image2Name);
        resultImage = imageAPI.avgBlur(srcImage3, kernelSize);
        imageAPI.saveImage(resultImage, resultPath + "blur_" + kernelSize + "_" + image3Name);

        resultImage = imageAPI.gaussianBlur(srcImage1, kernelSize);
        imageAPI.saveImage(resultImage, resultPath + "gaussianBlur_" + kernelSize + "_" + image1Name);
        resultImage = imageAPI.gaussianBlur(srcImage2, kernelSize);
        imageAPI.saveImage(resultImage, resultPath + "gaussianBlur_" + kernelSize + "_" + image2Name);
        resultImage = imageAPI.gaussianBlur(srcImage3, kernelSize);
        imageAPI.saveImage(resultImage, resultPath + "gaussianBlur_" + kernelSize + "_" + image3Name);

        resultImage = imageAPI.medianBlur(srcImage1, kernelSize);
        imageAPI.saveImage(resultImage, resultPath + "medianBlur_" + kernelSize + "_" + image1Name);
        resultImage = imageAPI.medianBlur(srcImage2, kernelSize);
        imageAPI.saveImage(resultImage, resultPath + "medianBlur_" + kernelSize + "_" + image2Name);
        resultImage = imageAPI.medianBlur(srcImage3, kernelSize);
        imageAPI.saveImage(resultImage, resultPath + "medianBlur_" + kernelSize + "_" + image3Name);

        resultImage = imageAPI.bilateralFilter(srcImage1, kernelSize);
        imageAPI.saveImage(resultImage, resultPath + "bilateralFilter_" + kernelSize + "_" + image1Name);
        resultImage = imageAPI.bilateralFilter(srcImage2, kernelSize);
        imageAPI.saveImage(resultImage, resultPath + "bilateralFilter_" + kernelSize + "_" + image2Name);
        resultImage = imageAPI.bilateralFilter(srcImage3, kernelSize);
        imageAPI.saveImage(resultImage, resultPath + "bilateralFilter_" + kernelSize + "_" + image3Name);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 5, 7, 9, 13, 15})
    void morphologyTest(int kernelSize) {
        Mat resultImage;

        resultImage = imageAPI.erode(srcImage4, kernelSize, Morph.CROSS);
        imageAPI.saveImage(resultImage, resultPath + "CROSS_" + kernelSize + "_" + image4Name);
        resultImage = imageAPI.erode(srcImage4, kernelSize, Morph.RECT);
        imageAPI.saveImage(resultImage, resultPath + "RECT_" + kernelSize + "_" + image4Name);
        resultImage = imageAPI.erode(srcImage4, kernelSize, Morph.ELLIPSE);
        imageAPI.saveImage(resultImage, resultPath + "ELLIPSE_" + kernelSize + "_" + image4Name);

        resultImage = imageAPI.dilate(srcImage4, kernelSize, Morph.CROSS);
        imageAPI.saveImage(resultImage, resultPath + "CROSS_" + kernelSize + "_" + image4Name);
        resultImage = imageAPI.dilate(srcImage4, kernelSize, Morph.RECT);
        imageAPI.saveImage(resultImage, resultPath + "RECT_" + kernelSize + "_" + image4Name);
        resultImage = imageAPI.dilate(srcImage4, kernelSize, Morph.ELLIPSE);
        imageAPI.saveImage(resultImage, resultPath + "ELLIPSE_" + kernelSize + "_" + image4Name);
    }
}
