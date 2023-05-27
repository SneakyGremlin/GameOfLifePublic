package container;

// TODO implement iterable and skip manual iteration
// TODO potential for optimisation by splitting the board into bits

import classes.cells.CellTwoDimension;
import exceptions.InvariantBroken;

import java.util.ArrayList;


// TODO specification for coordiante system

/**
 * Primary Container for a Two Dimensional Version of Game of Life.
 *
 * Bears mentioning that since computer memory is unfortunately a finite construct I made the design decision to make
 * the container "wrap around".
 */

public class ContainerTwoDimension {

    private ArrayList<ArrayList<CellTwoDimension>> container;
    private int xDimension;
    private int yDimension;
    private ContainerTwoDimension theSuccessor;

    public ContainerTwoDimension(int xDimension, int yDimension) {
        this.container = new ArrayList<>();
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        for (int x = 0; x < xDimension; x++) {
            ArrayList<CellTwoDimension> temp = new ArrayList<>();
            for (int y = 0; y < yDimension; y++) {
                try {
                    CellTwoDimension cell = new CellTwoDimension(false, x, y);
                    temp.add(cell);
                } catch (InvariantBroken e) {
                    // nothing
                }
            }
            container.add(temp);
        }
    }

    /**
     * For optimization and to mitigate against testing tedium "update" is decomposed into two methods "updateBorders"
     * and "updateOthers."
     */
    public void update() {
        theSuccessor = new ContainerTwoDimension(this.xDimension, this.yDimension);
        updateBorders();
        updateOthers();
        this.container = theSuccessor.container; // TODO check
        // >>> Java has GarbageCollection otherwise I would've done the freeing myself.
    }

    /** updateOthers updates all the cells apart from the ones at the borders. Utilises simple, beauteous, but precarious quadruple
     * nested iteration.
     *
     * A bit of a side note but if you're into asymptotics, despite the fact there are 4 for_loops the method itself is still
     * O(n) where n is the no of cells. GoBsMaCkInG, right?
     *
     */
    private void updateOthers() {
        for (int x = 1; x < xDimension - 1; x++) { // !!! TODO
            for (int y = 1; y < yDimension - 1; y++) {
                int count = 0;
                CellTwoDimension cellCurrNotUpdate = (this.container.get(x)).get(y);
                CellTwoDimension cellCurrToUpdate = (this.theSuccessor.container.get(x)).get(y); // !!! >>> the successor's is updated
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
     * updateBorders is separate since the container wraps around, and it's a wee bit hard (well more like leads to
     * unsightly code) to account for that through normal iteration.
     */
    private void updateBorders() {

        updateCorners();

        updateStripsSansCorners();
    }

    private void updateStripsSansCorners() {

        topmostRow();

        bottommostRow();

        leftmostColumn();

        rightmostColumn();
    }

    // >>> for testing the four methods below change the access modifier to public and uncomment the two statements at the
    //      top and the bottom of each method

    private void rightmostColumn() {
        //theSuccessor = new ContainerTwoDimension(xDimension, yDimension);

        for (int i = 1; i < xDimension - 1 ; i++) {
            int count = 0;
            CellTwoDimension beingChecked;
            CellTwoDimension beingUpdated;
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

    private void leftmostColumn() {
        //theSuccessor = new ContainerTwoDimension(xDimension, yDimension);

        for (int i = 1; i < xDimension - 1 ; i++) {
            int count = 0;
            CellTwoDimension beingChecked;
            CellTwoDimension beingUpdated;
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

    private void bottommostRow() {
        //theSuccessor = new ContainerTwoDimension(xDimension, yDimension);

        for (int i = 1; i < yDimension - 1 ; i++) {
            int count = 0;
            CellTwoDimension beingChecked;
            CellTwoDimension beingUpdated;
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

    private void topmostRow() {
        //theSuccessor = new ContainerTwoDimension(xDimension, yDimension);

        for (int i = 1; i < yDimension - 1 ; i++) {
            int count = 0;
            CellTwoDimension beingChecked;
            CellTwoDimension beingUpdated;
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


    private void updateCorners() {

        northwest();

        northeast();

        southwest();

        southeast();

    }

    // >>> for testing the four methods below uncomment the two statements at the top and bottom of each method AND change
    //      access modifier to private
    private void northwest() {
        //theSuccessor = new ContainerTwoDimension(xDimension, yDimension);


        int count = 0;
        CellTwoDimension beingChecked;
        CellTwoDimension beingUpdated;

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

    private void northeast() {
        // theSuccessor = new ContainerTwoDimension(xDimension, yDimension);


        int count = 0;
        CellTwoDimension beingChecked;
        CellTwoDimension beingUpdated;


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

    private void southwest() {
        //theSuccessor = new ContainerTwoDimension(xDimension, yDimension);


        int count = 0;
        CellTwoDimension beingChecked;
        CellTwoDimension beingUpdated;


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

    private void southeast() {
        //theSuccessor = new ContainerTwoDimension(xDimension, yDimension);


        int count = 0;
        CellTwoDimension beingChecked;
        CellTwoDimension beingUpdated;


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
    private int condCheckerAndUpdater(int count, CellTwoDimension beingChecked, CellTwoDimension beingUpdated, CellTwoDimension neighbour) {
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
        // >>> an attempt at refining the logic encapsulated above ^^^
//        if (((beingChecked.getState() && count >= 2) || !beingChecked.getState() &&count >= 3) && count < 4 ) {
//            beingUpdated.setState(true);
//        } else {
//            beingUpdated.setState(false);
//        }
        return count;


        // >>> for testing
//        beingUpdated.setState(true);
//        return 0;
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


    /**
     * Methods below are for testing
     */

    public void setCell(int x, int y, boolean val) {
        container.get(x).get(y).setState(val);
    }
}

// Why cant i use [] for access? !!! TODO


// !!!! TODO x!=x and y!=y

// TODO == is used for primitives yes?
// TODO default access modifier
// TODO automated testing; automated construction of getters and setters
// TODO singleton pattern private constructor... constructors return a new object anyho... is there a defualt constructor?