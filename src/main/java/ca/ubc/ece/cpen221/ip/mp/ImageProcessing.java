package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;

import java.awt.Color;

/**
 * This class provides some simple operations involving
 * more than one image.
 */
public class ImageProcessing {

    /**
     * Compute the cosine similarity between two images.
     *
     * @param img1: the first image, is not null.
     * @param img2: the second image, in not null and matches img1 in dimensions.
     * @return if both images are entirely black returns 1,
     * if one image is entirely black and the other is not returns 0,
     * otherwise returns the cosine similarity between the Images
     * referenced by img1 and img2.
     */
    public static double cosineSimilarity(Image img1, Image img2) {

        if (isAllBlackImage(img1) && isAllBlackImage(img2)) {
            return 1.0;
        } else if (isAllBlackImage(img1) && !isAllBlackImage(img2) ||
            !isAllBlackImage(img1) && isAllBlackImage(img2)) {
            return 0.0;
        } else {
            double cosTheta;
            ImageTransformer imgTransformer1 = new ImageTransformer(img1);
            ImageTransformer imgTransformer2 = new ImageTransformer(img2);

            Image grayscale1 = imgTransformer1.grayscale();
            Image grayscale2 = imgTransformer2.grayscale();


            int[] vector1 = imageToVector(grayscale1);
            int[] vector2 = imageToVector(grayscale2);

            double magnitude1 = magnitude(vector1);
            double magnitude2 = magnitude(vector2);
            long dotProd = dotProduct(vector1, vector2);

            cosTheta = dotProd / (magnitude1 * magnitude2);

            return cosTheta;
        }
    }

    /**
     * Converts a n x m Image to a vector with length N = n*m
     * row 0 appears before row 1 and so on
     *
     * @param image the image to be converted to a vector, is not null
     * @return a vector translation of the image
     */
    private static int[] imageToVector(Image image) {
        int vectorSize = image.width() * image.height();
        int[] vector = new int[vectorSize];
        int vectorIndex = 0;
        Color pixelColor;

        for (int i = 0; i < image.height(); i++) {
            for (int j = 0; j < image.width(); j++) {

                pixelColor = image.get(j, i);
                vector[vectorIndex] = pixelColor.getBlue();
                vectorIndex++;
            }
        }
        return vector;
    }

    /**
     * Calculates the dot product of 2 vectors
     *
     * @param vector1 first vector of the dot product, is not null
     * @param vector2 second vector of the dot product, is not null
     *                matches vector1 in length
     * @return the dot product of vector1 and vector2
     */
    private static long dotProduct(int[] vector1, int[] vector2) {

        long dotProduct = 0;
        long ithVal = 0;

        for (int i = 0; i < vector1.length; i++) {

            ithVal = ((long) vector1[i] * vector2[i]);

            dotProduct += ithVal;

        }
        return dotProduct;
    }

    /**
     * Calculates the magnitude of a vector
     *
     * @param vector the vector who's magnitude will be calculated
     * @return the magnitude of vector
     */
    private static double magnitude(int[] vector) {
        double magnitude = 0;
        long sumSquaredTerms = 0;

        for (int i = 0; i < vector.length; i++) {
            sumSquaredTerms += Math.pow(vector[i], 2);
        }

        magnitude = Math.sqrt(sumSquaredTerms);
        return magnitude;
    }

    /**
     * Checks if an image is entirely black
     *
     * @param img the image to be tested, is not null
     * @return true if
     */
    private static boolean isAllBlackImage(Image img) {

        for (int i = 0; i < img.height(); i++) {
            for (int j = 0; j < img.width(); j++) {
                if (!img.get(i, j).equals(Color.black)) {
                    return false;
                }
            }
        }
        return true;
    }

}
