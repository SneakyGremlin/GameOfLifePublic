package cells;

import exceptions.InvariantBroken;

/**
 * Primary contained class for a 3D array for game of life. Follows rules as specified in the README file.
 *
 * Invariant: Coordinates are always positive or zero.
 */
public class CellThreeDimension extends CellTwoDimension {

    private int zCoordinate;

    private CellThreeDimension(boolean initial, int x, int y, int z) throws InvariantBroken {
        super(initial, x, y);
        if (z < 0) {
            throw new InvariantBroken();
        }
        this.zCoordinate = z;
    }

}


// >>> when extending if have a constructor with arguments and some stuff inside must call it
// >>> remember if a callee throws an exception the caller must catch it.
// !!! TODO when an exception is thrown does the method terminate? what about finally? Create a pedagogical file
