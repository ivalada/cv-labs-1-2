package ru.sfedu.api;

import lombok.extern.slf4j.Slf4j;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;
import org.opencv.utils.Converters;
import ru.sfedu.util.ConfigUtils;
import ru.sfedu.util.ImageUtils;
import ru.sfedu.util.SystemUtils;

import java.util.*;
import java.util.stream.IntStream;

import static ru.sfedu.constant.Constants.OPENCV_LINUX_LIB_PATH_NAME;
import static ru.sfedu.constant.Constants.OperatingSystem;
import static ru.sfedu.constant.Constants.Morph;

@Slf4j
public class ImageAPI {

    /* lab 1*/

    public ImageAPI() throws RuntimeException {
        OperatingSystem os = SystemUtils.getOperationSystem();
        log.info("Detected OS: {}", os.name());
        loadOpenCVLibForOS(os);
        log.info("OpenCV lib load success");
    }

    public String getOpenCVVersion() {
        return Core.getVersionString();
    }

    private void loadOpenCVLibForOS(OperatingSystem os) throws RuntimeException {
        if ( OperatingSystem.LINUX.equals(os) ) {
            System.load(ConfigUtils.getConfigProperty(OPENCV_LINUX_LIB_PATH_NAME));
            return;
        }
        throw new RuntimeException("OS not supported");
    }

    /* lab 2 */

    public Mat loadImage(String pathToImageFile) {
        return Imgcodecs.imread(pathToImageFile);
    }

    public void saveImage(Mat image, String path, String name) {
        saveImage(image, path + name);
    }

    public void saveImage(Mat image, String fullPath) {
        Imgcodecs.imwrite(fullPath, image);
    }

    public Mat resetChannel(Mat image, int channel) {
        byte[] imageArray = ImageUtils.convertToByteArray(image);
        IntStream.range(0, imageArray.length).filter(i -> i % channel == 0).forEach(i -> imageArray[i] = 0);
        Mat resultImage = image.clone();
        resultImage.put(0, 0, imageArray);
        return resultImage;
    }

    /* lab 3 */

    public void sobelTransform(Mat image, Mat resultX, Mat resultY) {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Sobel(grayImage, resultX, CvType.CV_32F, 1, 0);
        Imgproc.Sobel(grayImage, resultY, CvType.CV_32F, 0, 1);
    }

    public Mat laplaceTransform(Mat image) {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        Mat dstLaplace = new Mat();
        Imgproc.Laplacian(image, dstLaplace, CvType.CV_32F);
        Mat absLaplaceImage = new Mat();
        Core.convertScaleAbs(dstLaplace, absLaplaceImage);
        return absLaplaceImage;
    }

    public Mat flipHV(Mat image) {
        Mat resultImage = new Mat();
        Core.flip(image, resultImage, -1);
        return resultImage;
    }

    public Mat flipH(Mat image) {
        Mat resultImage = new Mat();
        Core.flip(image, resultImage, 1);
        return resultImage;
    }

    public Mat flipV(Mat image) {
        Mat resultImage = new Mat();
        Core.flip(image, resultImage, 0);
        return resultImage;
    }

    public Mat repeatH(Mat image, int n) {
        return repeatHV(image, n, 1);
    }

    public Mat repeatV(Mat image, int n) {
        return repeatHV(image, 1, n);
    }

    public Mat repeatHV(Mat image, int nH, int nV) {
        Mat resultImage = new Mat();
        Core.repeat(image, nH, nV, resultImage);
        return resultImage;
    }

    public Mat concatH(Mat ...images) {
        Mat resultImage = new Mat();
        Core.hconcat(List.of(images), resultImage);
        return resultImage;
    }

    public Mat concatV(Mat ...images) {
        Mat resultImage = new Mat();
        Core.vconcat(List.of(images), resultImage);
        return resultImage;
    }

    public Mat resize(Mat image, int width, int height) {
        Mat resultImage = new Mat();
        Imgproc.resize(image, resultImage, new Size(width, height));
        return resultImage;
    }

