package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
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
	 * Finds the intersection points between the given ray and the sphere.
	 * 
	 * @param ray the ray to intersect with the sphere
	 * @return a list of intersection points, or null if there are no intersections
	 */
	public List<Point> findIntersections(Ray ray) {
	    // Calculate vector from ray's head to the center of the sphere
	    Vector u = centerPoint.subtract(ray.getHead());
	    
	    // Calculate projection of u onto ray's direction
	    double tm = u.dotProduct(ray.getDirection());
	    
	    // Calculate distance squared between ray's head and sphere's center
	    double d = u.dotProduct(u) - tm * tm;
	    
	    // Calculate squared radius of the sphere
	    double r2 = radius * radius;

	    // Check for no intersection (ray misses the sphere)
	    if (d > r2) {
	        return null; // No intersection
	    }
	    
	    // Calculate distance from projection point to intersection points
	    double th = Math.sqrt(r2 - d);
	    
	    // Calculate parametric distances along the ray to intersection points
	    double t1 = Util.alignZero(tm - th);
	    double t2 = Util.alignZero(tm + th);

	    // Return intersection points (if any)
	    if (t1 >= 0 && t2 >= 0) {
	        // Two intersection points
	        return List.of(
	            ray.getPoint(t1), ray.getPoint(t2));
	    } else if (t1 >= 0) {
	        // One intersection point (t1)
	        return List.of(ray.getPoint(t1));
	    } else if (t2 >= 0) {
	        // One intersection point (t2)
	        return List.of(ray.getPoint(t2));
	    } else {
	        // No intersection
	        return null;
	    }
	}

}
