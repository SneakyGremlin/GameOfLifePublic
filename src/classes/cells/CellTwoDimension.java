package classes.cells;

import exceptions.InvariantBroken;

/**
 * Primary contained class for a 2D array for game of life. Follows all rules of the classical version. For rules refer
 * to README file.
 *
 * Invariant: Coordinates are always positive or zero.
 */
public class CellTwoDimension {

    protected boolean state;
    protected int xCoordinate;
    protected int yCoordinate;

    public CellTwoDimension(boolean initial, int x, int y) throws InvariantBroken {
        if (x < 0 || y < 0) {
            throw new InvariantBroken();
        }
        state = initial;
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    /**
     * GETTER(S)
     */

    public Boolean getState() {
        return this.state;
    }

    /**
     *  SETTER(S)
     */

    public void setState(boolean state) {
        this.state = state;
    }
}

// TODO boolean or Boolean?

// TODO override hashcode and equals and refine the for loop inside container