    public Mat rotate(Mat image, int angle, boolean crop) {
        Point center = new Point(image.width() / 2, image.height() / 2);
        Mat rotationMatrix = Imgproc.getRotationMatrix2D(center, angle, 1);
        Mat resultImage = new Mat();
        if ( crop )
            Imgproc.warpAffine(image, resultImage, rotationMatrix, new Size(image.width(), image.height()), Imgproc.INTER_LINEAR);
        else
            Imgproc.warpAffine(image, resultImage, rotationMatrix, new Size(image.width(), image.height()), Imgproc.INTER_LINEAR, Core.BORDER_REPLICATE);
        return resultImage;
    }

    public Mat shiftH(Mat image, int n) {
        return shiftHV(image, n, 0);
    }

    public Mat shiftV(Mat image, int n) {
        return shiftHV(image, 0, n);
    }

    public Mat shiftHV(Mat image, int nH, int nV) {
        Mat warpMat = new Mat(2, 3, CvType.CV_64FC1);
        warpMat.put(0 ,0, 1, 0, nH, 0, 1, nV);
        Mat resultImage = new Mat();
        Imgproc.warpAffine(
                image,
                resultImage,
                warpMat,
                image.size()
        );
        return resultImage;
    }

    public Mat warpPerspective(Mat image) {
        int x0 = image.cols() / 4;
        int x1 = (image.cols() / 4) * 3;
        int y0 = image.cols() / 4;
        int y1 = (image.cols() / 4 ) * 3;

        List<Point> listSrcs = List.of(new Point(x0,y0), new Point(x0,y1), new Point(x1,y1), new Point(x1,y0));
        Mat srcPoints = Converters.vector_Point_to_Mat(listSrcs,CvType.CV_32F);

        int xMargin= image.cols() / 10;
        int yMargin= image.rows() / 10;
        List<Point> listDsts = List.of(
                new Point(x0+ xMargin,y0 + yMargin),
                listSrcs.get(1),
                listSrcs.get(2),
                new Point(x1 - xMargin, y0 + yMargin)
        );
        Mat dstPoints = Converters.vector_Point_to_Mat(listDsts, CvType.CV_32F);

        Mat perspectiveMmat = Imgproc.getPerspectiveTransform(srcPoints, dstPoints);

        Mat resultImage = new Mat();
        Imgproc.warpPerspective(image, resultImage, perspectiveMmat, image.size());

        return resultImage;
    }

    /* lab 4 */

    public Mat avgBlur(Mat image, int kernelSize) {
        Mat resultImage = new Mat();
        Imgproc.blur(image, resultImage, new Size(kernelSize, kernelSize));
        return resultImage;
    }

    public Mat gaussianBlur(Mat image, int kernelSize) {
        Mat resultImage = new Mat();
        double sigma = 0.3 * ((kernelSize - 1) * 0.5 -1) + 0.8;
        Imgproc.GaussianBlur(image, resultImage, new Size(kernelSize, kernelSize), sigma);
        return resultImage;
    }

    public Mat medianBlur(Mat image, int kernelSize) {
        Mat resultImage = new Mat();
        Imgproc.medianBlur(image, resultImage, kernelSize);
        return resultImage;
    }

    public Mat bilateralFilter(Mat image, int kernelSize) {
        Mat resultImage = new Mat();
        Imgproc.bilateralFilter(image, resultImage, kernelSize, 250, 50);
        return resultImage;
    }

    public Mat erode(Mat image, int kernelSize, Morph shapeName) {
        Mat resultImage = new Mat();
        Mat element = Imgproc.getStructuringElement(shapeName.getShape(), new Size(kernelSize, kernelSize));
        Imgproc.erode(image, resultImage, element);
        return resultImage;
    }

    public Mat dilate(Mat image, int kernelSize, Morph shapeName) {
        Mat resultImage = new Mat();
        Mat element = Imgproc.getStructuringElement(shapeName.getShape(), new Size(kernelSize, kernelSize));
        Imgproc.dilate(image, resultImage, element);
        return resultImage;
    }

    /* lab 5 */

