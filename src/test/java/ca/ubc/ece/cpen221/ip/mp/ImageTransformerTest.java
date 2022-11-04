package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import ca.ubc.ece.cpen221.ip.core.ImageProcessingException;
import ca.ubc.ece.cpen221.ip.core.Rectangle;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class ImageTransformerTest {

    @Test
    public void redTestNoRed() {
        Image inputImg = Image.makeSolidColorImage(Color.GREEN, 10, 7);
        ImageTransformer input = new ImageTransformer(inputImg);

        Image output = input.red();

        Image expectedOutput = Image.makeSolidColorImage(Color.BLACK, 10, 7);

        assertEquals(expectedOutput, output);

    }

    @Test
    public void redTestAllRed() {
        Image inputImg = Image.makeSolidColorImage(Color.RED, 10, 7);
        ImageTransformer input = new ImageTransformer(inputImg);

        Image output = input.red();

        Image expectedOutput = Image.makeSolidColorImage(Color.RED, 10, 7);

        assertEquals(expectedOutput, output);

    }

    @Test
    public void redTest1() {
        Color customColor = new Color(69, 234, 90);

        Image inputImg = Image.makeSolidColorImage(customColor, 10, 7);
        ImageTransformer input = new ImageTransformer(inputImg);

        Image output = input.red();

        Color redCustomColor = new Color(69, 0, 0);
        Image expectedOutput = Image.makeSolidColorImage(redCustomColor, 10, 7);

        assertEquals(expectedOutput, output);

    }

    @Test
    public void redTest2() {
        Color customColor1 = new Color(69, 234, 90);
        Color customColor2 = new Color(188, 5, 8);
        Color customColor3 = new Color(208, 69, 88);

        Color[][] imageMatrix = new Color[][]{{Color.RED, Color.YELLOW, customColor1},
                {customColor3, Color.BLACK, customColor2},
                {Color.PINK, customColor1, customColor2}};
        Image inputImg = Image.makeImageFromColorArray(imageMatrix, 3);
        ImageTransformer input = new ImageTransformer(inputImg);

        Image output = input.red();

        Color redCustomColor1 = new Color(69, 0, 0);
        Color redCustomColor2 = new Color(188, 0, 0);
        Color redCustomColor3 = new Color(208, 0, 0);

        Color[][] expectedMatrix = new Color[][]{{Color.RED, Color.RED, redCustomColor1},
                {redCustomColor3, Color.BLACK, redCustomColor2},
                {Color.RED, redCustomColor1, redCustomColor2}};
        Image expectedOutput = Image.makeImageFromColorArray(expectedMatrix, 3);

        assertEquals(expectedOutput, output);

    }

    @Test
    public void grayscaleTest() {
        Image inputImage = Image.makeSolidColorImage(Color.GREEN, 7, 3);
        Color customColor1 = new Color(88, 188, 208);
        Color customColor2 = new Color(69, 234, 90);


        for (int i = 0; i < inputImage.width(); i++) {
            inputImage.set(i, 0, Color.MAGENTA);
        }
        for (int i = 0; i < inputImage.width(); i++) {
            inputImage.set(i, 1, customColor1);
        }
        for (int i = 0; i < inputImage.height(); i++) {
            inputImage.set(4, i, customColor2);
        }

        Color grayGreen = new Color(150, 150, 150);
        Color grayMagenta = new Color(105, 105, 105);
        Color grayCustomColor1 = new Color(160, 160, 160);
        Color grayCustomColor2 = new Color(168, 168, 168);
        Image expectedOutput = Image.makeSolidColorImage(grayGreen, 7, 3);

        for (int i = 0; i < inputImage.width(); i++) {
            expectedOutput.set(i, 0, grayMagenta);
        }
        for (int i = 0; i < inputImage.width(); i++) {
            expectedOutput.set(i, 1, grayCustomColor1);
        }
        for (int i = 0; i < inputImage.height(); i++) {
            expectedOutput.set(4, i, grayCustomColor2);
        }

        ImageTransformer input = new ImageTransformer(inputImage);
        Image output = input.grayscale();

        assertEquals(expectedOutput, output);

    }

    @Test
    public void denoiseOneColorTest() {
        Color customColor = new Color(24, 182, 248);
        Image inputImage = Image.makeSolidColorImage(customColor, 88, 50);

        ImageTransformer input = new ImageTransformer(inputImage);
        Image output = input.denoise();

        Image expectedOutput = Image.makeSolidColorImage(customColor, 88, 50);

        assertEquals(expectedOutput, output);
    }

    @Test
    public void denoiseTest() {
        Color customColor1 = new Color(4, 128, 252);
        Color customColor2 = new Color(14, 28, 42);
        Color customColor3 = new Color(34, 64, 88);

        Color[][] inputMatrix = new Color[][]{{customColor1, Color.RED, Color.MAGENTA},
                {Color.BLUE, Color.GREEN, Color.PINK},
                {customColor2, customColor3, Color.WHITE}};

        Image inputImage = Image.makeImageFromColorArray(inputMatrix, 3);

        Color zeroZero = new Color(2, 64, 126);
        Color oneZero = new Color(129, 64, 213);
        Color twoZero = new Color(255, 87, 87);
        Color zeroOne = new Color(9, 46, 65);
        Color oneOne = new Color(34, 64, 175);
        Color twoOne = new Color(255, 119, 131);
        Color zeroTwo = new Color(7, 46, 65);
        Color oneTwo = new Color(24, 119, 131);
        Color twoTwo = new Color(144, 215, 131);

        Color[][] expectedMatrix = new Color[][]{{zeroZero, oneZero, twoZero},
                {zeroOne, oneOne, twoOne}, {zeroTwo, oneTwo, twoTwo}};
        Image expectedOutput = Image.makeImageFromColorArray(expectedMatrix, 3);

        ImageTransformer input = new ImageTransformer(inputImage);

        Image output = input.denoise();

        assertEquals(expectedOutput, output);
    }

    @Test
    public void denoiseHorizontalLineTest() {
        Color customColor1 = new Color(224, 127, 183);
        Color customColor2 = new Color(58, 201, 231);
        Color customColor3 = new Color(34, 63, 106);
        Color customColor4 = new Color(200, 61, 190);

        Color[][] inputMatrix = new Color[][]{{customColor1, customColor2,
                customColor3, customColor4, Color.BLACK}};

        Image inputImage = Image.makeImageFromColorArray(inputMatrix, 5);

        Color zeroZero = new Color(141, 164, 207);
        Color oneZero = new Color(58, 127, 183);
        Color twoZero = new Color(58, 63, 190);
        Color threeZero = new Color(34, 61, 106);
        Color fourZero = new Color(100, 30, 95);


        Color[][] expectedMatrix = new Color[][]{{zeroZero, oneZero, twoZero,
                threeZero, fourZero}};
        Image expectedOutput = Image.makeImageFromColorArray(expectedMatrix, 5);

        ImageTransformer input = new ImageTransformer(inputImage);

        Image output = input.denoise();

        assertEquals(expectedOutput, output);
    }

    @Test
    public void denoiseVerticalLineTest() {
        Color customColor1 = new Color(224, 127, 183);
        Color customColor2 = new Color(58, 201, 231);
        Color customColor3 = new Color(34, 63, 106);
        Color customColor4 = new Color(200, 61, 190);

        Color[][] inputMatrix = new Color[][]{{customColor1}, {customColor2},
                {customColor3}, {customColor4}, {Color.BLACK}};

        Image inputImage = Image.makeImageFromColorArray(inputMatrix, 1);

        Color zeroZero = new Color(141, 164, 207);
        Color zeroOne = new Color(58, 127, 183);
        Color zeroTwo = new Color(58, 63, 190);
        Color zeroThree = new Color(34, 61, 106);
        Color zeroFour = new Color(100, 30, 95);


        Color[][] expectedMatrix = new Color[][]{{zeroZero}, {zeroOne}, {zeroTwo},
                {zeroThree}, {zeroFour}};
        Image expectedOutput = Image.makeImageFromColorArray(expectedMatrix, 1);

        ImageTransformer input = new ImageTransformer(inputImage);

        Image output = input.denoise();

        assertEquals(expectedOutput, output);
    }

    @Test
    public void blockPaintAllSameColour() {
        int blockSize = 6;
        Image inputImage = Image.makeSolidColorImage(Color.BLUE, 32, 32);
        ImageTransformer input = new ImageTransformer(inputImage);

        Image expectedOutput = Image.makeSolidColorImage(Color.BLUE, 32, 32);

        Image output = input.blockPaint(blockSize);

        assertEquals(expectedOutput, output);
    }

    @Test
    public void blockPaintEachBlockSameColor() {
        int blockSize = 2;
        Image inputImage = Image.makeSolidColorImage(Color.BLUE, 4, 4);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                inputImage.set(i, j, Color.RED);
            }
        }
        for (int i = 2; i < inputImage.width(); i++) {
            for (int j = 0; j < 2; j++) {
                inputImage.set(i, j, Color.YELLOW);
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 2; j < inputImage.height(); j++) {
                inputImage.set(i, j, Color.GREEN);
            }
        }

        Image expectedOutput = Image.makeSolidColorImage(Color.BLUE, 4, 4);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                expectedOutput.set(i, j, Color.RED);
            }
        }
        for (int i = 2; i < expectedOutput.width(); i++) {
            for (int j = 0; j < 2; j++) {
                expectedOutput.set(i, j, Color.YELLOW);
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 2; j < expectedOutput.height(); j++) {
                expectedOutput.set(i, j, Color.GREEN);
            }
        }

        ImageTransformer input = new ImageTransformer(inputImage);


        Image output = input.blockPaint(blockSize);

        assertEquals(expectedOutput, output);
    }


    @Test
    public void blockPaintTest() {

        int blockSize = 2;

        Color customColor1 = new Color(224, 127, 183);
        Color customColor2 = new Color(58, 201, 231);
        Color customColor3 = new Color(34, 63, 106);
        Color customColor4 = new Color(200, 61, 190);

        Color[][] inputMatrix = new Color[][]{{Color.RED, Color.GREEN, Color.YELLOW, Color.WHITE},
                {Color.BLUE, Color.BLUE, Color.BLACK, Color.MAGENTA},
                {Color.ORANGE, Color.YELLOW, customColor4, customColor3},
                {customColor1, Color.PINK, customColor4, customColor2}};

        Image inputImage = Image.makeImageFromColorArray(inputMatrix, 4);

        Color box1 = new Color(63, 63, 127);
        Color box2 = new Color(191, 127, 127);
        Color box3 = new Color(247, 189, 89);
        Color box4 = new Color(123, 96, 179);


        Image expectedOutput = Image.makeSolidColorImage(box4, 4, 4);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                expectedOutput.set(i, j, box1);
            }
        }
        for (int i = 2; i < expectedOutput.width(); i++) {
            for (int j = 0; j < 2; j++) {
                expectedOutput.set(i, j, box2);
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 2; j < expectedOutput.height(); j++) {
                expectedOutput.set(i, j, box3);
            }
        }

        ImageTransformer input = new ImageTransformer(inputImage);
        Image output = input.blockPaint(blockSize);

        assertEquals(expectedOutput, output);

    }


    @Test
    public void blockPaintNotPerfectSquareImageTest() {
        int blockSize = 3;


        Color[][] inputMatrix = new Color[][]{{Color.RED, Color.GREEN, Color.GREEN, Color.PINK},
                {Color.GREEN, Color.BLUE, Color.BLUE, Color.YELLOW},
                {Color.BLUE, Color.BLUE, Color.BLUE, Color.GRAY},
                {Color.MAGENTA, Color.ORANGE, Color.WHITE, Color.RED}};

        Image inputImage = Image.makeImageFromColorArray(inputMatrix, 4);

        Color box1 = new Color(28, 85, 141);
        Color box2 = new Color(212, 186, 101);
        Color box3 = new Color(255, 151, 170);


        Image expectedOutput = Image.makeSolidColorImage(box1, 4, 4);

        for (int i = 0; i < 3; i++) {
            expectedOutput.set(3, i, box2);
        }
        for (int i = 0; i < 3; i++) {
            expectedOutput.set(i, 3, box3);
        }
        expectedOutput.set(3, 3, Color.RED);

        ImageTransformer input = new ImageTransformer(inputImage);
        Image output = input.blockPaint(blockSize);

        assertEquals(expectedOutput, output);
    }


    @Test
    public void negativeAllBlack() {
        Image inputImage = Image.makeSolidColorImage(Color.BLACK, 73, 105);
        ImageTransformer input = new ImageTransformer(inputImage);
        Image output = input.negative();

        Image expectedOutput = Image.makeSolidColorImage(Color.WHITE, 73, 105);

        assertEquals(expectedOutput, output);
    }

    @Test
    public void negativeAllWhite() {
        Image inputImage = Image.makeSolidColorImage(Color.WHITE, 73, 105);
        ImageTransformer input = new ImageTransformer(inputImage);
        Image output = input.negative();

        Image expectedOutput = Image.makeSolidColorImage(Color.BLACK, 73, 105);

        assertEquals(expectedOutput, output);
    }

    @Test
    public void negativeHorizontalLineTest() {
        Color customColor1 = new Color(224, 127, 183);
        Color customColor2 = new Color(58, 201, 231);
        Color customColor3 = new Color(34, 63, 106);
        Color customColor4 = new Color(200, 61, 190);

        Color[][] inputMatrix = new Color[][]{{customColor1, customColor2,
                customColor3, customColor4, Color.BLACK}};

        Image inputImage = Image.makeImageFromColorArray(inputMatrix, 5);

        Color zeroZero = new Color(31, 128, 72);
        Color oneZero = new Color(197, 54, 24);
        Color twoZero = new Color(221, 192, 149);
        Color threeZero = new Color(55, 194, 65);
        Color fourZero = new Color(255, 255, 255);


        Color[][] expectedMatrix = new Color[][]{{zeroZero, oneZero, twoZero,
                threeZero, fourZero}};
        Image expectedOutput = Image.makeImageFromColorArray(expectedMatrix, 5);

        ImageTransformer input = new ImageTransformer(inputImage);

        Image output = input.negative();

        assertEquals(expectedOutput, output);
    }

    @Test
    public void negativeVerticalLineTest() {
        Color customColor1 = new Color(224, 127, 183);
        Color customColor2 = new Color(58, 201, 231);
        Color customColor3 = new Color(34, 63, 106);
        Color customColor4 = new Color(200, 61, 190);

        Color[][] inputMatrix = new Color[][]{{customColor1}, {customColor2},
                {customColor3}, {customColor4}, {Color.BLACK}};

        Image inputImage = Image.makeImageFromColorArray(inputMatrix, 1);

        Color zeroZero = new Color(31, 128, 72);
        Color zeroOne = new Color(197, 54, 24);
        Color zeroTwo = new Color(221, 192, 149);
        Color zeroThree = new Color(55, 194, 65);
        Color zeroFour = new Color(255, 255, 255);


        Color[][] expectedMatrix = new Color[][]{{zeroZero}, {zeroOne}, {zeroTwo},
                {zeroThree}, {zeroFour}};
        Image expectedOutput = Image.makeImageFromColorArray(expectedMatrix, 1);

        ImageTransformer input = new ImageTransformer(inputImage);

        Image output = input.negative();

        assertEquals(expectedOutput, output);
    }

    @Test
    public void clipTest() throws ImageProcessingException {
        Image inputImage = Image.makeSolidColorImage(Color.CYAN, 5, 5);
        ImageTransformer input = new ImageTransformer(inputImage);

        Rectangle clippingBox = new Rectangle(0, 0, 4, 1);
        Image output = input.clip(clippingBox);

        Image expectedOutput = Image.makeSolidColorImage(Color.CYAN, 5, 2);

        assertEquals(expectedOutput, output);
    }


}