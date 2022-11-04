package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import org.junit.Test;

import java.awt.Color;

import static ca.ubc.ece.cpen221.ip.mp.ImageProcessing.*;
import static org.junit.Assert.*;

public class CosineSimilarityTests {

    @Test
    public void cosineSimilaritySameImageTest() {
        Image img1 = new Image("resources/15088.jpg");
        Image img2 = new Image("resources/15088.jpg");
        double expectedSimilarity = 1.0;

        assertEquals(expectedSimilarity, cosineSimilarity(img1, img2), 0.00001);
    }

    @Test
    public void cosineSimilaritySimpleSameImageTest() {
        Color[][] colorMatrix1 = new Color[][] {{Color.GREEN, Color.BLUE},
            {Color.GREEN, Color.RED}};
        Color[][] colorMatrix2 = new Color[][] {{Color.GREEN, Color.BLUE},
            {Color.GREEN, Color.RED}};

        int height = 2;

        Image img1 = Image.makeImageFromColorArray(colorMatrix1, height);
        Image img2 = Image.makeImageFromColorArray(colorMatrix2, height);
        double expectedSimilarity = 1.0;

        assertEquals(expectedSimilarity, cosineSimilarity(img1, img2), 0.00001);
    }

    @Test
    public void cosineSimilarityTest() {
        Color[][] colorMatrix1 = new Color[][] {{Color.GREEN, Color.BLUE},
            {Color.GREEN, Color.RED}};
        Color[][] colorMatrix2 = new Color[][] {{Color.BLUE, Color.PINK},
            {Color.GREEN, Color.RED}};

        int height = 2;

        Image img1 = Image.makeImageFromColorArray(colorMatrix1, height);
        Image img2 = Image.makeImageFromColorArray(colorMatrix2, height);

        double expectedSimilarity = 0.64471;

        assertEquals(expectedSimilarity, cosineSimilarity(img1, img2), 0.00001);
    }


    @Test
    public void cosineSimilarity2BlackImageTest() {
        Image img1 = Image.makeSolidColorImage(Color.BLACK, 5, 5);
        Image img2 = Image.makeSolidColorImage(Color.BLACK, 5, 5);
        double expectedSimilarity = 1.0;

        assertEquals(expectedSimilarity, cosineSimilarity(img1, img2), 0.00001);
    }

    @Test
    public void cosineSimilarity1BlackImageTest() {

        Image img1 = Image.makeSolidColorImage(Color.BLACK, 5, 5);
        Image img2 = Image.makeSolidColorImage(Color.RED, 5, 5);
        double expectedSimilarity = 0.0;

        assertEquals(expectedSimilarity, cosineSimilarity(img1, img2), 0.00001);
    }

    @Test
    public void cosineSimilarity1BlackImageTestSwitchOrder() {
        Image img1 = Image.makeSolidColorImage(Color.BLACK, 5, 5);
        Image img2 = Image.makeSolidColorImage(Color.RED, 5, 5);
        double expectedSimilarity = 0.0;

        assertEquals(expectedSimilarity, cosineSimilarity(img2, img1), 0.00001);
    }

}


