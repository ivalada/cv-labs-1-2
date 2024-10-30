package ru.sfedu.util;

import lombok.SneakyThrows;
import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Arrays;

public class ImageUtils {

    public static byte[] convertToByteArray(Mat imageMatrix) {
        int totalBytes = (int) (imageMatrix.total() * imageMatrix.elemSize());
        byte[] buffer = new byte[totalBytes];
        imageMatrix.get(0, 0, buffer);
        return buffer;
    }

    public static JFrame imageToJFrame(Mat image, String title) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( image.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = image.channels() * image.cols()*  image.rows();
        byte [] buffer = new byte[bufferSize];
        image.get(0,0, buffer);

        BufferedImage buffImage = new BufferedImage(image.cols(),image.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) buffImage.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, bufferSize);

        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(buffImage.getWidth(null) + 50, buffImage.getHeight(null) + 50);
        JLabel lbl = new JLabel();
        ImageIcon icon = new ImageIcon(buffImage);
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setTitle(title);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        return frame;
    }

    @SneakyThrows
    public static void sleepWhileAnyShowing(JFrame ...images) {
        boolean anyShowing = Arrays.stream(images).anyMatch(JFrame::isShowing);
        while ( anyShowing ) {
            Thread.sleep(1000);
            anyShowing = Arrays.stream(images).anyMatch(JFrame::isShowing);
        }
    }
}
