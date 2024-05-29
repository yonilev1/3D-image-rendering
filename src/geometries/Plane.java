package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a plane in three-dimensional space.
 */
public class Plane implements Geometry {

	/** A point on the plane. */
	private final Point pointOnPlane;

	/** The normal vector to the plane. */
	private final Vector normalVector;

	/**
	 * Constructs a plane passing through three points.
	 *
	 * @param x The first point.
	 * @param y The second point.
	 * @param z The third point.
	 */
	public Plane(Point p1, Point p2, Point p3) {
		Vector v1 = p2.subtract(p1);
		Vector v2 = p3.subtract(p1);
		this.pointOnPlane = p1; 
		this.normalVector = v1.crossProduct(v2).normalize();
	}

	/**
	 * Constructs a plane with the specified point on the plane and normal vector.
	 *
	 * @param pointOnPlane The point on the plane.
	 * @param normalVector The normal vector to the plane.
	 */
	public Plane(Point pointOnPlane, Vector normalVector) {
		this.pointOnPlane = pointOnPlane;
		this.normalVector = normalVector.normalize();
	}

	/**
	 * Returns the normal vector to the plane.
	 * 
	 * @param pointOnSurface (unused) A point on the surface of the plane.
	 * @return The normal vector to the plane.
	 */
	@Override
	public Vector getNormal(Point pointOnSurface) {
		return normalVector;
	}

	/**
	 * Returns the normal vector to the plane.
	 * 
	 * @return The normal vector to the plane.
	 */
	public Vector getNormal() {
		return normalVector;
	}
}
