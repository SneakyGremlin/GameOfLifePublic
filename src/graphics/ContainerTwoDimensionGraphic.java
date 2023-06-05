package graphics;

import exceptions.InvariantBroken;

import java.util.ArrayList;

/**
 * Primary Container for a Two Dimensional Version of Game of Life.
 *
 * Bears mentioning that since computer memory is unfortunately a finite construct I made the design decision to make
 * the container "wrap around". So when the east ends the west begins; likewise for north and south. Corners wrap around diagonally,
 * therefore, the NorthWest Corner will wrap to the SouthEast Corner; likewise for NE AND SW.
 *
 * attributes:
 *          container: is a nested array of format row and then column i.e. container.get(row).get(column) which contains CellTwoDimensionGraphic.
 *          xDimension: is the number of columns.
 *          yDimension: is the number of rows.
 *          theSuccessor: the class has a reflexive association with itself. Could have chosen to have another container but this is more extensible.
 *          n.b. xDimension and yDimension actually refer to the number of rows/columns. NO ZERO IS NOT ONE.
 */
public class ContainerTwoDimensionGraphic {

    private ArrayList<ArrayList<CellTwoDimensionGraphic>> container;
    private int xDimension;
    private int yDimension;
    private ContainerTwoDimensionGraphic theSuccessor;

    /** Constructor
     *
     * @param xDimension is the column number.
     * @param yDimension is the row number.
     *
     * successor remains null
     * container is populated with CellsTwoDimensionGraphic
     */

    public ContainerTwoDimensionGraphic(int xDimension, int yDimension) {
        this.container = new ArrayList<>();
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        for (int x = 0; x < xDimension; x++) {
            ArrayList<CellTwoDimensionGraphic> temp = new ArrayList<>();
            for (int y = 0; y < yDimension; y++) {
                try {
                    CellTwoDimensionGraphic cell = new CellTwoDimensionGraphic(x, y);
                    temp.add(cell);
                } catch (InvariantBroken e) {
                    // nothing since this was for construction purposes and perhaps for extensibility.
                }
            }
            container.add(temp);
        }
    }

    /**
     * For optimization and to mitigate against testing tedium, "update" is decomposed into two methods "updateBorders"
     * and "updateOthers".
     *
     * The successor is not instantiated and by the end of this method it's container will have supplanted this' container.
     */
    public void update() {
        theSuccessor = new ContainerTwoDimensionGraphic(this.xDimension, this.yDimension);
        updateBorders();
        updateOthers();
        this.container = theSuccessor.container;
        // >>> Java has GarbageCollection otherwise I would've done the freeing myself.
    }

    /**
     * updateOthers updates all the cells apart from the ones at the borders. Utilises simple, beauteous, but precarious quadruple
     * nested iteration (think you have a rectangle, and you cut off the border)
     *
     * A bit of a side note but if you're into asymptotics, despite the fact there are 4 for_loops the method itself is still
     * O(n) where n is the no of cells. GoBsMaCkInG, right?
     *
     */
    private void updateOthers() {
        for (int x = 1; x < xDimension - 1; x++) {
            for (int y = 1; y < yDimension - 1; y++) {
                int count = 0;
                CellTwoDimensionGraphic cellCurrNotUpdate = (this.container.get(x)).get(y);
                CellTwoDimensionGraphic cellCurrToUpdate = (this.theSuccessor.container.get(x)).get(y); // >>> the successor's is updated
                for (int x_manual = x - 1; x_manual < x + 2; x_manual++) {
                    for (int y_manual = y - 1; y_manual < y + 2; y_manual++) {
                        if (x != x_manual) {
                            count = condCheckerAndUpdater(count, cellCurrNotUpdate, cellCurrToUpdate, (container.get(x_manual)).get(y_manual));
                        } else if (y != y_manual){
                            count = condCheckerAndUpdater(count, cellCurrNotUpdate, cellCurrToUpdate, (container.get(x_manual)).get(y_manual));
                        }
                    }
                }
            }
        }
    }

    /**
     * updateBorders() is separate since the container wraps around, and it's a wee bit hard (well more like leads to
     * unsightly code) to account for that through normal iteration (too many ifs). Is further deconstructed into updateCorner()
     * and updateStripsSansCorners().
     */
    private void updateBorders() {

        updateCorners();

        updateStripsSansCorners();
    }

    /**
     * method updates the borders without the corners. is deconstructed into four respective methods.
     */
    private void updateStripsSansCorners() {

        topmostRow();

        bottommostRow();

        leftmostColumn();

        rightmostColumn();
    }

    // >>> for testing the four methods below change the access modifier to public and uncomment the two statements at the
    //      top and the bottom of each method; remember to call update.

