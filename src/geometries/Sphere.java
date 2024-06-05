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
		
		if(ray.getHead().equals(centerPoint)) {
			return List.of(ray.getPoint(radius));
		}
		
	    // Calculate vector from ray's head to the center of the sphere
	    Vector u = centerPoint.subtract(ray.getHead());
	    	    
	    // Calculate projection of u onto ray's direction
	    double tm = Util.alignZero(u.dotProduct(ray.getDirection()));
	    
	    // Calculate distance squared between ray's head and sphere's center
	    double d = u.lengthSquared() - tm * tm;

	    // Calculate distance from projection point to intersection points
	    double thSquared = Util.alignZero(radiusSquared - d);
	    
	    // Check for no intersection (ray misses the sphere)
	    if (thSquared <= 0) {
	        return null; // No intersection
	    }
	    
	    
	    double th = Math.sqrt(thSquared);
	    // Calculate parametric distances along the ray to intersection points
	    double t1 = Util.alignZero(tm - th);
	    double t2 = Util.alignZero(tm + th);
	    
	    if(t2 <=0) {return null;}
	    
	    if(t1<=0)
	    	return List.of(ray.getPoint(t2));
	    else
	    	return List.of(ray.getPoint(t1),ray.getPoint(t2));
	    
	
}
}
