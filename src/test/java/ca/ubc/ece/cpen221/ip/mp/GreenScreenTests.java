package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import org.junit.Test;

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

public class GreenScreenTests {

    @Test
    public void replaceWholeImageTest() {
        Image inputImage = Image.makeSolidColorImage(Color.BLACK, 5, 5);
        ImageTransformer input = new ImageTransformer(inputImage);
        Image backgroundImage = Image.makeSolidColorImage(Color.white, 5, 5);

        Image output = input.greenScreen(Color.BLACK, backgroundImage);
        Image expectedOutput = Image.makeSolidColorImage(Color.white, 5, 5);

        assertEquals(expectedOutput, output);
    }

    @Test
    public void replacePartOfImageTest() {
        Image inputImage = Image.makeSolidColorImage(Color.BLACK, 5, 5);

        for (int i = 1; i < 4; i++) {
            for (int j = 2; j < 4; j++) {
                inputImage.set(i, j, Color.BLUE);
            }
        }
        ImageTransformer input = new ImageTransformer(inputImage);
        Image backgroundImage = Image.makeSolidColorImage(Color.white, 5, 5);

        Image output = input.greenScreen(Color.BLUE, backgroundImage);
        Image expectedOutput = Image.makeSolidColorImage(Color.BLACK, 5, 5);
        for (int i = 1; i < 4; i++) {
            for (int j = 2; j < 4; j++) {
                expectedOutput.set(i, j, Color.white);
            }
        }

        assertEquals(expectedOutput, output);
    }

    @Test
    public void multipleRegionsTest() {
        Image inputImage = Image.makeSolidColorImage(Color.BLACK, 5, 5);

        for (int i = 1; i < 4; i++) {
            for (int j = 2; j < 4; j++) {
                inputImage.set(i, j, Color.BLUE);
            }
        }

        inputImage.set(0, 0, Color.BLUE);

        ImageTransformer input = new ImageTransformer(inputImage);
        Image backgroundImage = Image.makeSolidColorImage(Color.white, 5, 5);

        Image output = input.greenScreen(Color.BLUE, backgroundImage);
        Image expectedOutput = Image.makeSolidColorImage(Color.BLACK, 5, 5);
        for (int i = 1; i < 4; i++) {
            for (int j = 2; j < 4; j++) {
                expectedOutput.set(i, j, Color.white);
            }
        }
        expectedOutput.set(0, 0, Color.BLUE);

        assertEquals(expectedOutput, output);
    }

    @Test
    public void nonUniformRegionTest() {
        Image inputImage = Image.makeSolidColorImage(Color.BLACK, 5, 5);


        HashSet<Point> greenPoints = new HashSet<>(Arrays.asList(new Point(0, 0),
            new Point(1, 0), new Point(2, 0), new Point(0, 1),
            new Point(3, 1), new Point(1, 2), new Point(3, 3),
            new Point(3, 4), new Point(4, 3), new Point(4, 4)));
        inputImage.changeColourOfPixelSet(greenPoints, Color.green);

        ImageTransformer input = new ImageTransformer(inputImage);
        Image backgroundImage = Image.makeSolidColorImage(Color.red, 5, 5);

        Image output = input.greenScreen(Color.green, backgroundImage);

        Image expectedOutput = Image.makeSolidColorImage(Color.BLACK, 5, 5);

        HashSet<Point> redPoints = new HashSet<>(Arrays.asList(new Point(0, 0),
            new Point(1, 0), new Point(2, 0), new Point(0, 1),
            new Point(3, 1), new Point(1, 2)));
        expectedOutput.changeColourOfPixelSet(redPoints, Color.RED);

        for (int i = 3; i < expectedOutput.height(); i++) {
            for (int j = 3; j < expectedOutput.width(); j++) {
                expectedOutput.set(i, j, Color.green);
            }
        }

        assertEquals(expectedOutput, output);

    }

