package cells;

import exceptions.InvariantBroken;

/**
 * Primary (effectively abstract) contained class for a 2D array for game of life. Follows all rules of the classical version. For rules refer
 * to README file.
 *
 * For the files available for perusal, the class can be effectively made abstract.
 *
 * Attributes:
 *          - state: false if the cell is inactive, true if is active (active == alive).
 *          - xCoordinate: the column of the cell in the primary containing class.
 *          - yCoordinate: the row of the cell in the primary containing class.
 *          Note that owing to the above two attributes one may argue in favour of coupling but note that their existence has no bearing on the program.
 *          My rationale for the attributes is an object in a "game" should know its position (had this game been non-rudimentary, this would facilitate interactions
 *          between game objects).
 *          n.b. rows and columns start from 0.
 *
 * Invariant: Coordinates are always positive or zero.
 */
public class CellTwoDimension {

    protected boolean state;
    protected int xCoordinate;
    protected int yCoordinate;

    /** Constructor; intended for extensibility: i.e. if one wishes to preload a configuration.
     *
     * @param initial is the initial state of the cell; I included this parameter here for extensibility purposes but the default state should be false.
     * @param x indicates the column wherein the cell resides.
     * @param y indicates the row wherein the cell resides
     * @throws InvariantBroken when either of x or y is negative.
     */
    public CellTwoDimension(boolean initial, int x, int y) throws InvariantBroken {
        if (x < 0 || y < 0) {
            throw new InvariantBroken();
        }
        state = initial;
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    /** Constructor; sets state to default value of false.
     *
     * @param x indicates the column wherein the cell resides.
     * @param y indicates the row wherein the cell resides
     * @throws InvariantBroken when either of x or y is negative.
     */
    public CellTwoDimension(int x, int y) throws InvariantBroken {
        if (x < 0 || y < 0) {
            throw new InvariantBroken();
        }
        state = false;
        this.xCoordinate = x;
        this.yCoordinate = y;
    }


    /**
     * GETTER(S)
     */

    public boolean getState() {
        return this.state;
    }

    /**
     *  SETTER(S)
     */

    public void setState(boolean state) {
        this.state = state;
    }
}