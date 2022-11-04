package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import ca.ubc.ece.cpen221.ip.core.ImageProcessingException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DFTTests {

    @Test
    public void write() throws IOException {
        Image input = new Image("Resources/12003_scaled.jpg");
        int[][] output = new int[input.width()][input.width()];
        for (int i = 0; i < input.width(); i++) {
            for (int j = 0; j < input.height(); j++) {
                output[i][j] = input.get(i, j).getRed();
            }
        }

        ImageTransformer.writeIntArrayToFile("DFT/12003.csv", output);
    }

    @Test
    public void test_skewText() throws IOException {
        Image img1 = new Image("DFT/skewText_scaled.jpg");
        ImageTransformer x = new ImageTransformer(img1);
        int[][] input = ImageTransformer.readImageFromCSV("DFT/postDFT.csv");
        DFTOutput output = x.dft();
        int[][] ampIntArray = new int[output.getAmplitude()[0].length][output.getAmplitude().length];
        for (int i = 0; i < output.getAmplitude().length; i++) {
            for (int j = 0; j < output.getAmplitude()[0].length; j++) {
                ampIntArray[j][i] = (int) output.getAmplitude()[i][j];
            }
        }
        assertArrayEquals(ampIntArray, input);
    }

    @Test
    public void test_12003() throws IOException {
        Image img1 = new Image("Resources/12003_scaled.jpg");
        ImageTransformer x = new ImageTransformer(img1);
        int[][] inputAmp = ImageTransformer.readImageFromCSV("DFT/12003_Amplitudes_DFT.csv");
        //int[][] inputPha = ImageTransformer.readImageFromCSV("DFT/12003_Phases_DFT.csv");
        DFTOutput output = x.dft();
        int[][] ampIntArray = new int[output.getAmplitude()[0].length][output.getAmplitude().length];
        //int[][] phaIntArray = new int[output.getAmplitude()[0].length][output.getAmplitude().length];
        for (int i = 0; i < output.getAmplitude().length; i++) {
            for (int j = 0; j < output.getAmplitude()[0].length; j++) {
                ampIntArray[j][i] = (int) output.getAmplitude()[i][j] * 100;
                //phaIntArray[j][i] = (int)output.getPhases()[i][j];
                System.out.print(ampIntArray[j][i] + " ");
            }
            System.out.println(" ");
        }
        assertArrayEquals(ampIntArray, inputAmp);
        //assertArrayEquals(phaIntArray, inputPha);
    }

    @Test
    public void align_test_1() throws ImageProcessingException {
        Image img1 = new Image("Resources/skewText_scaled.jpg");
        ImageTransformer align = new ImageTransformer(new ImageTransformer(img1).rotate(60));
        Image output = align.alignTextImage();
        Image doubleRotate = new ImageTransformer(new ImageTransformer(img1).rotate(60)).rotate(-60);
        doubleRotate.show();
        output.show();
        double exp = 1.0;
        System.out.println(ImageProcessing.cosineSimilarity(output,doubleRotate));
        assertEquals(ImageProcessing.cosineSimilarity(doubleRotate,output), 1.0, 0.1);
    }

}