    @Test
    public void horizontalAndVerticalTilingTest() {
        Image inputImg = Image.makeSolidColorImage(Color.yellow, 10, 5);
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 6; j++) {
                inputImg.set(j, i, Color.RED);
            }
        }

        ImageTransformer input = new ImageTransformer(inputImg);
        Color screenColor = Color.RED;

        Color[][] backgroundMatrix = new Color[][] {{Color.BLUE, Color.GREEN},
            {Color.orange, Color.pink}};
        Image background = Image.makeImageFromColorArray(backgroundMatrix, 2);


        Image output = input.greenScreen(screenColor, background);

        Image expectedOutput = Image.makeSolidColorImage(Color.yellow, 10, 5);

        HashSet<Point> bluePoints = new HashSet<>(Arrays.asList(new Point(1, 1),
            new Point(3, 1), new Point(5, 1), new Point(1, 3),
            new Point(1, 3), new Point(3, 3), new Point(5, 3)));
        expectedOutput.changeColourOfPixelSet(bluePoints, Color.BLUE);

        HashSet<Point> greenPoints = new HashSet<>(Arrays.asList(new Point(2, 1),
            new Point(4, 1), new Point(2, 3), new Point(4, 3)));
        expectedOutput.changeColourOfPixelSet(greenPoints, Color.GREEN);

        HashSet<Point> orangePoints = new HashSet<>((Arrays.asList(new Point(1, 2),
            new Point(3, 2), new Point(5, 2))));
        expectedOutput.changeColourOfPixelSet(orangePoints, Color.orange);

        HashSet<Point> pinkPoints = new HashSet<>((Arrays.asList(new Point(2, 2),
            new Point(4, 2))));
        expectedOutput.changeColourOfPixelSet(pinkPoints, Color.pink);

        assertEquals(expectedOutput, output);
    }

    @Test
    public void largeImageGreenScreen() {
        Image input = new Image("resources/clapper-green.jpg");
        ImageTransformer greenScreen = new ImageTransformer(input);
        Color screenColor = input.get(0, 0);
        Image backgroundImage = Image.makeSolidColorImage(Color.BLACK, 1000, 1000);
        Image output = greenScreen.greenScreen(screenColor, backgroundImage);

    }

    @Test
    public void horizontalTileTest() {
        Image inputImage = Image.makeSolidColorImage(Color.green, 5, 3);
        Color screenColour = Color.white;

        HashSet<Point> whitePoints = new HashSet<>((Arrays.asList(new Point(1, 0),
            new Point(2, 0), new Point(3, 0), new Point(4, 0),
            new Point(0, 1), new Point(2, 1), new Point(3, 1))));
        inputImage.changeColourOfPixelSet(whitePoints, Color.white);

        Color[][] backgroundMatrix = new Color[][] {{Color.BLUE}, {Color.RED}, {Color.yellow}};
        Image background = Image.makeImageFromColorArray(backgroundMatrix, 1);

        Image expectedOutput = Image.makeSolidColorImage(Color.green, 5, 3);

        HashSet<Point> bluePoints = new HashSet<>((Arrays.asList(new Point(1, 0),
            new Point(2, 0), new Point(3, 0), new Point(4, 0))));

        expectedOutput.changeColourOfPixelSet(bluePoints, Color.blue);

        HashSet<Point> redPoints = new HashSet<>((Arrays.asList(new Point(0, 1),
            new Point(2, 1), new Point(3, 1))));

        expectedOutput.changeColourOfPixelSet(redPoints, Color.RED);

        ImageTransformer input = new ImageTransformer(inputImage);
        Image output = input.greenScreen(screenColour, background);

        assertEquals(expectedOutput, output);

    }

    @Test
    public void verticalTileTest() {
        Image inputImage = Image.makeSolidColorImage(Color.GRAY, 4, 6);
        Color screenColour = Color.black;

        for (int i = 0; i < inputImage.width(); i++) {
            for (int j = 0; j < inputImage.height(); j++) {

                if (i == 2) {

                } else {
                    inputImage.set(i, j, screenColour);
                }
            }
        }

        HashSet<Point> greyPoints = new HashSet<>((Arrays.asList(new Point(0, 0),
            new Point(0, 1), new Point(1, 3), new Point(0, 4),
            new Point(1, 5))));

        inputImage.changeColourOfPixelSet(greyPoints, Color.GRAY);


        Color[][] backgroundMatrix =
            new Color[][] {{Color.PINK, Color.RED, Color.BLUE, Color.BLUE}};
        Image background = Image.makeImageFromColorArray(backgroundMatrix, 4);

        Image expectedOutput = Image.makeSolidColorImage(Color.GRAY, 4, 6);

        HashSet<Point> pinkPoints = new HashSet<>((Arrays.asList(new Point(0, 2),
            new Point(0, 3), new Point(0, 5))));

        expectedOutput.changeColourOfPixelSet(pinkPoints, Color.PINK);

        HashSet<Point> redPoints = new HashSet<>((Arrays.asList(new Point(1, 0),
            new Point(1, 1), new Point(1, 2), new Point(1, 4))));

        expectedOutput.changeColourOfPixelSet(redPoints, Color.RED);

        for (int i = 0; i < expectedOutput.height(); i++) {
            expectedOutput.set(3, i, Color.BLACK);
        }

        ImageTransformer input = new ImageTransformer(inputImage);
        Image output = input.greenScreen(screenColour, background);

        assertEquals(expectedOutput, output);
    }

    @Test
    public void onePixelRegionTest() {
        Color imgColor = new Color(8, 150, 200);
        Color greenScreenColor = new Color(200, 69, 69);
        Color replacementColor = new Color(100, 200, 8);

        Image inputImg = Image.makeSolidColorImage(imgColor, 23, 18);
        inputImg.set(22, 17, greenScreenColor);

        Color[][] backgroundMatrix = new Color[][] {{replacementColor}};
        Image backgroundImage = Image.makeImageFromColorArray(backgroundMatrix, 1);

        Image expectedOutput = Image.makeSolidColorImage(imgColor, 23, 18);
        expectedOutput.set(22, 17, replacementColor);

        ImageTransformer input = new ImageTransformer(inputImg);
        Image output = input.greenScreen(greenScreenColor, backgroundImage);

        assertEquals(expectedOutput, output);
    }

    @Test
    public void multipleSameSizeRegionsTest() {
        Color screenColor = Color.GREEN;
        Image inputImage = Image.makeSolidColorImage(Color.WHITE, 8, 8);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                inputImage.set(i, j, screenColor);
            }
        }
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 2; j++) {
                inputImage.set(i, j, screenColor);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 3; j < 5; j++) {
                inputImage.set(i, j, screenColor);
            }
        }
        for (int i = 5; i < 8; i++) {
            for (int j = 3; j < 5; j++) {
                inputImage.set(i, j, screenColor);
            }
        }
        for (int i = 5; i < 8; i++) {
            for (int j = 6; j < 8; j++) {
                inputImage.set(i, j, screenColor);
            }
        }

        Image expectedOutput = new Image(inputImage);
        Image background = Image.makeSolidColorImage(Color.BLUE, 3, 2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                expectedOutput.set(i, j, Color.BLUE);
            }
        }

        ImageTransformer input = new ImageTransformer(inputImage);
        Image output = input.greenScreen(screenColor, background);

        assertEquals(expectedOutput, output);
    }

    @Test
    public void noMatchingPixelsTest() {
        Image inputImg = Image.makeSolidColorImage(Color.MAGENTA, 88, 88);
        Color screenColor = new Color(254, 0, 255);
        Image background = Image.makeSolidColorImage(Color.CYAN, 69, 69);

        Image expectedOutput = Image.makeSolidColorImage(Color.MAGENTA, 88, 88);

        ImageTransformer input = new ImageTransformer(inputImg);

        Image output = input.greenScreen(screenColor, background);

        assertEquals(expectedOutput, output);
    }

}