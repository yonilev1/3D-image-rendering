package geometries;

import java.util.List;

import primitives.*;

/**
 * Represents a tube in three-dimensional space.
 */
public class Tube extends RadialGeometry {

	/** The axis of the tube. */
	protected final Ray axis;

	/**
	 * Constructs a new tube with the specified radius and axis.
	 *
	 * @param radius The radius of the tube.
	 * @param axis   The axis of the tube.
	 */
	public Tube(double radius, Ray axis) {
		super(radius);
		this.axis = axis;
	}

	/**
	 * Returns the normal vector at the specified point on the surface of the tube.
	 * 
	 * @param pointOnSurface The point on the surface of the tube.
	 * @return The normal vector at the specified point.
	 */
	@Override
	public Vector getNormal(Point pointOnSurface) {
		double t = this.axis.getDirection().dotProduct((pointOnSurface.subtract(this.axis.getHead())));
		return pointOnSurface.subtract(axis.getPoint(t)).normalize();
	}

	/**
	 * Finds the intersection points between a given ray and the Tube.
	 * 
	 * @param ray the ray to intersect with the Tube
	 * @return a list of intersection points, or null if there are no intersections
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		// Implementation goes here
		return null; // Returning null for now as a placeholder
	}
}