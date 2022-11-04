package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import ca.ubc.ece.cpen221.ip.core.ImageProcessingException;
import ca.ubc.ece.cpen221.ip.core.Rectangle;

import java.awt.Color;
import java.awt.Point;
import java.io.*;
import java.util.*;

/**
 * This datatype (or class) provides operations for transforming an image.
 *
 * <p>The operations supported are:
 * <ul>
 *     <li>The {@code ImageTransformer} constructor generates an instance of an image that
 *     we would like to transform;</li>
 *     <li></li>
 * </ul>
 * </p>
 */

public class ImageTransformer {

    private Image image;
    private int width;
    private int height;

    /**
     * Creates an ImageTransformer with an image. The provided image is
     * <strong>never</strong> changed by any of the operations.
     *
     * @param img is not null
     */
    public ImageTransformer(Image img) {

        image = new Image(img);
        width = img.width();
        height = img.height();
    }

    /**
     * Obtain the grayscale version of the image.
     *
     * @return the grayscale version of the instance.
     */
    public Image grayscale() {
        Image gsImage = new Image(width, height);
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                Color color = image.get(col, row);
                Color gray = Image.toGray(color);
                gsImage.set(col, row, gray);
            }
        }
        return gsImage;
    }

    /**
     * Obtain a version of the image with only the red colours.
     *
     * @return a reds-only version of the instance.
     */
    public Image red() {
        Image redImage = new Image(width, height);
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                int originalPixel = image.getRGB(col, row);
                int alpha = (originalPixel >> 24) & 0xFF;
                int red = (originalPixel >> 16) & 0xFF;
                int desiredColor = (alpha << 24) | (red << 16) | (0 << 8) | (0);
                redImage.setRGB(col, row, desiredColor);
            }
        }
        return redImage;
    }

    /**
     * Obtains the mirrored version of an image.
     *
     * @return the mirror image of the instance.
     */
    public Image mirror() {
        Image mirrorImage = new Image(width, height);
        int row = 0;
        int left = 0;
        int right = width - 1;

        while (row < height) {
            while (left < right) {
                Color leftColour = image.get(left, row);
                Color rightColour = image.get(right, row);

                mirrorImage.set(left, row, rightColour);
                mirrorImage.set(right, row, leftColour);

                left++;
                right--;

                if (left == right) {
                    Color middleColour = image.get(left, row);
                    mirrorImage.set(left, row, middleColour);
                }
            }
            left = 0;
            right = width - 1;
            row++;
        }
        return mirrorImage;
    }

    /**
     * <p>Returns the negative version of an instance.<br />
     * If the colour of a pixel is (r, g, b) then the colours of the same pixel
     * in the negative of the image are (255-r, 255-g, 255-b).</p>
     *
     * @return the negative of the instance.
     */
    public Image negative() {
        Color inColor;
        Color outColor;

        int red, green, blue;

        Image negativeImage = new Image(this.image);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                inColor = negativeImage.get(i, j);
                red = 255 - inColor.getRed();
                green = 255 - inColor.getGreen();
                blue = 255 - inColor.getBlue();

                outColor = new Color(red, green, blue);

                negativeImage.set(i, j, outColor);
            }
        }
        return negativeImage;
    }

    /**
     * <p>Returns the posterized version of an instance.<br />
     * For each pixel, each colour is analyzed independently to produce a new image as follows:
     * <ul>
     * <li>if the value of the colour is between 0 and 64 (limits inclusive), set it to 32;</li>
     * <li>if the value of the colour is between 65 and 128, set it to 96;</li>
     * <li>if the value of the colour is between 129 and 255, set it to 222.</li>
     * </ul>
     * </p>
     *
     * @return the posterized version of the instance.
     */
    public Image posterize() {
        int[] rgb = {0, 0, 0};
        Image output = new Image(image.width(),image.height());
        for (int i = 0; i < image.width(); i++) {
            for (int j = 0; j < image.height(); j++) {
                rgb[0] = image.get(i, j).getRed();
                rgb[1] = image.get(i, j).getGreen();
                rgb[2] = image.get(i, j).getBlue();

                for (int k = 0; k < 3; k++) {
                    if (rgb[k] <= 64) {
                        rgb[k] = 32;
                    } else if (rgb[k] <= 128) {
                        rgb[k] = 96;
                    } else {
                        rgb[k] = 222;
                    }
                }
                Color tempColor = new Color(rgb[0], rgb[1], rgb[2]);
                output.setRGB(i, j, tempColor.getRGB());
            }
        }
        return output;
    }

    /**
     * Clip the image given a rectangle that represents the region to be retained.
     *
     * @param clippingBox the rectangle that represents the region to be retained.
     *                    x and y coordinates of this rectangle correspond to
     *                    the col and row respectively of pixels in the image.
     *                    is not null.
     * @return a clipped version of the instance.
     * @throws ImageProcessingException if the clippingBox does not fit completely
     *                                  within the image.
     */
    public Image clip(Rectangle clippingBox) throws ImageProcessingException {

        if (clippingBox.xBottomRight > image.width() || clippingBox.yBottomRight > image.height()) {
            throw new ImageProcessingException();
        } else {

            int xTopLeft = clippingBox.xTopLeft;
            int yTopLeft = clippingBox.yTopLeft;
            int xBottomRight = clippingBox.xBottomRight;
            int yBottomRight = clippingBox.yBottomRight;

            Image clippedImage =
                    new Image(xBottomRight - xTopLeft + 1, yBottomRight - yTopLeft + 1);

            for (int i = 0; i < clippedImage.width(); i++) {
                for (int j = 0; j < clippedImage.height(); j++) {
                    clippedImage.set(i, j, image.get(i + xTopLeft, j + yTopLeft));
                }
            }
            return clippedImage;
        }
    }


    /**
     * Denoise an image by replacing each pixel by the median value of that pixel and
     * all its neighbouring pixels. During this process, each colour channel is handled
     * separately.
     *
     * @return a denoised version of the instance.
     */
    public Image denoise() {
        Image deNoised = new Image(image.width(), image.height());
        for (int i = 0; i < image.width(); i++) {
            for (int j = 0; j < image.height(); j++) {
                deNoised.set(i, j, median(getNeighbors(i, j)));
            }
        }
        return deNoised;
    }

    /**
     * Returns a weathered version of the image by replacing each pixel by the minimum value
     * of that pixel and all its neighbouring pixels. During this process, each colour channel
     * is handled separately.
     *
     * @return a weathered version of the image.
     */
    public Image weather() {
        Image weatheredImage = new Image(image.width(), image.height());
        for (int i = 0; i < image.width(); i++) {
            for (int j = 0; j < image.height(); j++) {
                weatheredImage.set(i, j, getNeighbors(i, j).getMin());
            }
        }
        return weatheredImage;
    }

    /**
     * Return a block paint version of the instance by treating the image as a
     * sequence of squares of a given size and replacing all pixels in a square
     * by the average value of all pixels in that square.
     * During this process, each colour channel is handled separately.
     *
     * @param blockSize the dimension of the square block, > 1.
     * @return the block paint version of the instance
     * When the original image is not a perfect multiple of blockSize * blockSize,
     * the bottom rows and right columns are obtained by averaging the pixels that
     * fit the smaller rectangular regions. For example, if we have a 642 x 642 size
     * original image and the block size is 4 x 4 then the bottom two rows will use
     * 2 x 4 blocks, the rightmost two columns will use 4 x 2 blocks, and the
     * bottom-right corner will use a 2 x 2 block.
     */
    public Image blockPaint(int blockSize) {
        Image toBlockPaint = new Image(image);
        for (int i = 0; i < image.width(); i += blockSize) {
            for (int j = 0; j < image.height(); j += blockSize) {
                averageBlock(toBlockPaint, blockSize, j, i);
            }
        }
        return toBlockPaint;
    }

    /**
     * Rotate an image by the given angle (degrees) about the centre of the image.
     * The centre of an image is the pixel at (width/2, height/2). The new regions
     * that may be created are given the colour white (<code>#ffffff</code>) with
     * maximum transparency (alpha = 255).
     *
     * @param degrees the angle to rotate the image by, 0 <= degrees <= 360.
     * @return a rotate version of the instance.
     */
    public Image rotate(double degrees) {
        Image original_image = image;

        int original_width = width;
        int original_height = height;


        int new_width = (int) (Math.abs(width * Math.cos(degrees * Math.PI / 180)) +
                Math.abs(height * Math.sin(degrees * Math.PI / 180)));
        int new_height = (int) (Math.abs(width * Math.sin(degrees * Math.PI / 180)) +
                Math.abs(height * Math.cos(degrees * Math.PI / 180)));


        Image outImage = Image.makeSolidColorImage(Color.WHITE, new_width, new_height);

        for (int col = 0; col < new_width; col++) {
            for (int row = 0; row < new_height; row++) {
                int original_x = (int) ((col - new_width / 2) * Math.cos(degrees * Math.PI / 180) +
                        (row - new_height / 2) * Math.sin(degrees * Math.PI / 180) +
                        original_width / 2);
                int original_y = (int) (-(col - new_width / 2) * Math.sin(degrees * Math.PI / 180) +
                        (row - new_height / 2) * Math.cos(degrees * Math.PI / 180) +
                        original_height / 2);
                if (original_x >= 0 && original_y >= 0
                        && original_x < original_width
                        && original_y < original_height) {
                    outImage.set(col, row, original_image.get(original_x, original_y));
                }
            }
        }

        return outImage;
    }

    /**
     * Compute the discrete Fourier transform of the image and return the
     * amplitude and phase matrices as a DFTOutput instance.
     *
     * @return the amplitude and phase of the DFT of the instance.
     */
    public DFTOutput dft() {
        double real;
        double imag;
        double[][] amplitudes = new double[image.width()][image.height()];
        double[][] phases = new double[image.width()][image.height()];
        Image grayImage = grayscale();

        for (int i = 0; i < image.width(); i++) {
            for (int j = 0; j < image.height(); j++) {
                real = 0.0;
                imag = 0.0;
                for (int k = 0; k < image.width(); k++) {
                    for (int l = 0; l < image.height(); l++) {
                        real += Image.intensity(grayImage.get(k, l)) * Math.cos(2 * Math.PI *
                                ((i + 0.0) * k / image.width() + (j + 0.0) * l / image.height()));
                        imag -= Image.intensity(grayImage.get(k, l)) * Math.sin(2 * Math.PI *
                                ((i + 0.0) * k / image.width() + (j + 0.0) * l / image.height()));
                    }
                }
                amplitudes[i][j] = Math.sqrt((real * real) + (imag * imag));
                phases[i][j] = Math.atan(imag / real);
            }
        }
        return new DFTOutput(amplitudes, phases);
    }

    /**
     * Replaces a background screen with a provided image.
     * <p>
     * This operation identifies the largest connected region of the image that matches
     * <code>screenColour</code> exactly. This operation determines a rectangle that bounds
     * the "green screen" region and overlays the <code>backgroundImage</code> over that
     * rectangle by aligning the top-left corner of the image with the top-left corner of the
     * rectangle. After determining the screen region, all pixels in that region matching
     * <code>screenColour</code> are replaced with corresponding pixels from
     * <code>backgroundImage</code>.
     * <p>
     * If <code>backgroundImage</code> is smaller
     * than the screen then the image is tiled over the screen.
     * If there is more than one connected region of the same size (number of pixels)
     * than the upper-left most region will be bounded and replaced
     *
     * @param screenColour    the colour of the background screen, is not null
     * @param backgroundImage the image to replace the screen with, is not null
     * @return an image with provided image replacing the background screen
     * of the specified colour, tiling the screen with the background image if the
     * background image is smaller than the screen size. If there are no pixels that
     * match screenColor, returns the original image.
     */
    public Image greenScreen(Color screenColour, Image backgroundImage) {

        HashSet largestRegion = new HashSet<>(getLargestGreenScreenRegion(screenColour));

        if (largestRegion.isEmpty()) {
            return new Image(this.image);
        }

        CustomRectangle replacementBox = makeReplacementBox(largestRegion);
        Image greenScreenedImg;
        if (needsTiling(backgroundImage, replacementBox)) {
            greenScreenedImg =
                    new Image(tileImage(this.image, backgroundImage, replacementBox, screenColour));
        } else {
            greenScreenedImg = new Image(replaceGivenRectangle(this.image, backgroundImage,
                    replacementBox, screenColour));
        }
        return greenScreenedImg;
    }

    /**
     * Finds the largest connected region of pixels that exactly match
     * screenColour and creates a set that contains all pixels this region
     *
     * @param screenColour pixels will be checked to see if they match this color
     * @return a set that contains all the pixels, represented as point,
     * within the largest connected region
     */
    private HashSet<Point> getLargestGreenScreenRegion(Color screenColour) {
        HashSet<Point> checkedPoints = new HashSet();
        HashSet<Point> largestRegion = new HashSet();


        for (int i = 0; i < image.width(); i++) {
            for (int j = 0; j < image.height(); j++) {
                Point currentPixel = new Point(i, j);
                if (checkedPoints.contains(currentPixel)) {

                } else if (screenColour.equals(image.get(i, j))) {
                    checkedPoints.add(currentPixel);
                    HashSet<Point> potentialLargestRegion;
                    potentialLargestRegion = new HashSet<>(
                            this.addToPotentialLargestRegion(checkedPoints, currentPixel));

                    if (potentialLargestRegion.size() > largestRegion.size()) {
                        largestRegion = new HashSet<>(potentialLargestRegion);
                    }
                } else {
                    checkedPoints.add(currentPixel);
                }
            }

        }


        return new HashSet<>(largestRegion);
    }


    /**
     * Takes a pixel represented as a point and checks if surrounding neighbours
     * match the colour of this pixel. Adds matching pixels to a set of matching pixels,
     * and adds all checked pixels to a set of checked pixels.
     *
     * @param checkedPoints the set of pixels which have been checked
     * @param pixel         a point which represents a pixel in the image
     *                      must have x >= 0 and y >= 0
     * @return a set containing all of the connected points that are the same colour as pixel
     */
    private HashSet<Point> addToPotentialLargestRegion(HashSet<Point> checkedPoints,
                                                       Point pixel) {

        HashSet<Point> potentialLargestRegion = new HashSet<>();
        HashSet<Point> matchesToCheck = new HashSet<>();
        matchesToCheck.add(pixel);


        while (!matchesToCheck.isEmpty()) {
            Iterator<Point> matchesToCheckIterator = matchesToCheck.iterator();
            Point currentPixel = new Point(matchesToCheckIterator.next());
            matchesToCheckIterator.remove();
            potentialLargestRegion.add(new Point(currentPixel));
            checkedPoints.add(new Point(currentPixel));

            HashSet<Point> setOfNeighbours =
                    new HashSet<>(this.getMatchingNeighbours(currentPixel, checkedPoints));

            if (setOfNeighbours.isEmpty()) {

            } else {
                Iterator<Point> setOfNeighboursIterator = setOfNeighbours.iterator();
                while (setOfNeighboursIterator.hasNext()) {
                    Point nextPixel = new Point(setOfNeighboursIterator.next());
                    if (checkedPoints.contains(nextPixel)) {

                    } else {
                        matchesToCheck.add(new Point(nextPixel));
                    }
                }
            }
        }
        return new HashSet<>(potentialLargestRegion);
    }


    /**
     * Creates a HashSet of the neighbours of a pixel that match the color of the pixel
     *
     * @param p             pixel who's neighbours will be checked represented as a point
     *                      must have x and y coordinate greater than or equal to 0
     * @param checkedPoints the set of points that have already been checked
     *                      adds all checked neighbours that do not match to this set
     * @return a HashSet containing all neighbouring pixels who's color
     * matches the given pixel. Does not include the given pixel.
     * Returns empty HashSet if no matching neighbours
     */
    private HashSet<Point> getMatchingNeighbours(Point p, HashSet<Point> checkedPoints) {
        int startX = Math.max(0, p.x - 1);
        int startY = Math.max(0, p.y - 1);
        int endX = Math.min(image.width() - 1, p.x + 1);
        int endY = Math.min(image.height() - 1, p.y + 1);

        HashSet<Point> matchingNeighbours = new HashSet<>();

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                if (p.x == i && p.y == j) {

                } else if (image.get(p.x, p.y).equals(image.get(i, j))) {
                    matchingNeighbours.add(new Point(i, j));
                } else {
                    checkedPoints.add(new Point(i, j));
                }
            }
        }
        return new HashSet<>(matchingNeighbours);
    }

    /**
     * Creates a rectangle that contains all points within a set of Points
     *
     * @param region the HashSet of Points to be enclosed
     *               is not null and is not empty
     * @return a customRectangle that encloses all points in the HashSet
     */
    private CustomRectangle makeReplacementBox(HashSet<Point> region) {
        int xTopLeft = getMinX(region);
        int yTopLeft = getMinY(region);
        int xBottomRight = getMaxX(region);
        int yBottomRight = getMaxY(region);

        CustomRectangle replacementBox = new CustomRectangle(xTopLeft, yTopLeft,
                xBottomRight, yBottomRight);

        return replacementBox;
    }

    /**
     * Finds the minimum X value within a set of points
     *
     * @param pointSet the Hashset of Points
     *                 is not null and is not empty
     * @return the smallest x value of all points in the set
     */
    private static int getMinX(HashSet<Point> pointSet) {
        Iterator<Point> iterator = pointSet.iterator();
        int smallestX = iterator.next().x;

        for (Point p : pointSet) {
            if (p.x < smallestX) {
                smallestX = p.x;
            }
        }
        return smallestX;
    }

    /**
     * Finds the minimum Y value within a set of points
     *
     * @param pointSet the Hashset of Points
     *                 is not null and is not empty
     * @return the smallest Y value of all points in the set
     */
    private static int getMinY(HashSet<Point> pointSet) {
        Iterator<Point> iterator = pointSet.iterator();
        int smallestY = iterator.next().y;

        for (Point p : pointSet) {
            if (p.y < smallestY) {
                smallestY = p.y;
            }
        }
        return smallestY;
    }

    /**
     * Finds the maximum X value within a set of points
     *
     * @param pointSet the Hashset of Points
     *                 is not null and is not empty
     * @return the largest X value of all points in the set
     */
    private static int getMaxX(HashSet<Point> pointSet) {
        Iterator<Point> iterator = pointSet.iterator();
        int biggestX = iterator.next().x;

        for (Point p : pointSet) {
            if (p.x > biggestX) {
                biggestX = p.x;
            }
        }
        return biggestX;
    }


    /**
     * Finds the maximum X value within a set of points
     *
     * @param pointSet the Hashset of Points
     *                 is not null and is not empty
     * @return the largest X value of all points in the set
     */
    private static int getMaxY(HashSet<Point> pointSet) {
        Iterator<Point> iterator = pointSet.iterator();
        int biggestY = iterator.next().y;

        for (Point p : pointSet) {
            if (p.y > biggestY) {
                biggestY = p.y;
            }
        }
        return biggestY;
    }


    /**
     * Replaces the pixels of an image inside a bounding region that match a given colour
     * with pixels from a replacement image. Determines how the image needs to be tiled
     * (vertically, horizontally, or both), and tiles the image
     * starting with the top left corner of the replacement image
     *
     * @param mainImage        the image that will have its area replaced
     *                         is not null
     * @param replacementImage the image that will be used to replace
     *                         is not null
     * @param replacementBox   the box that the main image will be replaced inside of
     *                         is not null and is not bigger than original image
     * @param screenColour     the colour of the pixels that will be replaced
     * @return a new image with the pixels matching a given colour
     * inside of the replacement box replaced with pixels from the replacement image,
     * tiled as necessary to fit the entire box
     */
    private static Image tileImage(Image mainImage, Image replacementImage,
                                   CustomRectangle replacementBox, Color screenColour) {


        int xTopLeft = replacementBox.xTopLeft;
        int yTopLeft = replacementBox.yTopLeft;
        int xBottomRight = replacementBox.xBottomRight;
        int yBottomRight = replacementBox.yBottomRight;

        int boxWidth = xBottomRight - xTopLeft;
        int boxHeight = yBottomRight - yTopLeft;

        Image greenScreenedImg = new Image(mainImage);
        int replacementWidth = replacementImage.width();
        int replacementHeight = replacementImage.height();

        if (replacementWidth <= boxWidth
                && replacementHeight <= boxHeight) {
            greenScreenedImg = tileHorizontalAndVertical(greenScreenedImg, replacementImage,
                    replacementBox, screenColour);
        } else if (replacementWidth <= boxWidth) {

            greenScreenedImg = tileHorizontal(greenScreenedImg, replacementImage,
                    replacementBox, screenColour);
        } else if (replacementHeight <= boxHeight) {

            greenScreenedImg = tileVertical(greenScreenedImg, replacementImage,
                    replacementBox, screenColour);
        }
        return new Image(greenScreenedImg);
    }

    /**
     * Replaces the pixels of an image inside a box that match a given colour
     * with pixels from a replacement image. Tiles the replacement image
     * vertically across the box, starting with the top left corner of the replacement image
     *
     * @param mainImage        the image that will have its area replaced
     *                         is not null
     * @param replacementImage the image that will be used to replace
     *                         is not null
     * @param replacementBox   the box that the main image will be replaced inside of
     *                         is not null and is not bigger than original image
     * @param screenColour     the colour of the pixels that will be replaced
     * @return a new image with the pixels matching a given colour
     * inside of the replacement box replaced with pixels from the replacement image,
     * tiled vertically to fit the entire box
     */
    private static Image tileVertical(Image mainImage, Image replacementImage,
                                      CustomRectangle replacementBox, Color screenColour) {
        int xTopLeft = replacementBox.xTopLeft;
        int yTopLeft = replacementBox.yTopLeft;
        int xBottomRight = replacementBox.xBottomRight;
        int yBottomRight = replacementBox.yBottomRight;

        int boxHeight = yBottomRight - yTopLeft;

        Image greenScreenedImg = new Image(mainImage);
        int replacementHeight = replacementImage.height();
        int numVerticalTiles = (boxHeight) / replacementHeight;

        int newYTopLeft = 0;
        int newYBottomRight = 0;

        for (int i = 0; i < numVerticalTiles; i++) {
            newYTopLeft = yTopLeft + i * replacementHeight;
            newYBottomRight = newYTopLeft + replacementHeight - 1;


            CustomRectangle smallRectangle = new CustomRectangle(xTopLeft, newYTopLeft,
                    xBottomRight, newYBottomRight);


            greenScreenedImg = replaceGivenRectangle(greenScreenedImg, replacementImage,
                    smallRectangle, screenColour);
        }

        newYTopLeft = newYBottomRight + 1;
        newYBottomRight = newYTopLeft + (boxHeight % replacementHeight);


        CustomRectangle smallRectangle = new CustomRectangle(xTopLeft,
                newYTopLeft, xBottomRight, newYBottomRight);

        greenScreenedImg = replaceGivenRectangle(greenScreenedImg, replacementImage,
                smallRectangle, screenColour);


        return new Image(greenScreenedImg);
    }

    /**
     * Replaces the pixels of an image inside a box that match a given colour
     * with pixels from a replacement image. Tiles the replacement image
     * horizontally across the box, starting with the top left corner of the replacement image
     *
     * @param mainImage        the image that will have its area replaced
     *                         is not null
     * @param replacementImage the image that will be used to replace
     *                         is not null
     * @param replacementBox   the box that the main image will be replaced inside of
     *                         is not null and is not bigger than original image
     * @param screenColour     the colour of the pixels that will be replaced
     * @return a new image with the pixels matching a given colour
     * inside of the replacement box replaced with pixels from the replacement image,
     * tiled horizontally to fit the entire box
     */
    private static Image tileHorizontal(Image mainImage, Image replacementImage,
                                        CustomRectangle replacementBox, Color screenColour) {

        int xTopLeft = replacementBox.xTopLeft;
        int yTopLeft = replacementBox.yTopLeft;
        int xBottomRight = replacementBox.xBottomRight;
        int yBottomRight = replacementBox.yBottomRight;

        int boxWidth = xBottomRight - xTopLeft;

        Image greenScreenedImg = new Image(mainImage);
        int replacementWidth = replacementImage.width();

        int numHorizontalTiles = boxWidth / replacementWidth;

        int newXTopLeft = 0;
        int newXBottomRight = 0;

        for (int j = 0; j < numHorizontalTiles; j++) {
            newXTopLeft = xTopLeft + j * replacementWidth;
            newXBottomRight = newXTopLeft + replacementWidth - 1;


            CustomRectangle smallRectangle = new CustomRectangle(newXTopLeft, yTopLeft,
                newXBottomRight, yBottomRight);


            greenScreenedImg = replaceGivenRectangle(greenScreenedImg, replacementImage,
                smallRectangle, screenColour);
        }

        newXTopLeft = newXBottomRight + 1;
        newXBottomRight = newXTopLeft + (boxWidth % replacementWidth);


        CustomRectangle smallRectangle = new CustomRectangle(newXTopLeft,
            yTopLeft, newXBottomRight, yBottomRight);


        greenScreenedImg = replaceGivenRectangle(greenScreenedImg, replacementImage,
            smallRectangle, screenColour);

        return new Image(greenScreenedImg);

    }

    /**
     * Replaces the pixels of an image inside a box that match a given colour
     * with pixels from a replacement image. Tiles the replacement image
     * both vertically and horizontally across the box, starting with the
     * top left corner of the replacement image
     *
     * @param mainImage        the image that will have its area replaced
     *                         is not null
     * @param replacementImage the image that will be used to replace
     *                         is not null
     * @param replacementBox   the box that the main image will be replaced inside of
     *                         is not null and is not bigger than original image
     * @param screenColour     the colour of the pixels that will be replaced
     * @return a new image with the pixels matching a given colour
     * inside of the replacement box replaced with pixels from the replacement image,
     * tiled both horizontally and vertically to fit the entire box
     */
    private static Image tileHorizontalAndVertical(Image mainImage, Image replacementImage,
                                                   CustomRectangle replacementBox,
                                                   Color screenColour) {
        int xTopLeft = replacementBox.xTopLeft;
        int yTopLeft = replacementBox.yTopLeft;
        int xBottomRight = replacementBox.xBottomRight;
        int yBottomRight = replacementBox.yBottomRight;

        int boxWidth = xBottomRight - xTopLeft;
        int boxHeight = yBottomRight - yTopLeft;

        Image greenScreenedImg = new Image(mainImage);
        int replacementWidth = replacementImage.width();
        int replacementHeight = replacementImage.height();


        int numHorizontalTiles = boxWidth / replacementWidth;
        int numVerticalTiles = boxHeight / replacementHeight;

        int newXTopLeft = 0;
        int newXBottomRight = xBottomRight;
        int newYTopLeft = 0;
        int newYBottomRight = 0;

        for (int i = 0; i < numVerticalTiles; i++) {
            newYTopLeft = yTopLeft + i * replacementHeight;
            newYBottomRight = newYTopLeft + replacementHeight - 1;


            CustomRectangle xRectangle = new CustomRectangle(xTopLeft, newYTopLeft,
                xBottomRight, newYBottomRight);


            greenScreenedImg =
                tileHorizontal(greenScreenedImg, replacementImage, xRectangle, screenColour);
        }

        newYTopLeft = newYBottomRight + 1;
        newYBottomRight = newYTopLeft + (boxHeight % replacementHeight);

        for (int j = 0; j < numHorizontalTiles; j++) {


            CustomRectangle xRectangle = new CustomRectangle(xTopLeft, newYTopLeft,
                xBottomRight, newYBottomRight);


            greenScreenedImg =
                tileHorizontal(greenScreenedImg, replacementImage, xRectangle, screenColour);

        }

        newXTopLeft = newXBottomRight - (boxWidth % replacementWidth);


        CustomRectangle smallRectangle = new CustomRectangle(newXTopLeft, newYTopLeft,
            newXBottomRight, newYBottomRight);

        greenScreenedImg = replaceGivenRectangle(greenScreenedImg, replacementImage,
            smallRectangle, screenColour);

        return new Image(greenScreenedImg);
    }

    /**
     * Replaces the pixels of an image inside a box that match a given colour
     * with pixels from a replacement image, starting with the
     * top left corner of the replacement image
     *
     * @param mainImage        the image that will have its area replaced
     *                         is not null
     * @param replacementImage the image that will be used to replace
     *                         is not null
     * @param replacementBox   the box that the main image will be replaced inside of
     *                         is not null and is not bigger than original image
     *                         and is smaller or equal in size to the replacementImage
     * @param screenColour     the colour of the pixels that will be replaced
     * @return a new image with the pixels matching a given colour
     * inside of the replacement box replaced with pixels from the replacement image
     */
    private static Image replaceGivenRectangle(Image mainImage, Image replacementImage,
                                               CustomRectangle replacementBox, Color screenColour) {

        int xTopLeft = replacementBox.xTopLeft;
        int yTopLeft = replacementBox.yTopLeft;
        int xBottomRight = replacementBox.xBottomRight;
        int yBottomRight = replacementBox.yBottomRight;

        Image greenScreenedImg = new Image(mainImage);

        for (int i = xTopLeft; i <= xBottomRight; i++) {
            for (int j = yTopLeft; j <= yBottomRight; j++) {
                if (greenScreenedImg.get(i, j).equals(screenColour)) {
                    greenScreenedImg.set(i, j,
                        replacementImage.get(i - xTopLeft, j - yTopLeft));
                }
            }
        }
        return greenScreenedImg;
    }

    /**
     * Checks if a given background image will need to be tiled when
     * using it for a green screen background
     *
     * @param backgroundImage the background image
     *                        is not null
     * @param replacementBox  the box which will be used to green screen
     *                        is not null
     * @return true if the image will need to be tiled, false otherwise
     */
    private static boolean needsTiling(Image backgroundImage, CustomRectangle replacementBox) {
        int xTopLeft = replacementBox.xTopLeft;
        int yTopLeft = replacementBox.yTopLeft;
        int xBottomRight = replacementBox.xBottomRight;
        int yBottomRight = replacementBox.yBottomRight;

        if (backgroundImage.width() < xBottomRight - xTopLeft
            || backgroundImage.height() < yBottomRight - yTopLeft) {

            return true;
        } else {
            return false;
        }

    }

    /**
     * Align (appropriately rotate) an image of text that was improperly aligned.
     * This transformation can work properly only with text images.
     *
     * @return the aligned image.
     */
    public Image alignTextImage() throws ImageProcessingException {
        int lgWidth;
        int lgHeight;
        int medWidth = image.width();
        int medHeight = image.height();
        int newWidth;
        int newHeight;
        int topLeftX;
        int topLeftY;
        int bottomRightX;
        int bottomRightY;

        TextRotate rotator = new TextRotate(dft());
        double thetaRad = rotator.getRotationAngle();
        System.out.println(thetaRad * 180 / Math.PI);

        Image preClip = rotate(-thetaRad * 180.0 / Math.PI);
        lgWidth = preClip.width();
        lgHeight = preClip.height();

        newWidth =
            (int) (((medWidth) / (Math.cos(thetaRad))) - medHeight / (1 - 1 / Math.sin(thetaRad)));
        newHeight = (int) ((medHeight - newWidth * Math.cos(thetaRad)) / (Math.sin(thetaRad)));

        topLeftX = (int) (0.5 * (lgWidth - newWidth));
        topLeftY = (int) (0.5 * (lgHeight - newHeight));
        bottomRightX = lgWidth - topLeftX;
        bottomRightY = lgHeight - topLeftY;
        return preClip;
    }

    /**
     * Returns the Neighboors object containing as many
     * "neighbours" are available to a pixel at a given location
     *
     * @param x The column position of a pixel
     * @param y The row position of a pixel
     * @return A neighbours object, and the amount of neighbours that a given pixel has
     */
    private Neighbors getNeighbors(int x, int y) {
        int startX = Math.max(0, x - 1);
        int startY = Math.max(0, y - 1);
        int endX = Math.min(image.width() - 1, x + 1);
        int endY = Math.min(image.height() - 1, y + 1);
        int[][] neighbors = new int[9][3];
        int count = 0;

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                neighbors[count][0] = image.get(i, j).getRed();
                neighbors[count][1] = image.get(i, j).getGreen();
                neighbors[count][2] = image.get(i, j).getBlue();
                count++;
            }
        }
        return new Neighbors(neighbors, count);
    }

    /**
     * Returns a median Color object corresponding to all three of R, G, and B colours
     * of a neighbour object and all its corresponding neighbours
     *
     * @param n a Neighbors object
     * @return a median Color object corresponding to all three of R, G, and B colours
     * of a neighbour object and all its corresponding neighbours
     */
    private Color median(Neighbors n) {
        int[][] neighbors = n.getNeighbors();
        int count = n.getCount();
        int[][] invertedNeighbors = new int[neighbors[0].length][count];
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < neighbors[0].length; j++) {
                invertedNeighbors[j][i] = neighbors[i][j];
            }
        }

        return new Color(median(invertedNeighbors[0]), median(invertedNeighbors[1]),
            median(invertedNeighbors[2]));
    }

    /**
     * Returns the median integer corresponding to an array of integers
     *
     * @param numbers an array of integers which you would like the median of
     * @return the median value of the array of integers
     */
    private int median(int[] numbers) {
        Arrays.sort(numbers);
        if (numbers.length % 2 == 0) {
            return (int) (.5 * (numbers[numbers.length / 2] + numbers[(numbers.length / 2) - 1]));
        } else {
            return numbers[numbers.length / 2];
        }
    }

    /**
     * Creates a block using the average R G and V values available
     *
     * @param toBlock   the Image object which we want to send to a block
     * @param dimension The dimensions we want our block to have
     * @param top       The location of the top of the block
     * @param left      The leftmost location of the block
     */
    private void averageBlock(Image toBlock, int dimension, int top, int left) {
        int[] pixels = new int[3];
        int count = 0;
        for (int i = left; i < dimension + left && i < toBlock.width(); i++) {
            for (int j = top; j < dimension + top && j < toBlock.height(); j++) {
                pixels[0] += toBlock.get(i, j).getRed();
                pixels[1] += toBlock.get(i, j).getGreen();
                pixels[2] += toBlock.get(i, j).getBlue();
                count++;
            }
        }
        for (int k = 0; k < 3; k++) {
            pixels[k] /= count;
        }
        for (int l = left; l < dimension + left && l < toBlock.width(); l++) {
            for (int m = top; m < dimension + top && m < toBlock.height(); m++) {
                toBlock.set(l, m, new Color(pixels[0], pixels[1], pixels[2]));
            }
        }
    }

    /**
     * Given a CSV file, this method reads from it and converts it into a 2D integer array
     *
     * @param fileName The name of the CSV file
     * @return a 2D integer array corresponding to the values within the CSV file
     */
    public static int[][] readImageFromCSV(String fileName) {
        int[][] output;
        ArrayList<String> lines = new ArrayList<>();
        try {
            File fileToRead = new File(fileName);
            Scanner scanner = new Scanner(fileToRead);
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            return null;
        }
        int count = 0;
        for (int i = 0; i < lines.get(0).length(); i++) {
            if (lines.get(0).charAt(i) == ',') {
                count++;
            }
        }
        count++;
        output = new int[lines.size()][count];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < count; j++) {
                output[i][j] = (int)Double.parseDouble(lines.get(i).split(",")[j]);
            }
        }
        return output;
    }

    /**
     * Writes a given 2D array into a user-specified file
     *
     * @param filename the name of the file which the user wants to write to
     * @param input    the 2D array which the user wants written to a file
     * @throws IOException if either input or output are invalid
     */
    public static void writeIntArrayToFile(String filename, int[][] input) throws IOException {
        BufferedWriter fileBuilder = new BufferedWriter(new FileWriter(filename));
        StringBuilder lineBuilder = new StringBuilder();
        for (int i = 0; i < input.length; i++) {
            lineBuilder.setLength(0);
            for (int j = 0; j < input[0].length; j++) {
                if (j != 0) {
                    lineBuilder.append(",");
                }
                lineBuilder.append(input[i][j]);
            }
            lineBuilder.append("\n");
            fileBuilder.write(lineBuilder.toString());
        }
        fileBuilder.flush();
        fileBuilder.close();
    }

}
