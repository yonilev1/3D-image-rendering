package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a sphere in three-dimensional space.
 */
public class Sphere extends RadialGeometry {

	/** The center point of the sphere. */
	private final Point centerPoint;

	/**
	 * Constructs a new sphere with the specified center point and radius.
	 *
	 * @param centerPoint The center point of the sphere.
	 * @param radius      The radius of the sphere.
	 */
	public Sphere(Point centerPoint, double radius) {
		super(radius);
		this.centerPoint = centerPoint;
	}

	/**
	 * Returns the normal vector at the specified point on the surface of the
	 * sphere.
	 * 
	 * @param pointOnSurface The point on the surface of the sphere.
	 * @return The normal vector at the specified point.
	 */
	@Override
	public Vector getNormal(Point pointOnSurface) {
		return pointOnSurface.subtract(centerPoint).normalize();
	}
	
	 /**
     * Finds the intersection points between a given ray and the Sphere.
     * 
     * @param ray the ray to intersect with the Sphere
     * @return a list of intersection points, or null if there are no intersections
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        // Implementation goes here
        return null; // Returning null for now as a placeholder
    }
}
