package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.DoubleMatrix;
import ca.ubc.ece.cpen221.ip.core.Image;

import java.awt.Color;

/**
 * This datatype represents the output of a spatial Discrete Fourier Transform,
 * and holds the amplitude and phase matrix obtained from the DFT.
 */
public class DFTOutput {
    private DoubleMatrix amplitude;
    private DoubleMatrix phase;

    /**
     * Create a new DFTOutput instance.
     *
     * @param _amplitude is not null
     * @param _phase     is not null, and is equal in dimensions to _amplitude
     */
    public DFTOutput(double[][] _amplitude, double[][] _phase) {
        amplitude = new DoubleMatrix(_amplitude);
        phase = new DoubleMatrix(_phase);
        if (amplitude.columns != phase.columns || amplitude.rows != phase.rows) {
            throw new IllegalArgumentException(
                    "amplitude and phase matrices should have the same dimensions"
            );
        }
    }

    /**
     * Overrides assertEquals so that two DFTOutput objects
     * and their corresponding phase and amplitude arrays are compared
     *
     * @param o the second DFT output of amplitudes and
     * phases to which we want to compare to
     * @return true or false, corresponding to whether or not
     * the amplitudes and phases are equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DFTOutput)) {
            return false;
        }
        DFTOutput other = (DFTOutput) o;
        return amplitude.equals(other.amplitude) && phase.equals(other.phase);
    }

    /**
     * Overrides the hashcode command, so that the hashcode of DFT Amplitude is returned
     *
     * @return
     */
    @Override
    public int hashCode() {
        return amplitude.hashCode();
    }

    /**
     * Returns the 2D array corresponding to the amplitude calculated by the dft method
     *
     * @return the 2D array corresponding to the amplitude calculated by the dft method
     */
    public double[][] getAmplitude() {
        return amplitude.getMatrix();
    }

    public double[][] getPhases() {
        return phase.getMatrix();
    }

}
