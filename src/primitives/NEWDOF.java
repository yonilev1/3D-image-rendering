package primitives;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * Represents a target area for super sampling in a 3D space.
 * This class allows defining a square target area and scattering points within this area.
 */
public class NEWDOF {
    /**
     * The edge size of the square target area.
     */
    private double edgeSize = 0;

    /**
     * The center point of the target area.
     */
    private Point centerPoint = null;

    /**
     * The number of rows and columns in the grid.
     * This should be equal and in the form 2^k + 1 where k is an unsigned integer.
     */
    private int grid = 0;

    /**
     * Sets the center point of the target area.
     *
     * @param centerPoint The center point of the target area.
     * @return The updated NEWDOF instance.
     */
    public NEWDOF setCenter(Point centerPoint) {
        this.centerPoint = centerPoint;
        return this;
    }

    /**
     * Sets the edge size of the square target area.
     *
     * @param edgeSize The edge size of the square target area.
     * @return The updated NEWDOF instance.
     */
    public NEWDOF setEdgeSize(double edgeSize) {
        this.edgeSize = edgeSize;
        return this;
    }

    /**
     * Constructor for NEWDOF with edge size and grid.
     *
     * @param edgeSize The edge size of the square target area.
     * @param grid     The number of rows and columns in the grid (should be 2^k + 1 where k is an unsigned integer).
     */
    public NEWDOF(double edgeSize, int grid) {
        this.edgeSize = edgeSize;
        this.grid = grid;
    }

    /**
     * Sets the number of rows and columns in the grid.
     *
     * @param grid The number of rows and columns in the grid.
     * @return The updated NEWDOF instance.
     */
    public NEWDOF setGrid(int grid) {
        this.grid = grid;
        return this;
    }

    /**
     * Scatters points in a jittered manner, including the center point.
     * The points are scattered in a grid pattern around the center point.
     *
     * @param vTo The vector from the center ray to the center point of the target area that is orthogonal to the target area.
     * @return A list of scattered points including the center point.
     */
    public List<Point> scatterPoints(Vector vTo) {
        if (isZero(grid) || isZero(edgeSize) || grid == 1) {
            return null; // Return null if grid or edge size is not properly set
        }
        
        Vector vX = vTo.getOrto(); // Get an orthogonal vector
        Vector vY = vX.crossProduct(vTo).normalize(); // Get another orthogonal vector
        double ribOverGrid = edgeSize / grid;
        int gridOverTwo = grid / 2;
        List<Point> pointList = new LinkedList<>();
        
        for (int i = -gridOverTwo; i <= gridOverTwo; i++) {
            for (int j = -gridOverTwo; j <= gridOverTwo; j++) {
                Point p = this.centerPoint;
                double rand;
                
                if (j != 0) {
                    rand = Math.random() * ribOverGrid;
                    p = p.add(vY.scale(j * ribOverGrid + rand));
                }
                
                if (i != 0) {
                    rand = Math.random() * ribOverGrid;
                    p = p.add(vX.scale(i * ribOverGrid + rand));
                }
                
                pointList.add(p);
            }
        }
        
        return pointList;
    }

    /**
     * Scatters four points around the center point in a square pattern.
     * The points are scattered at the corners of a square with edge size half of the total edge size.
     *
     * @param vTo The vector from the center ray to the center point of the target area that is orthogonal to the target area.
     * @return A list of four scattered points around the center point.
     */
    public List<Point> scatterFourPoints(Vector vTo) {
        Vector vX = vTo.getOrto(); // Get an orthogonal vector
        Vector vY = vX.crossProduct(vTo).normalize(); // Get another orthogonal vector
        double ribOverTwo = edgeSize / 2;
        
        return List.of(
            centerPoint.add(vX.scale(ribOverTwo)).add(vY.scale(ribOverTwo)),
            centerPoint.add(vX.scale(ribOverTwo)).add(vY.scale(-ribOverTwo)),
            centerPoint.add(vX.scale(-ribOverTwo)).add(vY.scale(ribOverTwo)),
            centerPoint.add(vX.scale(-ribOverTwo)).add(vY.scale(-ribOverTwo))
        );
    }
}
