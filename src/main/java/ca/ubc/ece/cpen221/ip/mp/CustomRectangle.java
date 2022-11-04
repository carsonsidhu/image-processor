package ca.ubc.ece.cpen221.ip.mp;

public class CustomRectangle {

    /**
     * This datatype represents a Rectangle
     * in the positive quadrant of the 2D plane. These rectangles match
     * standard image representations with the coordinate values for the
     * top-left corner being smaller than the coordinate values for the
     * bottom-right corner. This datatype also allows rectangles that
     * are lines or points i.e. both x or both y coordinates are the same
     * or all coordinates are the same
     * <p>
     * After an instance is created, the coordinates of the top-left and bottom-right
     * corners can be accessed using the constant fields
     * <code>xTopLeft, yTopLeft, xBottomRight, yBottomRight</code>.
     */

    public final int xTopLeft, yTopLeft;
    public final int xBottomRight, yBottomRight;


    /**
     * Create a new customRectangle
     *
     * @param _xTopLeft:     is the x coordinate of the top-left corner and should be >= 0
     * @param _yTopLeft:     is the y coordinate of the top-left corner and should be >= 0
     * @param _xBottomRight: is the x coordinate of the bottom-right corner and should be >= 0
     * @param _yBottomRight: is the y coordinate of the bottom-right corner and should be >= 0
     */
    public CustomRectangle(int _xTopLeft, int _yTopLeft, int _xBottomRight, int _yBottomRight) {
        if (_xTopLeft < 0 || _yTopLeft < 0 ||
                _xBottomRight < _xTopLeft || _yBottomRight < _yTopLeft) {
            throw new IllegalArgumentException("Invalid rectangle specified.");
        }

        xTopLeft = _xTopLeft;
        yTopLeft = _yTopLeft;
        xBottomRight = _xBottomRight;
        yBottomRight = _yBottomRight;
    }

}

