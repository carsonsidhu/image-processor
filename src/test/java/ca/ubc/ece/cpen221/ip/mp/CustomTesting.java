package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import ca.ubc.ece.cpen221.ip.core.ImageProcessingException;
import ca.ubc.ece.cpen221.ip.core.Rectangle;
import ca.ubc.ece.cpen221.ip.mp.ImageProcessing;

import java.awt.Color;
import java.io.IOException;

import static ca.ubc.ece.cpen221.ip.mp.ImageProcessing.cosineSimilarity;

public class CustomTesting {


    public static void main(String[] args) throws IOException, ImageProcessingException {
        Image input = new Image("resources/12003.jpg");

        //Image input = new Image("resources/skewText_scaled.jpg");
        /*Color[][] inputColorArray = new Color[5][5];
        int[][] intArray = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}, {16, 17, 18, 19, 20}, {21, 22, 23, 24, 25}};
        //int[][] intArray = {{27, 123, 96}, {244, 56, 192}, {77, 41, 12}};
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                inputColorArray[i][j] = new Color(intArray[i][j], intArray[i][j], intArray[i][j]);
            }
        }*/

        input.show();
        Image output = denoiseTest(input);
        output.show();

        //grayscaleTest(input).show();
        //blockPaintTest(input).show();
        //rotateTest(input).show();
        //dftImage(input).show();
        //dftToTerminal(input);


        //clipAndDFT();

        input = new Image("resources/skewText_scaled.jpg");
        postProcessDFT(input).show();


        //Image greenScreenInput = new Image("resources/clapper-green.jpg");
        //greenScreenInput.show();
        //greenScreenTest(greenScreenInput).show();

//        Image img1 = new Image("resources/159045.jpg");
//        Image img2 = new Image("resources/159045.jpg");
//
//        //img1.show();
//
//
//        for(int i = 0; i < img2.width(); i++){
//            for(int j = 0; j < 2; j++) {
//
//                img2.set(i, j, Color.WHITE);
//            }
//
//        }

//        img2.set(45,69,Color.yellow);
//        img2.set(58,100,Color.yellow);
//        img2.set(88,88,Color.yellow);
//        img2.set(8,8,Color.yellow);
//        img2.set(300,73,Color.yellow);
//        img2.set(288,320,Color.yellow);
//        img2.set(7,19,Color.yellow);
//        img2.set(22,87,Color.yellow);

//        img2.show();
//        double test = cosineSimilarity(img1, img2);
//
//        System.out.println(test);
        //double[][] shiftTest = {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}};
        /*double[][] shiftTest = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        double[][] output = DFTOutput.shift(shiftTest);
        for(int i=0; i< shiftTest.length; i++){
            for(int j=0; j<shiftTest[0].length;j++){
                System.out.print(output[i][j] + " ");
            }
            System.out.println(" ");
        }*/


    }//main

    private static Image denoiseTest(Image input) {
        ImageTransformer toDenoise = new ImageTransformer(input);
        return toDenoise.denoise();
    }//denoiseTest

    private static Image grayscaleTest(Image input) {
        ImageTransformer toGrayscale = new ImageTransformer(input);
        return toGrayscale.grayscale();
    }//grayscaleTest

    private static Image blockPaintTest(Image input) {
        ImageTransformer toBlockPaint = new ImageTransformer(input);
        return toBlockPaint.blockPaint(20);
    }//blockPaintTest

    public static Image rotateTest(Image input) {
        ImageTransformer toRotate = new ImageTransformer(input);
        return toRotate.rotate(15);
    }//rotateTest

    /*public static Image dftImage(Image input) throws IOException {
        Image output = new Image(input.width(), input.height());
        ImageTransformer toDFT = new ImageTransformer(input);
        DFTOutput dftOutput = toDFT.dft();
        int[][] intArray = new int[dftOutput.getAmplitude()[0].length][dftOutput.getAmplitude().length];
        for (int i = 0; i < intArray[0].length; i++) {
            for (int j = 0; j < intArray.length; j++) {
                intArray[j][i] = (int)dftOutput.getAmplitude()[i][j];
            }
        }
        ImageTransformer.writeIntArrayToFile("DFT/postDFT.csv", intArray);
        intArray = new int[dftOutput.getAmplitude()[0].length][dftOutput.getAmplitude().length];
        for (int i = 0; i < intArray[0].length; i++) {
            for (int j = 0; j < intArray.length; j++) {
                intArray[j][i] = input.get(i,j).getRed();
            }
        }
        ImageTransformer.writeIntArrayToFile("DFT/sourceFile.csv", intArray);
        return output = dftOutput.print();
    }

    public static void dftToTerminal(Image input) {
        ImageTransformer toDFT = new ImageTransformer(grayscaleTest(input));
        toDFT.dft().printToTerminal();
        //toDFT.dft();
    }*/

    public static Image greenScreenTest(Image input) {
        ImageTransformer greenScreen = new ImageTransformer(input);
        Color screenColor = input.get(0, 0);
        //Image backgroundImage = new Image("resources/IMG_1436.JPG");
        Image backgroundImage = Image.makeSolidColorImage(Color.blue, 100, 100);
        for (int i = 0; i < 100; i++) {
            backgroundImage.set(99, i, Color.BLACK);
            backgroundImage.set(i, 99, Color.BLACK);
            backgroundImage.set(0, i, Color.BLACK);
            backgroundImage.set(i, 0, Color.BLACK);
        }
        //Image backgroundImage = Image.makeSolidColorImage(Color.BLACK,500,500);
        Image output = greenScreen.greenScreen(screenColor, backgroundImage);
        return output;
    }


    public static Image postProcessDFT(Image input) throws ImageProcessingException {
        ImageTransformer rotate = new ImageTransformer(input);
        Image interim = rotate.rotate(60);
        interim.show();
        ImageTransformer postProcess = new ImageTransformer(interim);
        return postProcess.alignTextImage();
    }

    public static void clipAndDFT() throws ImageProcessingException {
        Image originalImg = new Image("resources/15088.jpg");
        //Image expectedImg = new Image("resources/tests/15088-clip-60-100-250-350.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        ImageTransformer toDFT = new ImageTransformer(t.clip(new Rectangle(0, 0, 50, 50)));
        double[][] matrix = toDFT.dft().getAmplitude();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println(" ");
        }


    }//CustomTesting
}