    /**
     * wraps with the leftmost Column.
     */

    private void rightmostColumn() {
        //theSuccessor = new ContainerTwoDimension(xDimension, yDimension);

        for (int i = 1; i < xDimension - 1 ; i++) {
            int count = 0;
            CellTwoDimensionGraphic beingChecked;
            CellTwoDimensionGraphic beingUpdated;
            beingChecked = this.container.get(i).get(yDimension - 1);
            beingUpdated = this.theSuccessor.container.get(i).get(yDimension - 1);


            // checks counterclockwise
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i - 1).get(yDimension - 1));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i - 1).get(yDimension - 2));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i).get(yDimension - 2));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i + 1).get(yDimension - 2));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i + 1).get(yDimension - 1));

            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i + 1).get(0));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i).get(0));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i - 1).get(0));
        }

        //this.container = theSuccessor.container;
    }

    /**
     * wraps with the rightmost column
     */

    private void leftmostColumn() {
        //theSuccessor = new ContainerTwoDimension(xDimension, yDimension);

        for (int i = 1; i < xDimension - 1 ; i++) {
            int count = 0;
            CellTwoDimensionGraphic beingChecked;
            CellTwoDimensionGraphic beingUpdated;
            beingChecked = this.container.get(i).get(0);
            beingUpdated = this.theSuccessor.container.get(i).get(0);

            // checks clockwise
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i - 1).get(0));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i - 1).get(1));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i).get(1));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i + 1).get(1));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i + 1).get(0));

            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i + 1).get(yDimension - 1));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i).get(yDimension - 1));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(i - 1).get(yDimension - 1));
        }

        //this.container = theSuccessor.container;
    }

    /**
     * wraps with the topmost Row.
     */
    private void bottommostRow() {
        //theSuccessor = new ContainerTwoDimension(xDimension, yDimension);

        for (int i = 1; i < yDimension - 1 ; i++) {
            int count = 0;
            CellTwoDimensionGraphic beingChecked;
            CellTwoDimensionGraphic beingUpdated;
            beingChecked = this.container.get(xDimension - 1).get(i);
            beingUpdated = this.theSuccessor.container.get(xDimension - 1).get(i);

            // checks clockwise
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 1).get(i - 1));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 2).get(i - 1));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 2).get(i));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 2).get(i + 1));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 1).get(i + 1));

            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(0).get(i + 1));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(0).get(i));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(0).get(i - 1));
        }

        //this.container = theSuccessor.container;
    }

    /**
     * wraps with the bottommost row.
     */
    private void topmostRow() {
        //theSuccessor = new ContainerTwoDimension(xDimension, yDimension);

        for (int i = 1; i < yDimension - 1 ; i++) {
            int count = 0;
            CellTwoDimensionGraphic beingChecked;
            CellTwoDimensionGraphic beingUpdated;
            beingChecked = this.container.get(0).get(i);
            beingUpdated = this.theSuccessor.container.get(0).get(i);

            // checks counterclockwise
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(0).get(i - 1));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(1).get(i - 1));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(1).get(i));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(1).get(i + 1));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(0).get(i + 1));

            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 1).get(i + 1));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 1).get(i));
            count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 1).get(i - 1));
        }

        //this.container = theSuccessor.container;
    }

    /**
     * the corners are special: a 3 by 3 grid of cells with a corner cell at the middle wraps with all three other corners of the board
     * Consider the following matrix at the bottomright corner of the board.
     *
     *          NNL
     *          MML
     *          TTD
     *
     *          >>> N means normal coordinate; intuitive and adjacent
     *          >>> M means the middle cell i.e. the corner cell
     *          >>> L means the cell that exists at the first/zeroth (left) column (hence the "wrapping around")
     *          >>> T means the cell that exists at the first/zeroth (top) row
     *          >>> D stands for diagonal; refers to the cell at the NorthWest corner of the board.
     */
    private void updateCorners() {

        northwest();

        northeast();

        southwest();

        southeast();

    }

    // >>> for testing the four methods below uncomment the two statements at the top and bottom of each method AND change
    //      access modifier to private; call update too

    /**
     * call condCheckerAndUpdater appropriate number of times and with appropriate parameters.
     */
    private void northwest() {
        //theSuccessor = new ContainerTwoDimension(xDimension, yDimension);


        int count = 0;
        CellTwoDimensionGraphic beingChecked;
        CellTwoDimensionGraphic beingUpdated;

        // checks counterclockwise
        beingChecked = this.container.get(0).get(0);
        beingUpdated = this.theSuccessor.container.get(0).get(0);
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 1).get(1));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 1).get(0));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 1).get(yDimension - 1));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(0).get(yDimension - 1));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(1).get(yDimension - 1));

        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(0).get(1));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(1).get(1));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(1).get(0));

        //this.container = theSuccessor.container;
    }

    /**
     * call condCheckerAndUpdater appropriate number of times and with appropriate parameters.
     */
    private void northeast() {
        // theSuccessor = new ContainerTwoDimension(xDimension, yDimension);


        int count = 0;
        CellTwoDimensionGraphic beingChecked;
        CellTwoDimensionGraphic beingUpdated;


        beingChecked = this.container.get(0).get(yDimension - 1);
        beingUpdated = this.theSuccessor.container.get(0).get(yDimension - 1);

        // checks clockwise
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 1).get(yDimension - 2));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 1).get(yDimension - 1));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 1).get(0));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(0).get(0));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(1).get(0));

        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(1).get(yDimension - 1));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(1).get(yDimension - 2));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(0).get(yDimension - 2));


        //this.container = theSuccessor.container;
    }

    /**
     * call condCheckerAndUpdater appropriate number of times and with appropriate parameters.
     */
    private void southwest() {
        //theSuccessor = new ContainerTwoDimension(xDimension, yDimension);


        int count = 0;
        CellTwoDimensionGraphic beingChecked;
        CellTwoDimensionGraphic beingUpdated;


        beingChecked = this.container.get(xDimension - 1).get(0);
        beingUpdated = this.theSuccessor.container.get(xDimension - 1).get(0);

        // checks counterclockwise
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 2).get(yDimension - 1));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 1).get(yDimension - 1));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(0).get(yDimension - 1));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(0).get(0));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(0).get(1));

        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 1).get(1));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 2).get(1));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 2).get(0));

        //this.container = theSuccessor.container;
    }

    /**
     * call condCheckerAndUpdater appropriate number of times and with appropriate parameters.
     */
    private void southeast() {
        //theSuccessor = new ContainerTwoDimension(xDimension, yDimension);


        int count = 0;
        CellTwoDimensionGraphic beingChecked;
        CellTwoDimensionGraphic beingUpdated;


        beingChecked = this.container.get(xDimension - 1).get(yDimension - 1);
        beingUpdated = this.theSuccessor.container.get(xDimension - 1).get(yDimension - 1);

        // checks clockwise
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 2).get(0));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 1).get(0));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(0).get(0));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(0).get(yDimension - 1));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(0).get(yDimension - 2));

        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 1).get(yDimension - 2));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 2).get(yDimension - 2));
        count = condCheckerAndUpdater(count, beingChecked, beingUpdated, this.container.get(xDimension - 2).get(yDimension - 1));

        //this.container = theSuccessor.container;
    }

    /**
     * EXCERPTED FROM WIKIPEDIA
     * Any live cell with two or three live neighbours survives.
     * Any dead cell with three live neighbours becomes a live cell.
     * All other live cells die in the next generation. Similarly, all other dead cells stay dead.
     */
    private int condCheckerAndUpdater(int count, CellTwoDimensionGraphic beingChecked, CellTwoDimensionGraphic beingUpdated, CellTwoDimensionGraphic neighbour) {
        if (neighbour.getState()) {
            count++;
        }
        if (count >= 4) {
            beingUpdated.setState(false);
        } else if (beingChecked.getState() && count >= 2) {
            beingUpdated.setState(true);
        } else if (!beingChecked.getState() &&count >= 3) {
            beingUpdated.setState(true);
        } else {
            beingUpdated.setState(false);
        }

        // >>> the code below is what the decoupling section in README was referring to.
//        if (beingUpdated.getState()) {
//            beingUpdated.getSquare().setBackground(Color.black);
//        } else {
//            beingUpdated.getSquare().setBackground(Color.white);
//        }

        beingUpdated.update();

        return count;
    }


    /**
     * GETTERS
     */

    public int getXDimension() {
        return xDimension;
    }

    public int getYDimension() {
        return yDimension;
    }


    /**
     * PRINTER
     */

    public void print() {
        for (int x = 0; x < xDimension; x++) {
            for (int y = 0; y < yDimension; y++) {
                if ((container.get(x)).get(y).getState()) {
                    System.out.print("!");
                } else {
                    System.out.print("F");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void setCell(int x, int y, boolean val) {
        container.get(x).get(y).setState(val);
    }

    public CellTwoDimensionGraphic getCell(int x, int y) {
        return container.get(x).get(y);
    }


    /**
     * Sets all members of the container to their default states i.e. with rect's color white and state false.
     */
    public void clearAll() {
        for (int x = 0; x < xDimension; x++) {
            for (int y = 0; y < yDimension; y++) {
                CellTwoDimensionGraphic cell = container.get(x).get(y);
                cell.setState(false);
                cell.update();
            }
        }

    }
}