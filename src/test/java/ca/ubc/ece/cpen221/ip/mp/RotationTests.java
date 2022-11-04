package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.*;
import static ca.ubc.ece.cpen221.ip.mp.ImageProcessing.*;

public class RotationTests {
    @Test
    public void imageRotationTest_0() {
        Image img1 = new Image("resources/12003.jpg");
        Image img2 = new Image("resources/12003.jpg");
        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(0);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.01);
    }

    @Test
    public void imageRotationTest_17() {
        Color[][] colourArray1 = new Color[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                colourArray1[i][j] = Color.BLUE;
            }
        }
        Image img1 = Image.makeImageFromColorArray(colourArray1, 2);

        Color[][] colourArray2 = new Color[2][2];
        colourArray2[0][0] = Color.BLUE;
        colourArray2[0][1] = Color.BLUE;
        colourArray2[1][0] = Color.BLUE;
        colourArray2[1][1] = Color.BLUE;
        Image img2 = Image.makeImageFromColorArray(colourArray2, 2);

        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(17);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.0001);
    }

    @Test
    public void imageRotationTest_30() {
        Image img1 = new Image("resources/12003.jpg");
        Image img2 = new Image("resources/tests/12003-r30.png");
        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(30);
        double expected = 1.0;
        double cosSimilarity = cosineSimilarity(img1, img2);
        assertEquals(expected, cosSimilarity, 0.01);
    }

    @Test
    public void imageRotationTest_45() {
        Image img1 = new Image("resources/12003.jpg");
        Image img2 = new Image("resources/tests/12003-r45.png");
        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(45);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.01);
    }

    @Test
    public void imageRotationTest_75() {
        Color[][] colourArray1 = new Color[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                colourArray1[i][j] = Color.BLUE;
            }
        }
        Image img1 = Image.makeImageFromColorArray(colourArray1, 2);

        Color[][] colourArray2 = new Color[2][2];
        colourArray2[0][0] = Color.BLUE;
        colourArray2[0][1] = Color.BLUE;
        colourArray2[1][0] = Color.BLUE;
        colourArray2[1][1] = Color.BLUE;
        Image img2 = Image.makeImageFromColorArray(colourArray2, 2);

        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(75);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.0001);
    }

    @Test
    public void imageRotationTest_90() {
        Color[][] colourArray1 = new Color[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                colourArray1[i][j] = Color.BLUE;
            }
        }
        Image img1 = Image.makeImageFromColorArray(colourArray1, 2);

        Color[][] colourArray2 = new Color[2][2];
        colourArray2[0][0] = Color.WHITE;
        colourArray2[0][1] = Color.BLUE;
        colourArray2[1][0] = Color.WHITE;
        colourArray2[1][1] = Color.BLUE;
        Image img2 = Image.makeImageFromColorArray(colourArray2, 2);

        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(90);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.0001);
    }

    @Test
    public void imageRotationTest_106() {
        Color[][] colourArray1 = new Color[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                colourArray1[i][j] = Color.BLUE;
            }
        }
        Image img1 = Image.makeImageFromColorArray(colourArray1, 2);

        Color[][] colourArray2 = new Color[2][2];
        colourArray2[0][0] = Color.WHITE;
        colourArray2[0][1] = Color.BLUE;
        colourArray2[1][0] = Color.BLUE;
        colourArray2[1][1] = Color.BLUE;
        Image img2 = Image.makeImageFromColorArray(colourArray2, 2);

        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(106);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.0001);
    }

    @Test
    public void imageRotationTest_135() {
        Image img1 = Image.makeSolidColorImage(Color.BLUE, 2, 2);

        Color[][] colourArray2 = new Color[2][2];
        colourArray2[0][0] = Color.WHITE;
        colourArray2[0][1] = Color.BLUE;
        colourArray2[1][0] = Color.BLUE;
        colourArray2[1][1] = Color.BLUE;
        Image img2 = Image.makeImageFromColorArray(colourArray2, 2);

        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(135);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.0001);
    }

    @Test
    public void imageRotationTest_150() {
        Color[][] colourArray1 = new Color[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                colourArray1[i][j] = Color.BLUE;
            }
        }
        Image img1 = Image.makeImageFromColorArray(colourArray1, 2);

        Color[][] colourArray2 = new Color[2][2];
        colourArray2[0][0] = Color.WHITE;
        colourArray2[0][1] = Color.BLUE;
        colourArray2[1][0] = Color.BLUE;
        colourArray2[1][1] = Color.BLUE;
        Image img2 = Image.makeImageFromColorArray(colourArray2, 2);

        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(150);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.0001);
    }

    @Test
    public void imageRotationTest_180() {
        Image img1 = new Image("resources/12003.jpg");
        Image img2 = new Image("resources/tests/12003-r180.png");
        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(180);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.01);
    }

    @Test
    public void imageRotationTest_207() {
        Color[][] colourArray1 = new Color[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                colourArray1[i][j] = Color.BLUE;
            }
        }
        Image img1 = Image.makeImageFromColorArray(colourArray1, 2);

        Color[][] colourArray2 = new Color[2][2];
        colourArray2[0][0] = Color.WHITE;
        colourArray2[0][1] = Color.BLUE;
        colourArray2[1][0] = Color.BLUE;
        colourArray2[1][1] = Color.BLUE;
        Image img2 = Image.makeImageFromColorArray(colourArray2, 2);

        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(207);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.0001);
    }

    @Test
    public void imageRotationTest_225() {
        Color[][] colourArray1 = new Color[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                colourArray1[i][j] = Color.BLUE;
            }
        }
        Image img1 = Image.makeImageFromColorArray(colourArray1, 2);

        Color[][] colourArray2 = new Color[2][2];
        colourArray2[0][0] = Color.WHITE;
        colourArray2[0][1] = Color.BLUE;
        colourArray2[1][0] = Color.BLUE;
        colourArray2[1][1] = Color.BLUE;
        Image img2 = Image.makeImageFromColorArray(colourArray2, 2);

        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(225);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.0001);
    }

    @Test
    public void imageRotationTest_241() {
        Color[][] colourArray1 = new Color[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                colourArray1[i][j] = Color.BLUE;
            }
        }
        Image img1 = Image.makeImageFromColorArray(colourArray1, 2);

        Color[][] colourArray2 = new Color[2][2];
        colourArray2[0][0] = Color.WHITE;
        colourArray2[0][1] = Color.BLUE;
        colourArray2[1][0] = Color.BLUE;
        colourArray2[1][1] = Color.BLUE;
        Image img2 = Image.makeImageFromColorArray(colourArray2, 2);

        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(241);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.0001);
    }

    @Test
    public void imageRotationTest_270() {
        Color[][] colourArray1 = new Color[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                colourArray1[i][j] = Color.BLUE;
            }
        }
        Image img1 = Image.makeImageFromColorArray(colourArray1, 2);

        Color[][] colourArray2 = new Color[2][2];
        colourArray2[0][0] = Color.WHITE;
        colourArray2[0][1] = Color.WHITE;
        colourArray2[1][0] = Color.BLUE;
        colourArray2[1][1] = Color.BLUE;
        Image img2 = Image.makeImageFromColorArray(colourArray2, 2);

        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(270);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.0001);
    }

    @Test
    public void imageRotationTest_299() {
        Color[][] colourArray1 = new Color[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                colourArray1[i][j] = Color.BLUE;
            }
        }
        Image img1 = Image.makeImageFromColorArray(colourArray1, 2);

        Color[][] colourArray2 = new Color[2][2];
        colourArray2[0][0] = Color.BLUE;
        colourArray2[0][1] = Color.BLUE;
        colourArray2[1][0] = Color.BLUE;
        colourArray2[1][1] = Color.BLUE;
        Image img2 = Image.makeImageFromColorArray(colourArray2, 2);

        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(299);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.0001);
    }

    @Test
    public void imageRotationTest_315() {
        Color[][] colourArray1 = new Color[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                colourArray1[i][j] = Color.BLUE;
            }
        }
        Image img1 = Image.makeImageFromColorArray(colourArray1, 2);

        Color[][] colourArray2 = new Color[2][2];
        colourArray2[0][0] = Color.BLUE;
        colourArray2[0][1] = Color.BLUE;
        colourArray2[1][0] = Color.BLUE;
        colourArray2[1][1] = Color.BLUE;
        Image img2 = Image.makeImageFromColorArray(colourArray2, 2);

        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(315);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.0001);
    }

    @Test
    public void imageRotationTest_333() { //change
        Color[][] colourArray1 = new Color[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                colourArray1[i][j] = Color.BLUE;
            }
        }
        Image img1 = Image.makeImageFromColorArray(colourArray1, 2);

        Color[][] colourArray2 = new Color[2][2];
        colourArray2[0][0] = Color.BLUE;
        colourArray2[0][1] = Color.BLUE;
        colourArray2[1][0] = Color.BLUE;
        colourArray2[1][1] = Color.BLUE;
        Image img2 = Image.makeImageFromColorArray(colourArray2, 2);

        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(333);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.0001);
    }

    @Test
    public void imageRotationTest_360() { //change
        Image img1 = new Image("resources/12003.jpg");
        Image img2 = new Image("resources/12003.jpg");
        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(360);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.1);
    }

    @Test
    public void imageRotationTest_line_45() {
        Color[][] colourArray1 = new Color[1][3];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                colourArray1[i][j] = Color.BLUE;
            }
        }
        Image img1 = Image.makeImageFromColorArray(colourArray1, 3);

        Color[][] colourArray2 = new Color[2][2];
        colourArray2[0][0] = Color.BLUE;
        colourArray2[0][1] = Color.BLUE;
        colourArray2[1][0] = Color.BLUE;
        colourArray2[1][1] = Color.BLUE;
        Image img2 = Image.makeImageFromColorArray(colourArray2, 2);

        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(45);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.01);
    }

    @Test
    public void imageRotationTest_line_90() {
        Color[][] colourArray1 = new Color[1][3];
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                colourArray1[i][j] = Color.BLUE;
            }
        }
        Image img1 = Image.makeImageFromColorArray(colourArray1, 3);

        Color[][] colourArray2 = new Color[3][1];
        colourArray2[0][0] = Color.BLUE;
        colourArray2[1][0] = Color.BLUE;
        colourArray2[2][0] = Color.BLUE;
        Image img2 = Image.makeImageFromColorArray(colourArray2, 1);

        ImageTransformer x = new ImageTransformer(img1);
        img1 = x.rotate(90);
        double expected = 1.0;
        assertEquals(expected, cosineSimilarity(img1, img2), 0.0001);
    }

}
