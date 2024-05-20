package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a cylinder in three-dimensional space.
 * A cylinder is defined by its height and a ray that defines its axis.
 */
public class Cylinder extends Tube {
    
    /** The height of the cylinder. */
    private final double hight;
    
    /**
     * Constructs a new cylinder with the specified height and axis ray.
     *
     * @param height The height of the cylinder.
     * @param axisRay The ray that defines the axis of the cylinder.
     */
    public Cylinder(double hight, Ray axisRay) {
        super(hight, axisRay);
        this.hight = hight;
    }

    /**
     * Returns the normal vector at the specified point on the surface of the cylinder.
     * 
     * @param pointOnSurface The point on the surface of the cylinder.
     * @return The normal vector at the specified point.
     */
    @Override
    public Vector getNormal(Point pointOnSurface) {
        return null; // Temporary implementation
    }
}
