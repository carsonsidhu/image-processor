package ca.ubc.ece.cpen221.ip.mp;

import java.awt.*;

/**
 * This datatype represents the pixels which surround a pixel at a given location.
 * These pixels would be located above, beside, and diagonally with respect to
 * the original pixel given
 */

public class Neighbors {
    private final int[][] neighbors;
    private final int countNeighbors;

    /**
     * A constructor method for the Neighbors datatype, generating instance variables
     *
     * @param neighbors a 2D integer array corresponding to the neighbours of a pixel
     * @param count     the amount of neighbours that a pixel has
     */
    public Neighbors(int[][] neighbors, int count) {
        this.neighbors = neighbors;
        this.countNeighbors = count;
    }

    /**
     * Returns the Neighbors datatype
     *
     * @return the Neighbors datatype
     */
    public int[][] getNeighbors() {
        return neighbors;
    }

    /**
     * Returns the amount of neighbours a pixel has
     *
     * @return the amount of neighbours a pixel has
     */
    public int getCount() {
        return countNeighbors;
    }

    /**
     * Returns the minimum-valued RGB colour corresponding to a Neighbor object
     *
     * @return the minimum-valued RGB colour corresponding to a Neighbor object
     */
    public Color getMin() {
        int[] min = new int[3];
        for (int j = 0; j < 3; j++) {
            min[j] = neighbors[0][j];
            for (int i = 1; i < countNeighbors; i++) {
                min[j] = Math.min(min[j], neighbors[i][j]);
            }
        }
        return new Color(min[0], min[1], min[2]);
    }

}
