package ru.sfedu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import ru.sfedu.api.ImageAPI;
import ru.sfedu.util.ConfigUtils;
import ru.sfedu.util.ImageUtils;

import javax.swing.*;

import static ru.sfedu.constant.Constants.*;

@DisplayName("Lab #2")
class Lab2Test {

    @Test
    void resetChannelAndShowImageAndSaveImageTest() {
        String srcPath = ConfigUtils.getConfigProperty(LAB2_SRC_PATH);
        String resultPath = ConfigUtils.getConfigProperty(LAB2_RESULT_PATH);
        String imageName = ConfigUtils.getConfigProperty(LAB2_IMAGE_NAME);

        ImageAPI imageAPI = new ImageAPI();
        Mat srcImage = imageAPI.loadImage(srcPath + imageName);

        JFrame beforeImageFrame = ImageUtils.imageToJFrame(srcImage, "before");

        Mat resultImage = imageAPI.resetChannel(srcImage, 2);
        imageAPI.saveImage(resultImage, resultPath + "reset_channel_" + imageName);

        JFrame afterImageFrame = ImageUtils.imageToJFrame(resultImage, "after");

        ImageUtils.sleepWhileAnyShowing(beforeImageFrame, afterImageFrame);
    }
}
