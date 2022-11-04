package ca.ubc.ece.cpen221.ip.mp;


import ca.ubc.ece.cpen221.ip.core.Image;

import java.awt.*;

/**
 * This datatype represents the rotation of text on an image so that it is centred
 */

public class TextRotate {
    private DFTOutput dft;
    private int whitePointCount = 0;

    /**
     * The constructor method for TextRotate, taking the dft of an image
     * as its only instance variable
     *
     * @param dft the spatial discrete fourier transform of an image
     */
    public TextRotate(DFTOutput dft) {
        this.dft = dft;
    }

    /**
     * Calculates the rotation angle of a given image
     *
     * @return the roatation anlge of a given image
     */
    public double getRotationAngle() {
        Image toFilter = postProcessDFT();
        Image toSlopeFit = filter(175, toFilter);
        toSlopeFit.show();
        int[] xCord = new int[whitePointCount];
        int[] yCord = new int[whitePointCount];
        int cordIndex = 0;

        for (int i = 0; i < toSlopeFit.width(); i++) {
            for (int j = 0; j < toSlopeFit.height(); j++) {
                if (toSlopeFit.get(i, j).getRed() == 255) {
                    xCord[cordIndex] = i;
                    yCord[cordIndex] = j;
                    cordIndex++;
                }
            }
        }
        return getBestAngle(xCord, yCord);
    }

    /**
     * Returns the image that results from a DFT process
     *
     * @return the image resulting from a DFT process
     */
    private Image postProcessDFT() {
        double[][] postLog = logAmplitude();
        double max = getMax(postLog);
        double scale = 255 / max;
        int intensity;
        Color[][] scaledMatrix = new Color[postLog[0].length][postLog.length];
        for (int i = 0; i < postLog.length; i++) {
            for (int j = 0; j < postLog[0].length; j++) {
                intensity = (int) (postLog[i][j] * scale);
                scaledMatrix[j][i] = new Color(intensity, intensity, intensity);
            }
        }
        return Image.makeImageFromColorArray(shift(scaledMatrix), dft.getAmplitude().length);
    }

    /**
     * Calculates the 2D array corresponding to the amplitude of an image
     * after its Discrete Fourier Transform is computed
     *
     * @return the 2D array corresponding to the amplitude of an image
     * after its Discrete Fourier Transform is computed
     */
    private double[][] logAmplitude() {
        double[][] output = new double[dft.getAmplitude().length][dft.getAmplitude()[0].length];
        for (int i = 0; i < dft.getAmplitude().length; i++) {
            for (int j = 0; j < dft.getAmplitude()[0].length; j++) {
                output[i][j] = Math.log10(dft.getAmplitude()[i][j] + 1);
            }
        }
        return output;
    }

    /**
     * Returns a user-specified 2D Color array that has been shifted by a given amount
     *
     * @param input the user-specified 2D colour array to be shifted
     * @return a user-specified 2D Color array that has been shifted by a given amount
     */
    private Color[][] shift(Color[][] input) {
        Color[][] output = new Color[input.length][input[0].length];
        Color[][] interim = new Color[input.length][input[0].length];
        for (int i = 0; i < (int) Math.ceil(input.length / 2.0); i++) {
            for (int j = 0; j < input[0].length; j++) {
                interim[i + input.length / 2][j] = input[i][j];
            }
        }
        for (int i = 0; i < input.length / 2; i++) {
            for (int j = 0; j < input[0].length; j++) {
                interim[i][j] = input[i + (int) Math.ceil(input.length / 2.0)][j];
            }
        }
        for (int j = 0; j < (int) Math.ceil(input[0].length / 2.0); j++) {
            for (int i = 0; i < input.length; i++) {
                output[i][j + input[0].length / 2] = interim[i][j];
            }
        }
        for (int j = 0; j < input[0].length / 2; j++) {
            for (int i = 0; i < input.length; i++) {
                output[i][j] = interim[i][j + (int) Math.ceil(input[0].length / 2.0)];
            }
        }

        return output;
    }

    /**
     * Return the maximum value within a user-specified 2D double array
     *
     * @param input the user-specified 2D double array
     * @return the maximum value within a user-specified 2D double array
     */
    private double getMax(double[][] input) {
        double max = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[0].length; j++) {
                if (input[i][j] > max) {
                    max = input[i][j];
                }
            }
        }
        return max;
    }

    /**
     * Pass a filter over a user-given image
     *
     * @param threshold the threshold to which we want to pass a filter over a pixel
     * @param input     the image which we will be filtering
     * @return the filtered image
     */
    private Image filter(int threshold, Image input) {
        Image output = new Image(input.width(), input.height());
        for (int i = 0; i < input.width(); i++) {
            for (int j = 0; j < input.height(); j++) {
                if (input.get(i, j).getRed() > threshold) {
                    output.set(i, j, new Color(255, 255, 255));
                    whitePointCount++;
                } else {
                    output.set(i, j, new Color(0, 0, 0));
                }
            }
        }
        return output;
    }

    /**
     * Returns the optimal angle for rotation so that text is as centred as possible
     *
     * @param x the columns of an image
     * @param y the rows of an image
     * @return the optimal angle for rotation so that text is as centred as possible
     */
    private double getBestAngle(int[] x, int[] y) {
        double rangeMin = -Math.PI / 2;
        double rangeMax = Math.PI / 2;
        double angleA = 0.0;
        double angleB = 0.0;

        while (rangeMax - rangeMin > 0.0001) {
            angleA = rangeMin + ((rangeMax - rangeMin) / 4);
            angleB = rangeMax - ((rangeMax - rangeMin) / 4);
            if (rSq(angleA, x, y) > rSq(angleB, x, y)) {
                rangeMax = rangeMin + ((rangeMax - rangeMin) / 2);
            } else {
                rangeMin = rangeMax - ((rangeMax - rangeMin) / 2);
            }
        }
        return (angleA + angleB) / 2.0;
    }

    /**
     * Find the rsquared value of a dataset
     *
     * @param angle the angle at which the text is to be rotated by
     * @param x     the columns of an image
     * @param y     the rows of an image
     * @return the rsquared value of a dataset
     */
    private double rSq(double angle, int[] x, int[] y) {
        double SSres = 0.0;
        double SStot = 0.0;
        int sumY = 0;

        for (int i = 0; i < y.length; i++) {
            sumY += y[i];
        }
        double avgY = -(sumY + 0.0) / y.length;

        for (int i = 0; i < x.length; i++) {
            SSres += (-y[i] - expectedFit(angle, x[i])) * (-y[i] - expectedFit(angle, x[i]));
            SStot += (-y[i] - avgY) * (-y[i] - avgY);
        }
        return 1 - (SSres / SStot);
    }

    /**
     * Returns the expected linear relationship of the dft
     *
     * @param angle the angle at which text is to be rotated by
     * @param x the rows of pixels in an image
     * @return
     */
    private double expectedFit(double angle, int x) {
        return Math.tan(-angle + Math.PI / 2) * (x - dft.getAmplitude().length / 2.0) -
            dft.getAmplitude()[0].length / 2.0;
    }
}