    public void floodFill(Mat image, int centerX, int centerY, int minLimit, int maxLimit, Scalar color) {
        Point center = new Point(centerX,centerY);
        Scalar low = new Scalar(minLimit, minLimit, minLimit);
        Scalar high = new Scalar(maxLimit, maxLimit, maxLimit);
        if (Objects.isNull(color)) {
            Random random = new Random();
            color = new Scalar(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        }
        Mat mask = new Mat();
        Imgproc.floodFill(image, mask, center, color, new Rect(), low, high, Imgproc.FLOODFILL_FIXED_RANGE);
    }

    public Mat pyramidUpSegmentation(Mat image, int n) {
        Mat mask = new Mat();
        IntStream.range(0, n).forEach(i -> Imgproc.pyrUp(image, mask));
        return mask;
    }

    public Mat pyramidDownSegmentation(Mat image, int n) {
        Mat mask = new Mat();
        IntStream.range(0, n).forEach(i -> Imgproc.pyrDown(image, mask));
        return mask;
    }

    public int countRectangleShapes(Mat image, int width, int height) {
        Mat grayMatImage = new Mat();
        Imgproc.cvtColor(image, grayMatImage, Imgproc.COLOR_BGR2GRAY);

        Mat denoisingImage = new Mat();
        Photo.fastNlMeansDenoising(grayMatImage, denoisingImage);

        Mat histogramEqualizationImageMat = new Mat();
        Imgproc.equalizeHist(denoisingImage, histogramEqualizationImageMat);

        Mat morhologicalOpeningImageMat = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5,5));
        Imgproc.morphologyEx(histogramEqualizationImageMat, morhologicalOpeningImageMat, Imgproc.MORPH_RECT,  kernel);

        Mat substractImage = new Mat();
        Core.subtract(histogramEqualizationImageMat, morhologicalOpeningImageMat, substractImage);

        Mat thresholdImage = new Mat();
        double threshold = Imgproc.threshold(
                denoisingImage,
                thresholdImage,
                100, // в зависимости от картиночки этот параметр стоит менять, зависит от контраста цветов самой картинки
                255,
                Imgproc.THRESH_BINARY
        );
        thresholdImage.convertTo(thresholdImage, CvType.CV_8U);

        Mat edgeImage = new Mat();
        Imgproc.Canny(thresholdImage, edgeImage, threshold, threshold * 3, 3, true);

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(thresholdImage, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        contours.sort(Collections.reverseOrder(Comparator.comparing(Imgproc::contourArea)));
        List<MatOfPoint> rectanglesContours = new ArrayList<MatOfPoint>();
        for (int i = 0; i < contours.size(); i++) {
            if (i == 0) {
                continue;
            }
            MatOfPoint contour = contours.get(i);
            MatOfPoint2f point2f = new MatOfPoint2f();
            MatOfPoint2f approxContour2f = new MatOfPoint2f();
            MatOfPoint approxContour = new MatOfPoint();

            contour.convertTo(point2f, CvType.CV_32FC2);

            double arcLength = Imgproc.arcLength(point2f, true);
            Imgproc.approxPolyDP(point2f, approxContour2f, 0.01 * arcLength, true);

            approxContour2f.convertTo(approxContour, CvType.CV_32S);
            Rect rect = Imgproc.boundingRect(approxContour);

            if (rect.height < height || rect.width < width) {
                continue;
            }
            log.info("total - {} height - {}, width - {}, hash - {}", point2f.total(), rect.height, rect.width, contour.hashCode());
            rectanglesContours.add(contour);
            Imgproc.putText(
                    image,
                    "" + contour.hashCode(),
                    new Point(rect.x + 1 , rect.y + (rect.height / 1.5)),
                    Imgproc.FONT_ITALIC,
                    0.5,
                    new Scalar(0, 0, 0),
                    1
            );
        }
        Imgproc.drawContours(image, rectanglesContours, -1, new Scalar(0, 255, 0), 3);
        return rectanglesContours.size();
    }

    /* lab 6 */

    public Mat cannyBorders(Mat image) {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        Mat detectedEdges = new Mat();
        Imgproc.blur(grayImage, detectedEdges, new Size(3, 3));

        Mat thresholdImage = new Mat();
        double threshold = Imgproc.threshold(grayImage, thresholdImage, 0, 255, Imgproc.THRESH_OTSU);
        Imgproc.Canny(detectedEdges, detectedEdges, threshold, threshold * 3);

        return detectedEdges;
    }
}
