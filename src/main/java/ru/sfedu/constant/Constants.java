package ru.sfedu.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.opencv.imgproc.Imgproc;

public class Constants {

    /* PROPERTIES NAMES */
    public static final String OS_NAME_SYSTEM_PROPERTY_NAME = "os.name";
    public static final String OPENCV_LINUX_LIB_PATH_NAME = "opencv.lib.linux.path";

    public static final String LAB2_SRC_PATH = "lab2.src.path";
    public static final String LAB2_RESULT_PATH = "lab2.result.path";
    public static final String LAB2_IMAGE_NAME = "lab2.image.name";

    public static final String LAB3_SRC_PATH = "lab3.src.path";
    public static final String LAB3_RESULT_PATH = "lab3.result.path";
    public static final String LAB3_IMAGE1_NAME = "lab3.image1.name";
    public static final String LAB3_IMAGE2_NAME = "lab3.image2.name";
    public static final String LAB3_IMAGE3_NAME = "lab3.image3.name";

    public static final String LAB4_SRC_PATH = "lab4.src.path";
    public static final String LAB4_RESULT_PATH = "lab4.result.path";
    public static final String LAB4_IMAGE1_NAME = "lab4.image1.name";
    public static final String LAB4_IMAGE2_NAME = "lab4.image2.name";
    public static final String LAB4_IMAGE3_NAME = "lab4.image3.name";
    public static final String LAB4_IMAGE4_NAME = "lab4.image4.name";

    public static final String LAB5_SRC_PATH = "lab5.src.path";
    public static final String LAB5_RESULT_PATH = "lab5.result.path";
    public static final String LAB5_IMAGE1_NAME = "lab5.image1.name";
    public static final String LAB5_IMAGE2_NAME = "lab5.image2.name";

    public static final String LAB6_SRC_PATH = "lab6.src.path";
    public static final String LAB6_RESULT_PATH = "lab6.result.path";
    public static final String LAB6_IMAGE1_NAME = "lab6.image1.name";

    public enum OperatingSystem {
        WINDOWS,
        LINUX,
        MACOS,
        UNDEFINED;
    };

    @Getter
    @RequiredArgsConstructor
    public enum Morph {
        CROSS(Imgproc.MORPH_CROSS),
        RECT(Imgproc.MORPH_RECT),
        ELLIPSE(Imgproc.MORPH_ELLIPSE);

        private final int shape;
    }
}
